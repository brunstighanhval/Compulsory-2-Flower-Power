package BE;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import static com.itextpdf.kernel.colors.DeviceGray.GRAY;



public class EntranceTicketPDF
{
        public void makePdf() throws FileNotFoundException, MalformedURLException {
                String path = "billet.pdf";
                PdfWriter pdfWriter = new PdfWriter(path);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);


                pdfDocument.setDefaultPageSize(PageSize.A4);
                Document document = new Document(pdfDocument);

                float fullWidth[] = {600};

                Border gBorder = new SolidBorder(GRAY, 2f);
                Table divider = new Table(fullWidth);
                divider.setBorder(gBorder);
                document.add(divider);

                Paragraph oneSp = new Paragraph("\n").setFontSize(6);
                document.add(oneSp); //tilføjer en linje med afstand


                float oneColumnWidth[] = {400f}; //to kolonner sat i en array

                Table header = new Table(oneColumnWidth); //De to kolonner er tilført til en tabel
                header.addCell(new Cell().add(new Paragraph("Fisketur - Varde Å")).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
                document.add(header);


                float twoColumnWidth[] = {80f, 200f}; //to kolonner sat i en array

                Table body = new Table(twoColumnWidth); //De to kolonner er tilført til en tabel
                body.addCell(new Cell().add(new Paragraph("Dato")).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph("12-12-2023")).setFontSize(12f).setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph("Tid")).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setBold());
                body.addCell(new Cell().add(new Paragraph("10:00-14:00")).setFontSize(12f).setBorder(Border.NO_BORDER).setBold());

                document.add(body);

                Table bund = new Table(oneColumnWidth); //De to kolonner er tilført til en tabel
                bund.addCell(new Cell().add(new Paragraph("Du skal huske at indløse statens fisketegn på nettet")).setFontSize(10f).setBorder(Border.NO_BORDER).setBold());
                document.add(oneSp); //tilføjer en linje med afstand
                document.add(bund);

                document.add(oneSp); //tilføjer en linje med afstand
                document.add(divider);


                String imagePath = "G:\\Mit drev\\Datamatiker\\Java\\PDFFire\\src\\Barcode.png";
                ImageData imageData = ImageDataFactory.create(imagePath);

                Image image = new Image(imageData);


                   image.setFixedPosition(300, 670);
                //  image.setOpacity(0.1f);
                document.add(image);


                document.close();
            }
}

