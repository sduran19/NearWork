package co.com.poli.authservice.dto;

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
public class ProfileDto {
    @NotEmpty(message = "No puede estar en blanco")
    @Email(message = "Debe tener el formato de email")
    private String emailUser;
    @NotEmpty(message = "No puede estar en blanco")
    private String typeService;
    @NotEmpty(message = "No puede estar en blanco")
    private String commune;
    @NotEmpty(message = "No puede estar en blanco")
    private String profession;
    @Positive
    private int yearsExperience;
    private String linkFacebook;
    private String linkInstragram;
}
