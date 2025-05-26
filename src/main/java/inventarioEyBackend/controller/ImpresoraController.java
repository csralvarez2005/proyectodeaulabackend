package inventarioEyBackend.controller;

import inventarioEyBackend.model.Impresora;
import inventarioEyBackend.service.ImpresoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/impresoras")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImpresoraController {

    @Autowired
    private ImpresoraService impresoraService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Impresora>> getAllImpresoras() {
        return new ResponseEntity<>(impresoraService.getAllImpresoras(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Impresora> getImpresoraById(@PathVariable Long id) {
        return impresoraService.getImpresoraById(id)
                .map(impresora -> new ResponseEntity<>(impresora, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createImpresora(@RequestBody Impresora impresora) {
        if (impresoraService.existsByNumeroSerie(impresora.getNumeroSerie())) {
            return ResponseEntity.badRequest().body("Error: Ya existe una impresora con este número de serie");
        }
        Impresora nueva = impresoraService.createImpresora(impresora);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Impresora> updateImpresora(@PathVariable Long id, @RequestBody Impresora impresoraDetails) {
        if (!impresoraService.getImpresoraById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Impresora actualizada = impresoraService.updateImpresora(id, impresoraDetails);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteImpresora(@PathVariable Long id) {
        if (!impresoraService.getImpresoraById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        impresoraService.deleteImpresora(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<Impresora>> getAllImpresorasPaginated(Pageable pageable) {
        return new ResponseEntity<>(impresoraService.getAllImpresoras(pageable), HttpStatus.OK);
    }
}
