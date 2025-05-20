package inventarioEyBackend.repository;

import inventarioEyBackend.model.AsignacionEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionEquipoRepository  extends JpaRepository<AsignacionEquipo, Long> {
    List<AsignacionEquipo> findByEquipoId(Long equipoId);
    List<AsignacionEquipo> findByFuncionarioId(Long funcionarioId);
    List<AsignacionEquipo> findByAreaId(Long areaId);
}
