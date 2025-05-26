package inventarioEyBackend.repository;


import inventarioEyBackend.dto.ReporteAsignacionDTO;
import inventarioEyBackend.model.AsignacionEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionEquipoRepository extends JpaRepository<AsignacionEquipo, Long> {

    // 🔍 Consulta personalizada con proyección DTO
    @Query("""
        SELECT 
            a.area.nombre AS nombreArea,
            a.equipo.codigoEquipo AS codigoEquipo,
            CONCAT(a.funcionario.nombreFuncionario, ' ', a.funcionario.apellidoFuncionario) AS funcionario,
            a.fechaAsignacion AS fechaAsignacion
        FROM AsignacionEquipo a
        ORDER BY a.area.nombre DESC
    """)
    List<ReporteAsignacionDTO> obtenerReporteAsignaciones();

    // 🔍 Métodos derivados
    List<AsignacionEquipo> findByEquipoId(Long equipoId);
    List<AsignacionEquipo> findByFuncionarioId(Long funcionarioId);
    List<AsignacionEquipo> findByAreaId(Long areaId);
}
