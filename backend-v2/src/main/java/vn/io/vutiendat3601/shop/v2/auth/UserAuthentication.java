package vn.io.vutiendat3601.shop.v2.auth;

public record UserAuthentication(
    Long id, String username, String name, boolean isVerified, String customerCode) {}
