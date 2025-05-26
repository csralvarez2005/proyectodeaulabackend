package inventarioEyBackend.service;



import inventarioEyBackend.model.Impresora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ImpresoraService {
    List<Impresora> getAllImpresoras();
    Optional<Impresora> getImpresoraById(Long id);
    Impresora createImpresora(Impresora impresora);
    Impresora updateImpresora(Long id, Impresora impresoraDetails);
    void deleteImpresora(Long id);
    boolean existsByNumeroSerie(String numeroSerie);
    Page<Impresora> getAllImpresoras(Pageable pageable);
}