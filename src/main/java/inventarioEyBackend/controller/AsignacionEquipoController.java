package inventarioEyBackend.controller;
import inventarioEyBackend.model.AsignacionEquipo;
import inventarioEyBackend.service.AsignacionEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*", maxAge = 3600)

public class AsignacionEquipoController {
    @Autowired
    private AsignacionEquipoService asignacionService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<AsignacionEquipo>> getAllAsignaciones() {
        List<AsignacionEquipo> asignaciones = asignacionService.getAllAsignaciones();
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<AsignacionEquipo>> getAllAsignacionesPaginated(Pageable pageable) {
        Page<AsignacionEquipo> asignaciones = asignacionService.getAllAsignaciones(pageable);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<AsignacionEquipo> getAsignacionById(@PathVariable Long id) {
        return asignacionService.getAsignacionById(id)
                .map(asignacion -> new ResponseEntity<>(asignacion, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/equipo/{equipoId}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<AsignacionEquipo>> getAsignacionesByEquipoId(@PathVariable Long equipoId) {
        List<AsignacionEquipo> asignaciones = asignacionService.getAsignacionesByEquipoId(equipoId);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<AsignacionEquipo>> getAsignacionesByFuncionarioId(@PathVariable Long funcionarioId) {
        List<AsignacionEquipo> asignaciones = asignacionService.getAsignacionesByFuncionarioId(funcionarioId);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @GetMapping("/area/{areaId}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<AsignacionEquipo>> getAsignacionesByAreaId(@PathVariable Long areaId) {
        List<AsignacionEquipo> asignaciones = asignacionService.getAsignacionesByAreaId(areaId);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionEquipo> createAsignacion(@RequestBody AsignacionEquipo asignacion) {
        AsignacionEquipo newAsignacion = asignacionService.createAsignacion(asignacion);
        return new ResponseEntity<>(newAsignacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AsignacionEquipo> updateAsignacion(@PathVariable Long id, @RequestBody AsignacionEquipo asignacionDetails) {
        if (!asignacionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AsignacionEquipo updatedAsignacion = asignacionService.updateAsignacion(id, asignacionDetails);
        return new ResponseEntity<>(updatedAsignacion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAsignacion(@PathVariable Long id) {
        if (!asignacionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        asignacionService.deleteAsignacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
