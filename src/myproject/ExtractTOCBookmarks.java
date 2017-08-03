package myproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

public class ExtractTOCBookmarks {
	
	public static void main( String[] args) throws Exception{
		
		PDDocument document = PDDocument.load(new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/GDM.pdf"));
		FileInputStream file = null;
		
		try {
			ExtractTOCBookmarks meta = new ExtractTOCBookmarks();
			PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
			if(outline != null) {
				meta.printBookmark(outline, "");
			}
			else
			{
				System.out.println("This document contains no bookmarks!");
			}
		}
		finally {
			if(file != null) {
				file.close();
			}
			if(document != null) {
				document.close();
			}
		}
		
	}
	
	public void printBookmark( PDOutlineNode bookmark, String indentation ) throws IOException
	{
		PDOutlineItem current = bookmark.getFirstChild();
		while( current != null )
		{
			System.out.println( indentation + current.getTitle() );
			printBookmark( current, indentation + "    " );
			current = current.getNextSibling();
        }
	}
	
}


