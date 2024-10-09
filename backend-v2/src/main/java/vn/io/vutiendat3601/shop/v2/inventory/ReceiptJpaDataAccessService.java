package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiptJpaDataAccessService implements ReceiptDao {
  private final ReceiptRespository receiptReso;

  @Autowired
  public ReceiptJpaDataAccessService(ReceiptRespository receiptRepository) {
    this.receiptReso = receiptRepository;
  }

  @Override
  public void insertRecepit(Receipt receipt) {
    receiptReso.save(receipt);
  }

  @Override
  public Optional<Receipt> selectById(Long id) {
    return receiptReso.findById(id);
  }

  @Override
  public void updateIsSignedReceipt(Receipt receipt) {
    receiptReso.save(receipt);
  }
}
