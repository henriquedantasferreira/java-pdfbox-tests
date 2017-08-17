package myproject;

import java.io.File; 
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class ExtractDocumentAttributes {
   public static void main(String args[]) throws IOException {
      
      //Loading an existing document 
      File file = new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/test.pdf");
      PDDocument document = PDDocument.load(file);
      //Getting the PDDocumentInformation object 
      PDDocumentInformation pdd = document.getDocumentInformation();

      //Retrieving the info of a PDF document
      System.out.println("Author :::: "+ pdd.getAuthor());
      System.out.println("Title :::: "+ pdd.getTitle());
      System.out.println("Subject :::: "+ pdd.getSubject());
      System.out.println("Pages :::: "+document.getNumberOfPages());
      System.out.println("Creation Date :::: "+pdd.getCreationDate());

      System.out.println("Creator :::: "+ pdd.getCreator());
      System.out.println("Keywords :::: "+ pdd.getKeywords()); 
       
      //Closing the document 
      document.close();        
   }  
}      

