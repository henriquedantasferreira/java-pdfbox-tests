package myproject;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ExtractCoverPage {

   public static void main(String args[]) throws Exception {

      //Loading an existing PDF document
      PDDocument document = PDDocument.load(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/alzheimer.pdf"));
       
      //Instantiating the PDFRenderer class
      PDFRenderer renderer = new PDFRenderer(document);

      //Rendering an image from the PDF document
      BufferedImage image = renderer.renderImage(0);

      //Writing the image to a file
      ImageIO.write(image, "JPEG", new File("/Users/henriqueferreira/Documents/PDFBoxImages/cover.jpeg"));
       
      System.out.println("Image created");
       
      //Closing the document
      document.close();

   }
}