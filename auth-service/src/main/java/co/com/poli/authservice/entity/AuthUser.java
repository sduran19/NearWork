package co.com.poli.authservice.entity;

import co.com.poli.authservice.constants.DocumentTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private Long cellPhone;
    private String address;
    @Builder.Default
    private Boolean locked = false;
    private String role;
}
