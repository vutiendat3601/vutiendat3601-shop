package vn.io.vutiendat3601.shop.v2.inventory;

import java.util.List;

public record CreateReceiptRequest(Long signed_By,TransactionType type,String description, List<CreateReceiptItemRequest> receipt_items) {}
