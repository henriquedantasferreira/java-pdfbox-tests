package myproject;

import java.io.File;
import java.io.IOException; 
import java.util.Calendar; 
import java.util.GregorianCalendar;
  
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
	
public class AddDocumentAttributes {
	
   public static void main(String args[]) throws IOException {
	   
		  File file = new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/documentoteste.pdf");
		  PDDocument document = PDDocument.load(file);

	      //Creating the PDDocumentInformation object 
	      PDDocumentInformation pdd = document.getDocumentInformation();
	
	      //Setting the author of the document
	      pdd.setAuthor("Mark Twain");
	       
	      // Setting the title of the document
	      pdd.setTitle("The adventures of Tom Sawyer"); 
	       
	      //Setting the creator of the document 
	      pdd.setCreator("Mark Twain"); 
	       
	      //Setting the subject of the document 
	      pdd.setSubject("Adventures of Tom Sawyer"); 
	       
	      //Setting the created date of the document 
	      Calendar date = new GregorianCalendar();
	      date.set(2017, 8, 16); 
	      pdd.setCreationDate(date);
	      //Setting the modified date of the document 
	      date.set(2017, 8, 16); 
	      pdd.setModificationDate(date); 
	       
	      //Setting keywords for the document 
	      pdd.setKeywords("tom, sawyer, adventure, story"); 
	 
	      //Saving the document 
	      document.save("/Users/henriqueferreira/Documents/PDFBoxDocuments/testnewnew.pdf");
	
	      System.out.println("Properties added successfully ");
	       
	      //Closing the document
	      document.close();

   }
}
