package utilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFReader {
    private static final Ruling[] EXTERNALLY_DEFINED_RULINGS = {
            new Ruling(new Point2D.Float(320.0f, 285.0f), new Point2D.Float(564.4409f, 285.0f)),
            new Ruling(new Point2D.Float(320.0f, 457.0f), new Point2D.Float(564.4409f, 457.0f)),
            new Ruling(new Point2D.Float(320.0f, 331.0f), new Point2D.Float(564.4409f, 331.0f)),
            new Ruling(new Point2D.Float(320.0f, 315.0f), new Point2D.Float(564.4409f, 315.0f)),
            new Ruling(new Point2D.Float(320.0f, 347.0f), new Point2D.Float(564.4409f, 347.0f)),
            new Ruling(new Point2D.Float(320.0f, 363.0f), new Point2D.Float(564.44088f, 363.0f)),
            new Ruling(new Point2D.Float(320.0f, 379.0f), new Point2D.Float(564.44087f, 379.0f)),
            new Ruling(new Point2D.Float(320.0f, 395.5f), new Point2D.Float(564.44086f, 395.5f)),
            new Ruling(new Point2D.Float(320.00006f, 415.0f), new Point2D.Float(564.4409f, 415.0f)),
            new Ruling(new Point2D.Float(320.00007f, 431.0f), new Point2D.Float(564.4409f, 431.0f)),

            new Ruling(new Point2D.Float(320.0f, 285.0f), new Point2D.Float(320.0f, 457.0f)),
            new Ruling(new Point2D.Float(565.0f, 285.0f), new Point2D.Float(564.4409f, 457.0f)),
            new Ruling(new Point2D.Float(470.5542f, 285.0f), new Point2D.Float(470.36865f, 457.0f))
    };

    public static void extractTable(String fileName) throws IOException {
        // Loading an existing document
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName);   //creating a new file instance

        PDDocument doc = PDDocument.load(file);

        int totalPages = doc.getNumberOfPages();
        System.out.println("Total Pages in Document: " + totalPages);

        ObjectExtractor oe = new ObjectExtractor(doc);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        for (int k = 1; k <= totalPages; k++) {
            Page page = oe.extract(k);

            // extract text from the table after detecting
            //   List<Table> table = sea.extract(page, Arrays.asList(EXTERNALLY_DEFINED_RULINGS));
            List<Table> table = sea.extract(page);

            for (Table tables : table) {
                List<List<RectangularTextContainer>> rows = tables.getRows();

                for (int i = 0; i < rows.size(); i++) {
                    List<RectangularTextContainer> cells = rows.get(i);
                    for (int j = 0; j < cells.size(); j++) {
                        System.out.print(cells.get(j).getText() + "|");
                    }
                }
            }
        }
        doc.close();
    }

    public static String extractTexts(String fileName) throws IOException {
        // Loading an existing document
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName);   //creating a new file instance

        PDDocument doc = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(doc);

        //  String[] lines = text.split("\n");
        System.out.println("Text in PDF\n---------------------------------");

        doc.close();
        return text;
    }
}  