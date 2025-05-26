package inventarioEyBackend.service.impl;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Impresora;
import inventarioEyBackend.repository.ImpresoraRepository;
import inventarioEyBackend.service.ImpresoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImpresoraServiceImpl implements ImpresoraService {
    @Autowired
    private ImpresoraRepository impresoraRepository;

    @Override
    public List<Impresora> getAllImpresoras() {
        return impresoraRepository.findAll();
    }

    @Override
    public Optional<Impresora> getImpresoraById(Long id) {
        return impresoraRepository.findById(id);
    }

    @Override
    public Impresora createImpresora(Impresora impresora) {
        return impresoraRepository.save(impresora);
    }

    @Override
    public Impresora updateImpresora(Long id, Impresora impresoraDetails) {
        Impresora impresora = impresoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impresora no encontrada con id: " + id));

        impresora.setNumeroSerie(impresoraDetails.getNumeroSerie());
        impresora.setCodigoPatrimonial(impresoraDetails.getCodigoPatrimonial());
        impresora.setMarca(impresoraDetails.getMarca());
        impresora.setModelo(impresoraDetails.getModelo());
        impresora.setTipoImpresora(impresoraDetails.getTipoImpresora());
        impresora.setTecnologiaImpresion(impresoraDetails.getTecnologiaImpresion());
        impresora.setTipoConexion(impresoraDetails.getTipoConexion());
        impresora.setTieneEscaner(impresoraDetails.getTieneEscaner());
        impresora.setTieneWifi(impresoraDetails.getTieneWifi());
        impresora.setTieneDuplex(impresoraDetails.getTieneDuplex());
        impresora.setEstadoFisico(impresoraDetails.getEstadoFisico());
        impresora.setCondicion(impresoraDetails.getCondicion());
        impresora.setAsignadoA(impresoraDetails.getAsignadoA());
        impresora.setAreaDepartamento(impresoraDetails.getAreaDepartamento());
        impresora.setUbicacionFisica(impresoraDetails.getUbicacionFisica());
        impresora.setFechaAdquisicion(impresoraDetails.getFechaAdquisicion());
        impresora.setProveedor(impresoraDetails.getProveedor());
        impresora.setPrecio(impresoraDetails.getPrecio());
        impresora.setNumeroFactura(impresoraDetails.getNumeroFactura());
        impresora.setGarantiaVigente(impresoraDetails.getGarantiaVigente());
        impresora.setFechaVencimientoGarantia(impresoraDetails.getFechaVencimientoGarantia());
        impresora.setObservaciones(impresoraDetails.getObservaciones());
        impresora.setRecibidoPor(impresoraDetails.getRecibidoPor());

        return impresoraRepository.save(impresora);
    }

    @Override
    public void deleteImpresora(Long id) {
        Impresora impresora = impresoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impresora no encontrada con id: " + id));
        impresoraRepository.delete(impresora);
    }

    @Override
    public boolean existsByNumeroSerie(String numeroSerie) {
        return impresoraRepository.existsByNumeroSerie(numeroSerie);
    }

    @Override
    public Page<Impresora> getAllImpresoras(Pageable pageable) {
        return impresoraRepository.findAll(pageable);
    }
}
