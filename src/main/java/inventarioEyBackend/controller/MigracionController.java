package inventarioEyBackend.controller;

import inventarioEyBackend.service.MigracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/migracion")
@CrossOrigin(origins = "*")
public class MigracionController {

    private final MigracionService migracionService;

    @Autowired
    public MigracionController(MigracionService migracionService) {
        this.migracionService = migracionService;
    }

    /**
     * Endpoint para forzar la migración manualmente.
     */
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PostMapping("/forzar")
    public ResponseEntity<Map<String, Object>> migrarManualmente() {
        int cantidad = migracionService.migrarForzado();
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Migración forzada completada.");
        response.put("cantidad", cantidad);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para descargar un PDF con los equipos migrados.
     */
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @GetMapping("/reporte")
    public ResponseEntity<InputStreamResource> exportarReporteEquiposMigrados() {
        ByteArrayInputStream bis = migracionService.migrarYGenerarPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_equipos_migrados.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}