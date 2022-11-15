package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileDto {
    private String emailUser;
    private String typeService;
    private String commune;
    private String profession;
    private int yearsExperience;
    private String linkFacebook;
    private String linkInstragram;
}
