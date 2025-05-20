package inventarioEyBackend.service.impl;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Area;
import inventarioEyBackend.repository.AreaRepository;
import inventarioEyBackend.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    @Override
    public Page<Area> getAllAreas(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public Optional<Area> getAreaById(Long id) {
        return areaRepository.findById(id);
    }

    @Override
    @Transactional
    public Area createArea(Area area) {
        return areaRepository.save(area);
    }

    @Override
    @Transactional
    public Area updateArea(Long id, Area areaDetails) {
        return areaRepository.findById(id)
                .map(area -> {
                    area.setNombre(areaDetails.getNombre());
                    area.setTipo(areaDetails.getTipo());
                    return areaRepository.save(area);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public void deleteArea(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con id: " + id));
        areaRepository.delete(area);
    }

    @Override
    public boolean existsById(Long id) {
        return areaRepository.existsById(id);
    }
}
