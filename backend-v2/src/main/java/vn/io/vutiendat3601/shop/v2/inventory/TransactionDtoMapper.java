package vn.io.vutiendat3601.shop.v2.inventory;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;

public class TransactionDtoMapper implements Function<Transaction, TransactionDto> {

  @Override
  public TransactionDto apply(Transaction inventory) {
    Assert.notNull(inventory, "inventory argument must not be null");
    return new TransactionDto(
        inventory.getId(),
        inventory.getProductId(),
        inventory.getTransactionType(),
        inventory.getBeforeQuantity(),
        inventory.getQuantityToStocks(),
        inventory.getQuantity(),
        inventory.getDescription(),
        inventory.getReceiptId());
  }
}
