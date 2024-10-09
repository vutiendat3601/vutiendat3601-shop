package vn.io.vutiendat3601.shop.v2.inventory;

public record CreateInventoryRequest(
  Long productId,
  String transactionType,
  Integer quantity,
  Integer quantity_to_stocks,
  Integer dinal_quantity,
  String description
) {}
