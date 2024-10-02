package vn.io.vutiendat3601.shop.v2.inventory;

import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.product.Product;
import vn.io.vutiendat3601.shop.v2.product.ProductDao;

@RequiredArgsConstructor
@Service
public class ReceiptService {
  private final ReceiptDao receiptDao;
  private final ReceiptItemDao receiptItemDao;
  private final ProductDao productDao;
  private final TransactionDao transactionDao;

  @Transactional
  public void createReceipt(CreateReceiptRequest createReceiptReq) {
    if (createReceiptReq.signed_By() == null) {
      throw new IllegalArgumentException("Category code cannot be null or empty");
    }
    final Receipt receipt =
        Receipt.builder()
            .signedAt(ZonedDateTime.now())
            .signedBy(createReceiptReq.signed_By())
            .type(createReceiptReq.type())
            .description(createReceiptReq.description())
            .isSigned(false)
            .build();
    receiptDao.insertRecepit(receipt);

    // Lưu các mặt hàng trong hóa đơn
    for (CreateReceiptItemRequest itemReq : createReceiptReq.receipt_items()) {
      ReceiptItem receiptItem =
          ReceiptItem.builder()
              .receipt(receipt)
              .productId(itemReq.productId())
              .quantity(itemReq.quantity())
              .build();
      receiptItemDao.insertReceiptItem(receiptItem);
    }
  }

  public void updateIsSignedReceipt(Long id) {
    Receipt receipt = getReceiptId(id);
    receipt.setIsSigned(true);
    receiptDao.updateIsSignedReceipt(receipt);

    saveTransaction(receipt);
  }

  private void saveTransaction(Receipt receipt) {
    for (ReceiptItem item : receipt.getReceiptItems()) {
      Long final_quantity = item.getQuantity() + getQuantityByProductId(item.getProductId());
      Transaction transaction =
          Transaction.builder()
              .productId(item.getProductId())
              .beforeQuantity(getQuantityByProductId(item.getProductId()).longValue())
              .quantityToStocks(item.getQuantity())
              .quantity(final_quantity)
              .description(receipt.getDescription())
              .transactionType(receipt.getType())
              .receiptId(receipt.getId())
              .build();

      transactionDao.insert(transaction);
    }
  }

  private Integer getQuantityByProductId(Long id) {
    Optional<Product> productOpt = productDao.selectUnitInStocksByProductId(id);
    
    Product product = productOpt.orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

    return product.getUnitsInStock();
  }

  private Receipt getReceiptId(Long id) {
    return receiptDao
        .selectById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Receipt with id [%s] not found".formatted(id)));
  }
}
