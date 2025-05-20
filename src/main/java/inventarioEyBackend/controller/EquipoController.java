package inventarioEyBackend.controller;

import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        List<Equipo> equipos = equipoService.getAllEquipos();
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<Equipo>> getAllEquiposPaginated(Pageable pageable) {
        Page<Equipo> equipos = equipoService.getAllEquipos(pageable);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return equipoService.getEquipoById(id)
                .map(equipo -> new ResponseEntity<>(equipo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Equipo>> getEquiposByTipo(@PathVariable String tipo) {
        List<Equipo> equipos = equipoService.findByTipo(tipo);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/marca/{marca}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Equipo>> getEquiposByMarca(@PathVariable String marca) {
        List<Equipo> equipos = equipoService.findByMarca(marca);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Equipo>> getEquiposByEstado(@PathVariable String estado) {
        List<Equipo> equipos = equipoService.findByEstado(estado);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) {
        Equipo newEquipo = equipoService.createEquipo(equipo);
        return new ResponseEntity<>(newEquipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable Long id, @RequestBody Equipo equipoDetails) {
        if (!equipoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Equipo updatedEquipo = equipoService.updateEquipo(id, equipoDetails);
        return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteEquipo(@PathVariable Long id) {
        if (!equipoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        equipoService.deleteEquipo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
