package inventarioEyBackend.mapper;
import inventarioEyBackend.model.AsignacionEquipo;
import inventarioEyBackend.model.AsignacionEquipoMongo;

public class AsignacionEquipoMapper {
    public static AsignacionEquipoMongo toMongo(AsignacionEquipo asignacion) {
        AsignacionEquipoMongo mongo = new AsignacionEquipoMongo();

        mongo.setId(asignacion.getId().toString());
        mongo.setFechaAsignacion(asignacion.getFechaAsignacion());

        if (asignacion.getArea() != null) {
            mongo.setAreaNombre(asignacion.getArea().getNombre());
        }

        if (asignacion.getEquipo() != null) {
            mongo.setEquipoNombre(asignacion.getEquipo().getCodigoEquipo());
        }

        if (asignacion.getFuncionario() != null) {
            mongo.setFuncionarioNombre(asignacion.getFuncionario().getNombreFuncionario()
                    + " " + asignacion.getFuncionario().getApellidoFuncionario());
        }

        return mongo;
    }
}
