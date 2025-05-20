package inventarioEyBackend.service.impl;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.repository.EquipoRepository;
import inventarioEyBackend.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Page<Equipo> getAllEquipos(Pageable pageable) {
        return equipoRepository.findAll(pageable);
    }

    @Override
    public Optional<Equipo> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    @Override
    @Transactional
    public Equipo createEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    @Transactional
    public Equipo updateEquipo(Long id, Equipo equipoDetails) {
        return equipoRepository.findById(id)
                .map(equipo -> {
                    // Información general
                    equipo.setCodigoEquipo(equipoDetails.getCodigoEquipo());
                    equipo.setDescripcion(equipoDetails.getDescripcion());
                    equipo.setTipo(equipoDetails.getTipo());
                    equipo.setModelo(equipoDetails.getModelo());
                    equipo.setMarca(equipoDetails.getMarca());
                    equipo.setSerie(equipoDetails.getSerie());
                    equipo.setUbicacionDelEquipo(equipoDetails.getUbicacionDelEquipo());
                    equipo.setUtilizacion(equipoDetails.getUtilizacion());
                    equipo.setRecibidoPor(equipoDetails.getRecibidoPor());

                    // Información de compra y garantía
                    equipo.setProveedor(equipoDetails.getProveedor());
                    equipo.setOrdenDeCompra(equipoDetails.getOrdenDeCompra());
                    equipo.setFactura(equipoDetails.getFactura());
                    equipo.setFechaDeCompra(equipoDetails.getFechaDeCompra());
                    equipo.setFechaFinGarantia(equipoDetails.getFechaFinGarantia());
                    equipo.setGarantia(equipoDetails.getGarantia());
                    equipo.setPrecio(equipoDetails.getPrecio());

                    // Hardware
                    equipo.setProcesador(equipoDetails.getProcesador());
                    equipo.setMemoriaRamGB(equipoDetails.getMemoriaRamGB());
                    equipo.setAlmacenamientoGB(equipoDetails.getAlmacenamientoGB());
                    equipo.setTipoAlmacenamiento(equipoDetails.getTipoAlmacenamiento());
                    equipo.setPlacaBase(equipoDetails.getPlacaBase());
                    equipo.setFuentePoderWatts(equipoDetails.getFuentePoderWatts());
                    equipo.setTarjetaGrafica(equipoDetails.getTarjetaGrafica());
                    equipo.setTieneTarjetaRed(equipoDetails.getTieneTarjetaRed());
                    equipo.setTieneTarjetaSonido(equipoDetails.getTieneTarjetaSonido());
                    equipo.setGabinete(equipoDetails.getGabinete());
                    equipo.setPerifericosEntrada(equipoDetails.getPerifericosEntrada());
                    equipo.setPerifericosSalida(equipoDetails.getPerifericosSalida());
                    equipo.setComponentes(equipoDetails.getComponentes());
                    equipo.setAccesorios(equipoDetails.getAccesorios());

                    // Software
                    equipo.setSistemaOperativo(equipoDetails.getSistemaOperativo());
                    equipo.setVersionSO(equipoDetails.getVersionSO());
                    equipo.setDriversInstalados(equipoDetails.getDriversInstalados());
                    equipo.setProgramasInstalados(equipoDetails.getProgramasInstalados());
                    equipo.setUtilidadesSistema(equipoDetails.getUtilidadesSistema());

                    // Red / Configuración técnica
                    equipo.setDireccionIP(equipoDetails.getDireccionIP());
                    equipo.setDireccionMAC(equipoDetails.getDireccionMAC());

                    // Estado
                    equipo.setEstado(equipoDetails.getEstado());

                    return equipoRepository.save(equipo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public void deleteEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + id));
        equipoRepository.delete(equipo);
    }

    @Override
    public List<Equipo> findByTipo(String tipo) {
        return equipoRepository.findByTipo(tipo);
    }

    @Override
    public List<Equipo> findByMarca(String marca) {
        return equipoRepository.findByMarca(marca);
    }

    @Override
    public List<Equipo> findByEstado(String estado) {
        return equipoRepository.findByEstado(estado);
    }

    @Override
    public boolean existsById(Long id) {
        return equipoRepository.existsById(id);
    }
}
