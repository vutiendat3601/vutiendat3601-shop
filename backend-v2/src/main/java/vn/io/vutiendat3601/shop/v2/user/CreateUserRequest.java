package vn.io.vutiendat3601.shop.v2.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @param password
 *     <ul>
 *       <li>At least 1 number
 *       <li>At least 8 characters
 *       <li>At least 1 lower character
 *       <li>At least 1 uppcase character
 *       <li>At least 1 character in [@, -, $, ?, !, # ]
 *     </ul>
 */
public record CreateUserRequest(
    @NotBlank String displayName,
    @Email String email,
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@$?!#])[A-Za-z0-9@$?!#-]{8,}$")
        String password) {}
