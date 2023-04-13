package BE;
import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;


import static com.itextpdf.kernel.colors.DeviceGray.GRAY;



public class EntranceTicketPDF {
        String path = "billet.pdf";
        PdfWriter pdfWriter;
        PdfDocument pdfDocument;
        Document document;

        ImageData data;

        public void makePdf(String name, String date, String startTime, String endTime, String note,int barCode, int type) throws FileNotFoundException, MalformedURLException {

                 pdfWriter = new PdfWriter(path);
                 pdfDocument = new PdfDocument(pdfWriter);
                 document = new Document(pdfDocument);

                pdfDocument.setDefaultPageSize(PageSize.A4);


                createDivider();
                createOneSpace();
                createHeader(name);
                createBody(date, startTime,endTime);
                createOneSpace();
                createBottom(note);


                createDivider();


                createBarcode(String.format("%08d", barCode), pdfDocument);
                getPicture(type);

                document.close();
        }

               private void createBottom(String note)
               {
                       float oneColumnWidth[] = {400f}; //to kolonner sat i en array
                       Table bund = new Table(oneColumnWidth); //De to kolonner er tilført til en tabel
                       bund.addCell(new Cell().add(new Paragraph(note)).setFontSize(10f).setBorder(Border.NO_BORDER).setBold());

                       document.add(bund);
               }


        private void createBody(String date,String startTime,String endTime)
        {
                float twoColumnWidth[] = {80f, 200f}; //to kolonner sat i en array

                Table body = new Table(twoColumnWidth); //De to kolonner er tilført til en tabel
                body.addCell(new Cell().add(new Paragraph("Dato")).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph(date)).setFontSize(12f).setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph("Tid")).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph(startTime + "-" + endTime)).setFontSize(12f).setBorder(Border.NO_BORDER).setBold());

                document.add(body);
        }


        private void createHeader(String name)
        {
                float oneColumnWidth[] = {400f}; //to kolonner sat i en array

                Table header = new Table(oneColumnWidth); //De to kolonner er tilført til en tabel
                header.addCell(new Cell().add(new Paragraph(name)).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
                document.add(header);
        }

        private void createOneSpace()
        {
                Paragraph oneSp = new Paragraph("\n").setFontSize(6);
                document.add(oneSp); //tilføjer en linje med afstand
        }

        private void createDivider() {

                float fullWidth[] = {600};

                Border gBorder = new SolidBorder(GRAY, 2f);
                Table divider = new Table(fullWidth);
                divider.setBorder(gBorder);
                document.add(divider);
        }
        private  void createBarcode(String code, PdfDocument pdfDoc) {
                BarcodeEAN barcode = new BarcodeEAN(pdfDoc);
                barcode.setCodeType(BarcodeEAN.EAN8);
                barcode.setCode(code);

                // Create barcode object to put it to the cell as image
                PdfFormXObject barcodeObject = barcode.createFormXObject(null, null, pdfDoc);
                Image image = new Image(barcodeObject);
                image.setFixedPosition(400, 725);
                document.add(image);

        }

        private void getPicture(int type) throws FileNotFoundException, MalformedURLException {


                switch (type)
                {
                        case 1:
                                 data= ImageDataFactory.create("Resources/Pictures/pexels-wendy-wei-1540406.jpg");
                                break;
                        case 2:
                                 data= ImageDataFactory.create("Resources/Pictures/vip.jpg");
                                break;
                        case 3:
                                data= ImageDataFactory.create("Resources/Pictures/beer.jpg");
                                break;
                }
                System.out.println(type);
                Image image = new Image(data);
                image.setFixedPosition(1, 1);
                image.setOpacity(0.2f);
                document.add(image);
        }




        public void showPDF() throws IOException {

                boolean filesExits = Files.exists(Path.of(path)); //check om filen eksisterer
                File file = new File(path);

                try {

                        if (filesExits) {

                                Desktop desktop = Desktop.getDesktop();


                                if (file.exists()) desktop.open(file);
                        } //else
                        // informationUser("File do not exist!");
                        // Her kaldes en metode, der viser et vindue med besked om, at filen ikke findes.
                        // Text file, should be opening in default text editor

                } catch (Exception e) {
                        throw new RuntimeException(e);
                }


        }
}
