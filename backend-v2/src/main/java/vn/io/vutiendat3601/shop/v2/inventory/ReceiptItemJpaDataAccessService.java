package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiptItemJpaDataAccessService implements ReceiptItemDao {
  private final ReceiptItemRepository receiptItemRepo;

  @Autowired
  public ReceiptItemJpaDataAccessService(ReceiptItemRepository receiptItemRepository) {
    this.receiptItemRepo = receiptItemRepository;
  }

  @Override
  public void insertReceiptItem(ReceiptItem receiptItem) {
    receiptItemRepo.save(receiptItem);
  }

  @Override
  public List<ReceiptItem> findByReceiptId(Long id) {
   return receiptItemRepo.findByReceiptId(id);
  }
}
