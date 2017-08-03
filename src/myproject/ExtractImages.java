package myproject;

import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;

public class ExtractImages {

	public static void main(String args[]) throws Exception {
	    PDDocument document = PDDocument.load(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/mediconsult.pdf"));
	    PDPageTree list = document.getPages();
	    for (PDPage page : list) {
	        PDResources pdResources = page.getResources();
	        for (COSName c : pdResources.getXObjectNames()) {
	            PDXObject o = pdResources.getXObject(c);
	            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
	                File file = new File("/Users/henriqueferreira/Documents/PDFBoxImages/"+ System.nanoTime() + ".png");
	                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
	            }
	        }
	    }
	    System.out.println("Images have been extracted successfully! Check your images folder.");
	}
	
}

