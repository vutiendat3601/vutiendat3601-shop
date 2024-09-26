package vn.io.vutiendat3601.shop.v2.user;

public record UserDto(Long id, String username, String email, String phone, Boolean isVerified) {}
