package co.com.poli.authservice.dto;

import co.com.poli.authservice.constants.DocumentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewUserDto {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private Long cellPhone;
    private String address;

    private String role;
}
