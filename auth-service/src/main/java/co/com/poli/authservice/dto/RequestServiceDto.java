package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestServiceDto {
    @NotEmpty(message = "No puede estar en blanco")
    @Email(message = "Debe tener el formato de email")
    private String emailClient;
    @NotEmpty(message = "No puede estar en blanco")
    private String comment;
    @NotNull
    @Positive
    private int idProfile;
}
