package co.com.poli.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyServices {
    private int idRequest;
    private String typeService;
    private String fullNameWorker;
    private String comment;
    private int state;
}
