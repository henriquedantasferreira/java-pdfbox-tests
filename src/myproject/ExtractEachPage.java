package myproject;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractEachPage {
	
	public static void main(String args[]) throws IOException {
	
		//Loading an existing document 
		File file = new File("/Users/henriqueferreira/Documents/PDFBoxDocuments/tomsawyer.pdf");
		PDDocument document = PDDocument.load(file);
		
		int page_number = document.getNumberOfPages();
		
		System.out.println("Number of pages in this document is: "+ page_number);
		//Instantiate PDFTextStripper class
	    PDFTextStripper pdfStripper = new PDFTextStripper();
	     
		for(int i=1; i<=page_number;i++)
		{
			System.out.print("====== Page "+i+": =======\n");
			pdfStripper.setStartPage(i);
			pdfStripper.setEndPage(i);
			//Retrieving text from PDF document
			String text = pdfStripper.getText(document);
	      	System.out.println(text);
		}
		document.close();
	}
	
}


