package myproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

public final class ExtractEmbeddedFiles
{
    public static void main( String[] args ) throws IOException
    {
	    	PDDocument doc = PDDocument.load(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/freedom.pdf"));
	    	for (int p = 0; p < doc.getNumberOfPages(); ++p)
	    	{
	    	    PDPage page = doc.getPage(p);
	    	    List<PDAnnotation> annotations = page.getAnnotations();
	    	    for (PDAnnotation ann : annotations)
	    	    {
	    	        if ("RichMedia".equals(ann.getSubtype()))
	    	        {
	    	            COSArray array = (COSArray) ann.getCOSObject().getObjectFromPath("RichMediaContent/Assets/Names/");
	    	            String name = array.getString(0);
	    	            COSDictionary filespec = (COSDictionary) array.getObject(1);
	    	            PDComplexFileSpecification cfs = new PDComplexFileSpecification(filespec);
	    	            PDEmbeddedFile embeddedFile = cfs.getEmbeddedFile();
	    	            File file = new File("/Users/henriqueferreira/Documents/PDFBoxFiles/"+name);
	    	            OutputStream out = new FileOutputStream(file);
	    	            out.write(embeddedFile.toByteArray());
	    	            out.close();
	    	            System.out.println("page: " + (p+1) + ", name: " + name + ", size: " + embeddedFile.createInputStream().available());
	    	        }
	    	    }
	    	}
    }
}

