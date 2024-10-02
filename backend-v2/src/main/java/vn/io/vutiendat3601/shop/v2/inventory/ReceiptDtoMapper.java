package vn.io.vutiendat3601.shop.v2.inventory;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;

public class ReceiptDtoMapper implements Function<Receipt, ReceiptDto> {

  @Override
  public ReceiptDto apply(Receipt receipt) {
    Assert.notNull(receipt, "recepit argument must not be null");
    ReceiptItem receiptItem =
        receipt.getReceiptItems().isEmpty() ? null : receipt.getReceiptItems().get(0);

    return new ReceiptDto(
        receipt.getSignedAt(), receipt.isSigned(), receipt.getSignedBy(),receipt.getType(), receipt.getDescription(), receiptItem);
  }
}
