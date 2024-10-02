package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long> {

  List<ReceiptItem> findByReceiptId(Long id);

}
