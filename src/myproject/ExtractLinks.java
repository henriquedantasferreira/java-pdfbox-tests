package myproject;


import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class ExtractLinks {

	public static void main(String args[]) throws IOException {
		
		//Loading an existing document 
		File file = new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/mediconsult.pdf");
		PDDocument doc = PDDocument.load(file);
		int pageNum = 0;
		
		for( PDPage page : doc.getPages() )
		{
		    pageNum++;
		    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		    List<PDAnnotation> annotations = page.getAnnotations();
		    
		    //first setup text extraction regions
		    for( int j=0; j<annotations.size(); j++ )
		    {
		        PDAnnotation annot = annotations.get(j);
		        if( annot instanceof PDAnnotationLink )
		        {
		            PDAnnotationLink link = (PDAnnotationLink)annot;
		            PDRectangle rect = link.getRectangle();
		            
		            //need to reposition link rectangle to match text space
		            float x = rect.getLowerLeftX();
		            float y = rect.getUpperRightY();
		            float width = rect.getWidth();
		            float height = rect.getHeight();
		            int rotation = page.getRotation();
		            if( rotation == 0 )
		            {
		                PDRectangle pageSize = page.getMediaBox();
		                y = pageSize.getHeight() - y;
		            }
		            else if( rotation == 90 )
		            {
		                //do nothing
		            }
	
		            Rectangle2D.Float awtRect = new Rectangle2D.Float( x,y,width,height );
		            stripper.addRegion( "" + j, awtRect );
		        }
		    }
	
		    stripper.extractRegions( page ); 
	
		    for( int j=0; j<annotations.size(); j++ )
		    {
		        PDAnnotation annot = annotations.get(j);
		        if( annot instanceof PDAnnotationLink )
		        {
		            PDAnnotationLink link = (PDAnnotationLink)annot;
		            PDAction action = link.getAction();
		            String urlText = stripper.getTextForRegion( "" + j );
		            if( action instanceof PDActionURI )
		            {
		                PDActionURI uri = (PDActionURI)action;
		                System.out.println( "Page " + pageNum +":'" + urlText.trim() + "'=" + uri.getURI() );
		            }
		        }
		    }
		}
	}
}


