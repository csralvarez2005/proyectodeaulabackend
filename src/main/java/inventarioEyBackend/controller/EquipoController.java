package inventarioEyBackend.controller;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Area;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.Monitor;
import inventarioEyBackend.service.AreaService;
import inventarioEyBackend.service.EquipoService;
import inventarioEyBackend.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private AreaService areaService;

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

    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return equipoService.getEquipoById(id)
                .map(equipo -> new ResponseEntity<>(equipo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/monitores")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Monitor>> getMonitoresByEquipoId(@PathVariable Long id) {
        if (!equipoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Monitor> monitores = equipoService.getMonitoresByEquipoId(id);
        return new ResponseEntity<>(monitores, HttpStatus.OK);
    }

    // Métodos para gestionar áreas
    @GetMapping("/{id}/areas")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Area>> getAreasByEquipoId(@PathVariable Long id) {
        if (!equipoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Area> areas = equipoService.getAreasByEquipoId(id);
        return new ResponseEntity<>(areas, HttpStatus.OK);
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

    @GetMapping("/sin-monitores")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Equipo>> getEquiposSinMonitores() {
        List<Equipo> equipos = equipoService.findByMonitoresIsEmpty();
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEquipo(@RequestBody Map<String, Object> payload) {
        try {
            // Extraer datos del equipo
            Equipo equipo = new Equipo();

            // Mapear propiedades básicas del equipo desde el payload
            if (payload.containsKey("codigoEquipo")) equipo.setCodigoEquipo((String) payload.get("codigoEquipo"));
            if (payload.containsKey("descripcion")) equipo.setDescripcion((String) payload.get("descripcion"));
            if (payload.containsKey("tipo")) equipo.setTipo((String) payload.get("tipo"));
            if (payload.containsKey("modelo")) equipo.setModelo((String) payload.get("modelo"));
            if (payload.containsKey("marca")) equipo.setMarca((String) payload.get("marca"));
            if (payload.containsKey("serie")) equipo.setSerie((String) payload.get("serie"));
            if (payload.containsKey("ubicacionDelEquipo")) equipo.setUbicacionDelEquipo((String) payload.get("ubicacionDelEquipo"));
            if (payload.containsKey("utilizacion")) equipo.setUtilizacion((String) payload.get("utilizacion"));
            if (payload.containsKey("recibidoPor")) equipo.setRecibidoPor((String) payload.get("recibidoPor"));
            if (payload.containsKey("proveedor")) equipo.setProveedor((String) payload.get("proveedor"));
            if (payload.containsKey("ordenDeCompra")) equipo.setOrdenDeCompra((String) payload.get("ordenDeCompra"));
            if (payload.containsKey("factura")) equipo.setFactura((String) payload.get("factura"));
            if (payload.containsKey("fechaDeCompra")) equipo.setFechaDeCompra(java.time.LocalDate.parse((String) payload.get("fechaDeCompra")));
            if (payload.containsKey("fechaFinGarantia")) equipo.setFechaFinGarantia(java.time.LocalDate.parse((String) payload.get("fechaFinGarantia")));
            if (payload.containsKey("garantia")) equipo.setGarantia((String) payload.get("garantia"));
            if (payload.containsKey("precio")) equipo.setPrecio(Double.valueOf(payload.get("precio").toString()));
            if (payload.containsKey("procesador")) equipo.setProcesador((String) payload.get("procesador"));
            if (payload.containsKey("memoriaRamGB")) equipo.setMemoriaRamGB(Integer.valueOf(payload.get("memoriaRamGB").toString()));
            if (payload.containsKey("almacenamientoGB")) equipo.setAlmacenamientoGB(Integer.valueOf(payload.get("almacenamientoGB").toString()));
            if (payload.containsKey("tipoAlmacenamiento")) equipo.setTipoAlmacenamiento((String) payload.get("tipoAlmacenamiento"));
            if (payload.containsKey("placaBase")) equipo.setPlacaBase((String) payload.get("placaBase"));
            if (payload.containsKey("fuentePoderWatts")) equipo.setFuentePoderWatts(Integer.valueOf(payload.get("fuentePoderWatts").toString()));
            if (payload.containsKey("tarjetaGrafica")) equipo.setTarjetaGrafica((String) payload.get("tarjetaGrafica"));
            if (payload.containsKey("tieneTarjetaRed")) equipo.setTieneTarjetaRed((Boolean) payload.get("tieneTarjetaRed"));
            if (payload.containsKey("tieneTarjetaSonido")) equipo.setTieneTarjetaSonido((Boolean) payload.get("tieneTarjetaSonido"));
            if (payload.containsKey("gabinete")) equipo.setGabinete((String) payload.get("gabinete"));
            if (payload.containsKey("perifericosEntrada")) equipo.setPerifericosEntrada((String) payload.get("perifericosEntrada"));
            if (payload.containsKey("perifericosSalida")) equipo.setPerifericosSalida((String) payload.get("perifericosSalida"));
            if (payload.containsKey("componentes")) equipo.setComponentes((String) payload.get("componentes"));
            if (payload.containsKey("accesorios")) equipo.setAccesorios((String) payload.get("accesorios"));
            if (payload.containsKey("sistemaOperativo")) equipo.setSistemaOperativo((String) payload.get("sistemaOperativo"));
            if (payload.containsKey("versionSO")) equipo.setVersionSO((String) payload.get("versionSO"));
            if (payload.containsKey("driversInstalados")) equipo.setDriversInstalados((String) payload.get("driversInstalados"));
            if (payload.containsKey("programasInstalados")) equipo.setProgramasInstalados((String) payload.get("programasInstalados"));
            if (payload.containsKey("utilidadesSistema")) equipo.setUtilidadesSistema((String) payload.get("utilidadesSistema"));
            if (payload.containsKey("direccionIP")) equipo.setDireccionIP((String) payload.get("direccionIP"));
            if (payload.containsKey("direccionMAC")) equipo.setDireccionMAC((String) payload.get("direccionMAC"));
            if (payload.containsKey("estado")) equipo.setEstado((String) payload.get("estado"));

            // Procesar IDs de monitores si existen
            if (payload.containsKey("monitorIds")) {
                List<Integer> monitorIds = (List<Integer>) payload.get("monitorIds");
                List<Monitor> monitores = new ArrayList<>();

                for (Integer monitorId : monitorIds) {
                    Monitor monitor = monitorService.getMonitorById(monitorId.longValue())
                            .orElseThrow(() -> new RuntimeException("Monitor no encontrado con ID: " + monitorId));
                    monitor.setEquipo(equipo);
                    monitores.add(monitor);
                }

                equipo.setMonitores(monitores);
            }

            // Procesar IDs de áreas si existen
            if (payload.containsKey("areaIds")) {
                List<Integer> areaIds = (List<Integer>) payload.get("areaIds");
                List<Area> areas = new ArrayList<>();

                for (Integer areaId : areaIds) {
                    Area area = areaService.getAreaById(areaId.longValue())
                            .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + areaId));
                    areas.add(area);
                }

                equipo.setAreas(areas);
            }

            Equipo savedEquipo = equipoService.createEquipo(equipo);
            return new ResponseEntity<>(savedEquipo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el equipo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEquipo(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            if (!equipoService.existsById(id)) {
                return new ResponseEntity<>("Equipo no encontrado con id: " + id, HttpStatus.NOT_FOUND);
            }

            Equipo equipo = equipoService.getEquipoById(id).get();

            // Actualizar propiedades básicas del equipo
            if (payload.containsKey("codigoEquipo")) equipo.setCodigoEquipo((String) payload.get("codigoEquipo"));
            if (payload.containsKey("descripcion")) equipo.setDescripcion((String) payload.get("descripcion"));
            if (payload.containsKey("tipo")) equipo.setTipo((String) payload.get("tipo"));
            if (payload.containsKey("modelo")) equipo.setModelo((String) payload.get("modelo"));
            if (payload.containsKey("marca")) equipo.setMarca((String) payload.get("marca"));
            if (payload.containsKey("serie")) equipo.setSerie((String) payload.get("serie"));
            if (payload.containsKey("ubicacionDelEquipo")) equipo.setUbicacionDelEquipo((String) payload.get("ubicacionDelEquipo"));
            if (payload.containsKey("utilizacion")) equipo.setUtilizacion((String) payload.get("utilizacion"));
            if (payload.containsKey("recibidoPor")) equipo.setRecibidoPor((String) payload.get("recibidoPor"));
            if (payload.containsKey("proveedor")) equipo.setProveedor((String) payload.get("proveedor"));
            if (payload.containsKey("ordenDeCompra")) equipo.setOrdenDeCompra((String) payload.get("ordenDeCompra"));
            if (payload.containsKey("factura")) equipo.setFactura((String) payload.get("factura"));
            if (payload.containsKey("fechaDeCompra")) equipo.setFechaDeCompra(java.time.LocalDate.parse((String) payload.get("fechaDeCompra")));
            if (payload.containsKey("fechaFinGarantia")) equipo.setFechaFinGarantia(java.time.LocalDate.parse((String) payload.get("fechaFinGarantia")));
            if (payload.containsKey("garantia")) equipo.setGarantia((String) payload.get("garantia"));
            if (payload.containsKey("precio")) equipo.setPrecio(Double.valueOf(payload.get("precio").toString()));
            if (payload.containsKey("procesador")) equipo.setProcesador((String) payload.get("procesador"));
            if (payload.containsKey("memoriaRamGB")) equipo.setMemoriaRamGB(Integer.valueOf(payload.get("memoriaRamGB").toString()));
            if (payload.containsKey("almacenamientoGB")) equipo.setAlmacenamientoGB(Integer.valueOf(payload.get("almacenamientoGB").toString()));
            if (payload.containsKey("tipoAlmacenamiento")) equipo.setTipoAlmacenamiento((String) payload.get("tipoAlmacenamiento"));
            if (payload.containsKey("placaBase")) equipo.setPlacaBase((String) payload.get("placaBase"));
            if (payload.containsKey("fuentePoderWatts")) equipo.setFuentePoderWatts(Integer.valueOf(payload.get("fuentePoderWatts").toString()));
            if (payload.containsKey("tarjetaGrafica")) equipo.setTarjetaGrafica((String) payload.get("tarjetaGrafica"));
            if (payload.containsKey("tieneTarjetaRed")) equipo.setTieneTarjetaRed((Boolean) payload.get("tieneTarjetaRed"));
            if (payload.containsKey("tieneTarjetaSonido")) equipo.setTieneTarjetaSonido((Boolean) payload.get("tieneTarjetaSonido"));
            if (payload.containsKey("gabinete")) equipo.setGabinete((String) payload.get("gabinete"));
            if (payload.containsKey("perifericosEntrada")) equipo.setPerifericosEntrada((String) payload.get("perifericosEntrada"));
            if (payload.containsKey("perifericosSalida")) equipo.setPerifericosSalida((String) payload.get("perifericosSalida"));
            if (payload.containsKey("componentes")) equipo.setComponentes((String) payload.get("componentes"));
            if (payload.containsKey("accesorios")) equipo.setAccesorios((String) payload.get("accesorios"));
            if (payload.containsKey("sistemaOperativo")) equipo.setSistemaOperativo((String) payload.get("sistemaOperativo"));
            if (payload.containsKey("versionSO")) equipo.setVersionSO((String) payload.get("versionSO"));
            if (payload.containsKey("driversInstalados")) equipo.setDriversInstalados((String) payload.get("driversInstalados"));
            if (payload.containsKey("programasInstalados")) equipo.setProgramasInstalados((String) payload.get("programasInstalados"));
            if (payload.containsKey("utilidadesSistema")) equipo.setUtilidadesSistema((String) payload.get("utilidadesSistema"));
            if (payload.containsKey("direccionIP")) equipo.setDireccionIP((String) payload.get("direccionIP"));
            if (payload.containsKey("direccionMAC")) equipo.setDireccionMAC((String) payload.get("direccionMAC"));
            if (payload.containsKey("estado")) equipo.setEstado((String) payload.get("estado"));

            Equipo updatedEquipo = equipoService.updateEquipo(id, equipo);
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el equipo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    @PostMapping("/{equipoId}/monitores/{monitorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> agregarMonitor(@PathVariable Long equipoId, @PathVariable Long monitorId) {
        try {
            Equipo equipo = equipoService.agregarMonitor(equipoId, monitorId);
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{equipoId}/monitores/{monitorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> quitarMonitor(@PathVariable Long equipoId, @PathVariable Long monitorId) {
        try {
            Equipo equipo = equipoService.quitarMonitor(equipoId, monitorId);
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para generar código de equipo por abreviatura
    @GetMapping("/generar-codigo")
    public String generarCodigoEquipo(@RequestParam String abreviatura) {
        return equipoService.generarCodigoEquipo(abreviatura);
    }

}