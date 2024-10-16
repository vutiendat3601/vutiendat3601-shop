package vn.io.vutiendat3601.shop.v2.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Receipt")
@RestController
@RequestMapping("v2/receipts")
@RequiredArgsConstructor
public class ReceiptController {
  private final ReceiptService receiptServicel;

  @PostMapping
  public ResponseEntity<?> createReceipt(@RequestBody CreateReceiptRequest createReceiptRequest) {
    receiptServicel.createReceipt(createReceiptRequest);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{id}/sign")
  public ResponseEntity<?> updateIsSignedReceipt(@PathVariable("id") Long id){
    receiptServicel.updateIsSignedReceipt(id);
    return ResponseEntity.ok().build();
  }
}
