package vn.io.vutiendat3601.shop.v2.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionJpaDataAccessService implements TransactionDao {
  private final TransactionRepository transactionRepo;

  @Autowired
  public TransactionJpaDataAccessService(TransactionRepository transactionRepository) {
    this.transactionRepo = transactionRepository;
  }


  @Override
  public Long findInventoryIdByProductId(Long id) {
    return transactionRepo.findInventoryIdByProductId(id);
  }

  @Override
  public void insert(Transaction transaction) {
    transactionRepo.save(transaction);
  }
}
