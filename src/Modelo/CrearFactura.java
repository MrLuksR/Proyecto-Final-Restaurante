package Modelo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CrearFactura {
    private String path;

    public CrearFactura(String path) {this.path  = path;}

    // Getter
    public String getPath() {return path;}

    // Setter
    public void setPath(String path) {this.path = path;}

    public void crear(String nombre, String personal, LocalDate fecha, LocalTime tiempo, Pedido[] pedido, int propina){
        try {
            // Crear documento
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(getPath()));
            documento.open();

            // Fuentes
            Font fontTitulo = FontFactory.getFont(FontFactory.COURIER_BOLD, 14);
            Font fontNormal = FontFactory.getFont(FontFactory.COURIER, 10);
            Font fontBold = FontFactory.getFont(FontFactory.COURIER_BOLD, 10);

            // Encabezado
            //Paragraph titulo = new Paragraph("***** FACTURA ELECTRÓNICA *****", fontTitulo);
            Image titulo = Image.getInstance("C:\\Users\\range\\IdeaProjects\\Imagenes\\Logo.png");
            titulo.scalePercent(50);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph empresa = new Paragraph(
                    "\nRUC: 214567890019\nEmpresa: LAMATE S.A.\nDirección: Florencio Sánchez 398, Salto 50000",
                    fontNormal);
            empresa.setAlignment(Element.ALIGN_CENTER);
            documento.add(empresa);

            documento.add(new Paragraph("----------------------------------------", fontNormal));

            // Datos del cliente
            documento.add(new Paragraph("Cliente: " + nombre, fontNormal));
            documento.add(new Paragraph("Cajero/a: " + personal, fontNormal));
            documento.add(new Paragraph("Fecha: " + fecha, fontNormal));
            documento.add(new Paragraph("Hora: " + tiempo, fontNormal));
            documento.add(new Paragraph("Factura N°: 0001-00002" + pedido[0].getId(), fontNormal));
            documento.add(new Paragraph("----------------------------------------", fontNormal));

            // Tabla de productos
            PdfPTable tabla = new PdfPTable(4); // 4 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setWidths(new float[]{1f, 4f, 2f, 2f}); // ancho relativo de columnas

            // Encabezados
            tabla.addCell(celda("CANT", fontBold, Element.ALIGN_LEFT));
            tabla.addCell(celda("DESCRIPCIÓN", fontBold, Element.ALIGN_LEFT));
            tabla.addCell(celda("P.UNIT", fontBold, Element.ALIGN_RIGHT));
            tabla.addCell(celda("TOTAL", fontBold, Element.ALIGN_RIGHT));

            // Productos
            for (int i = 0; i < pedido.length; i++){
                String cant = String.valueOf(pedido[i].getCantidad());
                tabla.addCell(celda(cant, fontNormal, Element.ALIGN_LEFT));
                tabla.addCell(celda(pedido[i].getProducto(), fontNormal, Element.ALIGN_LEFT));
                String precio = String.valueOf(pedido[i].getTotales()/pedido[i].getCantidad());
                tabla.addCell(celda(precio, fontNormal, Element.ALIGN_RIGHT));
                String total = String.valueOf(pedido[i].getTotales());
                tabla.addCell(celda(total, fontNormal, Element.ALIGN_RIGHT));}

            documento.add(tabla);

            documento.add(new Paragraph("----------------------------------------", fontNormal));

            // Total
            double finalTotal = 0;
            for (int i = 0; i < pedido.length; i++){
                finalTotal += pedido[i].getTotales();
            }
            Paragraph prop = new Paragraph("Propina: $" + propina, fontNormal);
            prop.setAlignment(Element.ALIGN_RIGHT);
            documento.add(prop);

            double impuesto = (finalTotal * 0.22);
            Paragraph imp = new Paragraph("Impuesto 22%: $" + impuesto, fontNormal);
            imp.setAlignment(Element.ALIGN_RIGHT);
            documento.add(imp);

            Paragraph total = new Paragraph("TOTAL: $" + (finalTotal + impuesto + propina), fontBold);
            total.setAlignment(Element.ALIGN_RIGHT);
            documento.add(total);

            // Pie de página
            Paragraph gracias = new Paragraph("\nGracias por su compra.", fontNormal);
            gracias.setAlignment(Element.ALIGN_CENTER);
            documento.add(gracias);

            // Cerrar documento
            documento.close();

            System.out.println("Factura PDF creada exitosamente en: " + getPath());

        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "Error en el documento:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error de archivo:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método auxiliar para crear celdas de tabla con alineación
    private static PdfPCell celda(String texto, Font font, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setHorizontalAlignment(alineacion);
        celda.setBorder(PdfPCell.NO_BORDER);
        return celda;
    }
}
