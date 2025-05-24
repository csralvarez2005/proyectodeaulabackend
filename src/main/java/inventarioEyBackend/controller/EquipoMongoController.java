package inventarioEyBackend.controller;

import inventarioEyBackend.model.EquipoMongo;
import inventarioEyBackend.service.EquipoMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reporte-equipos")
public class EquipoMongoController {
    private final EquipoMongoService service;

    @Autowired
    public EquipoMongoController(EquipoMongoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EquipoMongo> obtenerReporte() {
        return service.obtenerTodos();
    }

    @PostMapping
    public void guardarEquipo(@RequestBody EquipoMongo equipo) {
        service.guardar(equipo);
    }
}
