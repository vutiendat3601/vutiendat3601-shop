package vn.io.vutiendat3601.shop.v2.user;

public record UserDto(
    Long id, String displayName, String email, String phone, Boolean isVerified) {}
