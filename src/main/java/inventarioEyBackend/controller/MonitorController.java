package inventarioEyBackend.controller;

import inventarioEyBackend.model.Monitor;
import inventarioEyBackend.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitores")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MonitorController {
    @Autowired
    private MonitorService monitorService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Monitor>> getAllMonitores() {
        List<Monitor> monitores = monitorService.getAllMonitores();
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<Monitor>> getAllMonitoresPaginated(Pageable pageable) {
        Page<Monitor> monitores = monitorService.getAllMonitores(pageable);
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Monitor> getMonitorById(@PathVariable Long id) {
        return monitorService.getMonitorById(id)
                .map(monitor -> new ResponseEntity<>(monitor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/marca/{marca}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Monitor>> getMonitoresByMarca(@PathVariable String marca) {
        List<Monitor> monitores = monitorService.findByMarca(marca);
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    @GetMapping("/modelo/{modelo}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Monitor>> getMonitoresByModelo(@PathVariable String modelo) {
        List<Monitor> monitores = monitorService.findByModelo(modelo);
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Monitor>> getMonitoresByEstado(@PathVariable String estado) {
        List<Monitor> monitores = monitorService.findByEstado(estado);
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMonitor(@RequestBody Monitor monitor) {
        if (monitorService.existsByNumeroSerie(monitor.getNumeroSerie())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un monitor con este número de serie");
        }

        Monitor newMonitor = monitorService.createMonitor(monitor);
        return new ResponseEntity<>(newMonitor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Monitor> updateMonitor(@PathVariable Long id, @RequestBody Monitor monitorDetails) {
        if (!monitorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Monitor updatedMonitor = monitorService.updateMonitor(id, monitorDetails);
        return new ResponseEntity<>(updatedMonitor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteMonitor(@PathVariable Long id) {
        if (!monitorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        monitorService.deleteMonitor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
