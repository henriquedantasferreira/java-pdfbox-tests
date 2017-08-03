package myproject;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;

public class ClipPDFPages {

	public static void main( String[] args ) throws IOException
    {
		PDDocument srcDoc = PDDocument.load(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/mediconsult.pdf"));	    
	    PDPageTree srcPages = srcDoc.getDocumentCatalog().getPages();
	    
	    //The first page is begin = 1 in this case we're extracting from page 2 to page 5 of the pdf
	    PDDocument dstDoc = cutPages(2,5,srcPages);
	    dstDoc.save(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/Clipped/mediconsult.pdf"));
	    dstDoc.close();
	    srcDoc.close();
    }
	
	public static PDDocument cutPages(int begin, int end,  PDPageTree ListOfPages) {
		PDDocument dstDoc = new PDDocument();
		for (int p = 0; p < ListOfPages.getCount(); p++)
	    {
	        if (p >= begin-1 && p <= end-1)
	            dstDoc.addPage(ListOfPages.get(p));
	    }
		return dstDoc;
	}
	
}


