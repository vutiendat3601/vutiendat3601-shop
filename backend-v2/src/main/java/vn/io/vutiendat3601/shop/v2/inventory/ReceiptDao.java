package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.Optional;

public interface ReceiptDao {
  Optional<Receipt> selectById(Long id);

  void insertRecepit(Receipt receipt);
  void updateIsSignedReceipt(Receipt receipt);
}
