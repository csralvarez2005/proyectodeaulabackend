package inventarioEyBackend.repository;

import inventarioEyBackend.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    List<Monitor> findByMarca(String marca);
    List<Monitor> findByModelo(String modelo);
    List<Monitor> findByEstado(String estado);
    boolean existsByNumeroSerie(String numeroSerie);
    List<Monitor> findByEquipoIsNull();
}
