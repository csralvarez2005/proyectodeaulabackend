package inventarioEyBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventario_funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcionario_id")
    private Long id;

    @Column(name = "tipo_documento", length = 255)
    private String tipoDocumento;

    @Column(name = "apellido_funcionario", length = 255)
    private String apellidoFuncionario;

    @Column(length = 255)
    private String cargo;

    @Column(length = 255)
    private String celular;

    @Column(length = 255)
    private String direccion;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String estado;

    @Column(name = "estado_civil", length = 255)
    private String estadoCivil;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 255)
    private String genero;

    @Column(length = 255)
    private String identificacion;

    @Column(name = "nombre_funcionario", length = 255)
    private String nombreFuncionario;
}

