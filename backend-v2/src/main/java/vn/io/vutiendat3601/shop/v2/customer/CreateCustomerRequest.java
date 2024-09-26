package vn.io.vutiendat3601.shop.v2.customer;

import java.util.List;

public record CreateCustomerRequest(Long userId, String name, List<String> phones) {}
