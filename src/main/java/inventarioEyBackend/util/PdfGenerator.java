package inventarioEyBackend.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import inventarioEyBackend.dto.ReporteAsignacionDTO;
import inventarioEyBackend.model.Equipo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGenerator {

    // Método 1: Reporte de Equipos
    public static ByteArrayInputStream generarReporteEquipos(List<Equipo> equipos) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            for (Equipo equipo : equipos) {
                document.add(new Paragraph("Reporte del Equipo - " + equipo.getCodigoEquipo(), sectionFont));
                document.add(new Paragraph(" "));

                document.add(new Paragraph("Información General", sectionFont));
                document.add(crearTabla(
                        new String[]{"Código", "Descripción", "Tipo", "Modelo", "Marca", "Serie", "Ubicación", "Utilización", "Recibido por"},
                        new String[]{
                                equipo.getCodigoEquipo(), equipo.getDescripcion(), equipo.getTipo(), equipo.getModelo(),
                                equipo.getMarca(), equipo.getSerie(), equipo.getUbicacionDelEquipo(),
                                equipo.getUtilizacion(), equipo.getRecibidoPor()
                        },
                        labelFont, valueFont));

                document.add(new Paragraph("Información de Compra y Garantía", sectionFont));
                document.add(crearTabla(
                        new String[]{"Proveedor", "Orden de Compra", "Factura", "Fecha de Compra", "Fecha Fin Garantía", "Garantía", "Precio"},
                        new String[]{
                                equipo.getProveedor(), equipo.getOrdenDeCompra(), equipo.getFactura(),
                                equipo.getFechaDeCompra() != null ? equipo.getFechaDeCompra().toString() : "",
                                equipo.getFechaFinGarantia() != null ? equipo.getFechaFinGarantia().toString() : "",
                                equipo.getGarantia(), equipo.getPrecio() != null ? equipo.getPrecio().toString() : ""
                        },
                        labelFont, valueFont));

                document.add(new Paragraph("Hardware", sectionFont));
                document.add(crearTabla(
                        new String[]{"Procesador", "RAM (GB)", "Almacenamiento (GB)", "Tipo Almacenamiento", "Placa Base", "Fuente (W)", "Tarjeta Gráfica", "Red", "Sonido", "Gabinete", "Perif. Entrada", "Perif. Salida", "Componentes", "Accesorios"},
                        new String[]{
                                equipo.getProcesador(), String.valueOf(equipo.getMemoriaRamGB()), String.valueOf(equipo.getAlmacenamientoGB()),
                                equipo.getTipoAlmacenamiento(), equipo.getPlacaBase(), String.valueOf(equipo.getFuentePoderWatts()),
                                equipo.getTarjetaGrafica(),
                                equipo.getTieneTarjetaRed() != null && equipo.getTieneTarjetaRed() ? "Sí" : "No",
                                equipo.getTieneTarjetaSonido() != null && equipo.getTieneTarjetaSonido() ? "Sí" : "No",
                                equipo.getGabinete(), equipo.getPerifericosEntrada(), equipo.getPerifericosSalida(),
                                equipo.getComponentes(), equipo.getAccesorios()
                        },
                        labelFont, valueFont));

                document.add(new Paragraph("Software", sectionFont));
                document.add(crearTabla(
                        new String[]{"Sistema Operativo", "Versión SO", "Drivers Instalados", "Programas Instalados", "Utilidades del Sistema"},
                        new String[]{
                                equipo.getSistemaOperativo(), equipo.getVersionSO(), equipo.getDriversInstalados(),
                                equipo.getProgramasInstalados(), equipo.getUtilidadesSistema()
                        },
                        labelFont, valueFont));

                document.add(new Paragraph("Red / Configuración Técnica", sectionFont));
                document.add(crearTabla(
                        new String[]{"Dirección IP", "Dirección MAC", "Estado"},
                        new String[]{
                                equipo.getDireccionIP(), equipo.getDireccionMAC(), equipo.getEstado()
                        },
                        labelFont, valueFont));

                document.add(new Paragraph(" "));
                document.add(new LineSeparator());
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método 2: Reporte de Asignaciones
    public static ByteArrayInputStream generarReporteAsignaciones(List<ReporteAsignacionDTO> datos) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            Paragraph titulo = new Paragraph("Reporte de Asignaciones de Equipos", headFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 3, 4, 3});

            Stream.of("Área", "Código Equipo", "Funcionario", "Fecha Asignación")
                    .forEach(header -> {
                        PdfPCell hcell = new PdfPCell(new Phrase(header, headFont));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(hcell);
                    });

            for (ReporteAsignacionDTO dato : datos) {
                table.addCell(dato.getNombreArea());
                table.addCell(dato.getCodigoEquipo());
                table.addCell(dato.getFuncionario());
                table.addCell(dato.getFechaAsignacion().toString());
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método auxiliar común
    private static PdfPTable crearTabla(String[] etiquetas, String[] valores, Font labelFont, Font valueFont) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        for (int i = 0; i < etiquetas.length; i++) {
            PdfPCell labelCell = new PdfPCell(new Phrase(etiquetas[i], labelFont));
            PdfPCell valueCell = new PdfPCell(new Phrase(valores[i] != null ? valores[i] : "", valueFont));
            table.addCell(labelCell);
            table.addCell(valueCell);
        }
        return table;
    }
}

