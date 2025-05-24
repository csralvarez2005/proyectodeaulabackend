package inventarioEyBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventario_areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;

    @Column(length = 255)
    private String nombre;

    @Column(length = 255)
    private String tipo;
    @JsonIgnore
    @ManyToMany(mappedBy = "areas", fetch = FetchType.LAZY)
    private List<Equipo> equipos = new ArrayList<>();

    @Column(length = 10, nullable = false, unique = true)
    private String abreviatura;
}

