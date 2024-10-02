package vn.io.vutiendat3601.shop.v2.inventory;

import java.time.ZonedDateTime;

public record ReceiptDto(
    ZonedDateTime signed_at, boolean is_signed, Long signed_by, TransactionType transactionType, String description, ReceiptItem receiptItem) {}
