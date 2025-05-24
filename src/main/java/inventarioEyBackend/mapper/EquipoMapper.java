package inventarioEyBackend.mapper;

import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.EquipoMongo;

public class EquipoMapper {
    public static EquipoMongo toMongo(Equipo equipo) {
        if (equipo == null) return null;

        EquipoMongo mongo = new EquipoMongo();

        // Asignar ID del equipo MySQL como ID en MongoDB
        mongo.setId(String.valueOf(equipo.getId()));

        mongo.setCodigoEquipo(equipo.getCodigoEquipo());
        mongo.setDescripcion(equipo.getDescripcion());
        mongo.setTipo(equipo.getTipo());
        mongo.setModelo(equipo.getModelo());
        mongo.setMarca(equipo.getMarca());
        mongo.setSerie(equipo.getSerie());
        mongo.setUbicacionDelEquipo(equipo.getUbicacionDelEquipo());
        mongo.setUtilizacion(equipo.getUtilizacion());
        mongo.setRecibidoPor(equipo.getRecibidoPor());

        mongo.setProveedor(equipo.getProveedor());
        mongo.setOrdenDeCompra(equipo.getOrdenDeCompra());
        mongo.setFactura(equipo.getFactura());
        mongo.setFechaDeCompra(equipo.getFechaDeCompra());
        mongo.setFechaFinGarantia(equipo.getFechaFinGarantia());
        mongo.setGarantia(equipo.getGarantia());
        mongo.setPrecio(equipo.getPrecio());

        mongo.setProcesador(equipo.getProcesador());
        mongo.setMemoriaRamGB(equipo.getMemoriaRamGB());
        mongo.setAlmacenamientoGB(equipo.getAlmacenamientoGB());
        mongo.setTipoAlmacenamiento(equipo.getTipoAlmacenamiento());
        mongo.setPlacaBase(equipo.getPlacaBase());
        mongo.setFuentePoderWatts(equipo.getFuentePoderWatts());
        mongo.setTarjetaGrafica(equipo.getTarjetaGrafica());
        mongo.setTieneTarjetaRed(equipo.getTieneTarjetaRed());
        mongo.setTieneTarjetaSonido(equipo.getTieneTarjetaSonido());
        mongo.setGabinete(equipo.getGabinete());
        mongo.setPerifericosEntrada(equipo.getPerifericosEntrada());
        mongo.setPerifericosSalida(equipo.getPerifericosSalida());
        mongo.setComponentes(equipo.getComponentes());
        mongo.setAccesorios(equipo.getAccesorios());

        mongo.setSistemaOperativo(equipo.getSistemaOperativo());
        mongo.setVersionSO(equipo.getVersionSO());
        mongo.setDriversInstalados(equipo.getDriversInstalados());
        mongo.setProgramasInstalados(equipo.getProgramasInstalados());
        mongo.setUtilidadesSistema(equipo.getUtilidadesSistema());

        mongo.setDireccionIP(equipo.getDireccionIP());
        mongo.setDireccionMAC(equipo.getDireccionMAC());
        mongo.setEstado(equipo.getEstado());

        // Guardar el nombre del área asociada (asumiendo que usas una sola)
        mongo.setArea(equipo.getArea() != null ? equipo.getArea().getNombre() : null);

        // Si manejas impresora como string, puedes incluirla aquí:
        // mongo.setImpresora(equipo.getImpresora());

        return mongo;
    }
}
