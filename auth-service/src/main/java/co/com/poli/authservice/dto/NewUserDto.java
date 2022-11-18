package co.com.poli.authservice.dto;

import co.com.poli.authservice.constants.DocumentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewUserDto {
    @NotEmpty(message = "No puede estar en blanco")
    @Email(message = "Debe tener el formato de email")
    private String email;
    @NotEmpty(message = "No puede estar en blanco")
    private String password;
    @NotEmpty(message = "No puede estar en blanco")
    private String name;
    @NotEmpty(message = "No puede estar en blanco")
    private String lastName;
    @NotEmpty(message = "No puede estar en blanco")
    private String documentType;
    @NotEmpty(message = "No puede estar en blanco")
    private String documentNumber;
    @Positive
    private Long cellPhone;
    @NotEmpty(message = "No puede estar en blanco")
    private String address;
    private String role;
}
