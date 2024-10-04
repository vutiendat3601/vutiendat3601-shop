package vn.io.vutiendat3601.shop.v2.inventory;

public interface TransactionDao {
  Long findInventoryIdByProductId(Long id);
  void insert(Transaction transaction);
}
