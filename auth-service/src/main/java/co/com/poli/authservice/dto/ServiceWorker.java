package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ServiceWorker {
    private int idRequest;
    private String fullName;
    private Long cellPhone;
    private String emailClient;
    private String comment;
    private int state;
}
