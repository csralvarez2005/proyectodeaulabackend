package inventarioEyBackend.service;
import inventarioEyBackend.model.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
public interface EquipoService {

    List<Equipo> getAllEquipos();
    Page<Equipo> getAllEquipos(Pageable pageable);
    Optional<Equipo> getEquipoById(Long id);
    Equipo createEquipo(Equipo equipo);
    Equipo updateEquipo(Long id, Equipo equipoDetails);
    void deleteEquipo(Long id);
    List<Equipo> findByTipo(String tipo);
    List<Equipo> findByMarca(String marca);
    List<Equipo> findByEstado(String estado);
    boolean existsById(Long id);
}
