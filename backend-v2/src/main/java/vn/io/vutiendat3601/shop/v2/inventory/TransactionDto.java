package vn.io.vutiendat3601.shop.v2.inventory;

public record TransactionDto(
    Long id,
    Long productId,
    TransactionType transactionType,
    Long beforeQuantity,
    Long quantityToStocks,
    Long quantity,
    String description,
    Long receiptId) {}
