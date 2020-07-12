package utilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PDFReader {

    public static void main(String[] args) throws IOException {

        //Loading an existing document  
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "bids for transport.pdf");   //creating a new file instance
        PDDocument doc = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(doc);
        String[] lines = text.split("\n");
        System.out.println("Text in PDF\n---------------------------------");
        System.out.println("Project " + lines[2]);

        String[] headers = new String[10];
        for(int i = 3; i< 12; i++) {
            System.out.println("Header " + lines[i]);
            headers[i-3] = lines[i];
        }
        System.out.println("Headers " + text.substring(text.indexOf("Serial"), text.lastIndexOf("Remarks")));


        //Closing the document
        doc.close();
    }
}  