package co.com.poli.authservice.repository;

import co.com.poli.authservice.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByIdWorker(int idWorker);
    List<Request> findAllByIdClient(int idClient);
}
