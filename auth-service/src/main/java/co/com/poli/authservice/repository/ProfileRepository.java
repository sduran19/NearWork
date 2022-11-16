package co.com.poli.authservice.repository;

import co.com.poli.authservice.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    List<Profile> findAllByIdUser(int idUser);
}
