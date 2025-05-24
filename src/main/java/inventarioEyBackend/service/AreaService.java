package inventarioEyBackend.service;

import inventarioEyBackend.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AreaService {
    List<Area> getAllAreas();
    Page<Area> getAllAreas(Pageable pageable);
    Optional<Area> getAreaById(Long id);
    Area createArea(Area area);
    Area updateArea(Long id, Area areaDetails);
    void deleteArea(Long id);
    boolean existsById(Long id);
    Optional<Area> getAreaByAbreviatura(String abreviatura);
}
