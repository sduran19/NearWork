package co.com.poli.operationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class LaborProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
