package vn.io.vutiendat3601.shop.v2.auth;

public record UserAuthentication(
    Long id, String displayName, String email, String phone, boolean isVerified) {}
