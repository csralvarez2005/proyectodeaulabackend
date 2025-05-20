package inventarioEyBackend.repository;

import inventarioEyBackend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByTipo(String tipo);
    List<Equipo> findByMarca(String marca);
    List<Equipo> findByEstado(String estado);
}
