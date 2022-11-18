package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ListSearchDto {
    private int idProfile;
    private int idWorker;
    private String typeService;
    private String fullNameWorker;
    private Long cellPhone;
    private String emailWorker;
}
