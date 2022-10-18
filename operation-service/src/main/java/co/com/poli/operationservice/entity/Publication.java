package co.com.poli.operationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idEmployee;
    private String serviceType;
    private String description;

    //Agregar entidades dto para agregar entre tablas
    //En los servicios revisar la manera de guardar en todas las tablas

}
