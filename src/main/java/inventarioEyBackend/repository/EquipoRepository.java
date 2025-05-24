package inventarioEyBackend.repository;

import inventarioEyBackend.model.Area;
import inventarioEyBackend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByTipo(String tipo);
    List<Equipo> findByMarca(String marca);
    List<Equipo> findByEstado(String estado);
    List<Equipo> findByMonitoresIsEmpty();

    // Métodos de búsqueda por áreas
    List<Equipo> findByAreasId(Long areaId);
    List<Equipo> findByAreasIsEmpty();
    List<Equipo> findByAreasNombreContaining(String nombreArea);
    List<Equipo> findByAreasTipo(String tipoArea);

    @Query("SELECT e.codigoEquipo FROM Equipo e JOIN e.areas a WHERE a.abreviatura = :abreviatura ORDER BY e.codigoEquipo DESC")
    List<String> findCodigosByAbreviaturaOrderedDesc(@Param("abreviatura") String abreviatura);
}
