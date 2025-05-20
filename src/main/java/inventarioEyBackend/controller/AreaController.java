package inventarioEyBackend.controller;
import inventarioEyBackend.model.Area;
import inventarioEyBackend.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaService.getAllAreas();
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<Area>> getAllAreasPaginated(Pageable pageable) {
        Page<Area> areas = areaService.getAllAreas(pageable);
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Area> getAreaById(@PathVariable Long id) {
        return areaService.getAreaById(id)
                .map(area -> new ResponseEntity<>(area, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        Area newArea = areaService.createArea(area);
        return new ResponseEntity<>(newArea, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Area> updateArea(@PathVariable Long id, @RequestBody Area areaDetails) {
        if (!areaService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Area updatedArea = areaService.updateArea(id, areaDetails);
        return new ResponseEntity<>(updatedArea, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArea(@PathVariable Long id) {
        if (!areaService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        areaService.deleteArea(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
