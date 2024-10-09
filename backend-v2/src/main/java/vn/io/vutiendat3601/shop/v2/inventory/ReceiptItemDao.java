package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.List;

public interface ReceiptItemDao {
  void insertReceiptItem(ReceiptItem receiptItem);

  List<ReceiptItem> findByReceiptId(Long id);
}
