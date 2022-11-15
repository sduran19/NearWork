package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserInfoDto {
    private String name;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private Long cellPhone;
    private String address;
    private String email;
}
