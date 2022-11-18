package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthUserDto {
    @NotEmpty(message = "No puede estar en blanco")
    @Email(message = "Debe tener el formato de email")
    private String email;
    @NotEmpty(message = "No puede estar en blanco")
    private String password;
}
