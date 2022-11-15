package co.com.poli.authservice.repository;

import java.util.Optional;

import co.com.poli.authservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByIdUser(int idUser);
}
