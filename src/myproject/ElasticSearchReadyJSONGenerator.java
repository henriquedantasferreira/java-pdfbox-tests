package myproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ElasticSearchReadyJSONGenerator {
	
	private static String author = "";
	private static String title = "";
	private static int pageNumber = 0;
	private static String publishDate = "";
	private static String language = "";
	private static String keywords = "";
	
	public static void main( String[] args) throws Exception{
		
		PDDocument uploadedDocument = LoadDocument("/Users/henriqueferreira/Documents/PDFBoxDocuments/relevant.pdf");
		ExtractMetadata(uploadedDocument);
		int page_number = uploadedDocument.getNumberOfPages();
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String[] content = new String[page_number+1];
		for(int i=1; i<= page_number; i++)
		{
			pdfStripper.setStartPage(i);
			pdfStripper.setEndPage(i);
			content[i] = pdfStripper.getText(uploadedDocument); 			
		}
		GenerateJSON(author, title, pageNumber, publishDate, language, keywords, content);
		uploadedDocument.close();
	}
	
	public static PDDocument LoadDocument(String filePath)
	{
		//Loading an existing document 
		File file = new File(filePath);
		PDDocument document = null;
		try {
			document = PDDocument.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public static void ExtractMetadata(PDDocument document) {
		PDDocumentInformation pdd = document.getDocumentInformation();
		author = pdd.getAuthor();
		title = pdd.getTitle();
		pageNumber = document.getNumberOfPages();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		publishDate = format1.format(pdd.getCreationDate().getTime());
		keywords = pdd.getKeywords();
		if(keywords == null)
			keywords = "";
	}
	
	public static void GenerateJSON(String author, String title, int pageNumber, String publishDate, String language, String keywords, String[] content) throws UnsupportedEncodingException {
		JSONObject obj = new JSONObject();
		obj.put("author", author);
		obj.put("title", title);
		obj.put("number of pages", pageNumber);
		obj.put("publish date", publishDate);
		obj.put("language", language);
		obj.put("keywords", keywords);
 
		JSONArray contentArray = new JSONArray();
		for(int i = 1; i <= pageNumber; i++) {
			contentArray.add("Page "+i+":"+content[i].replaceAll("'"," ").replace("\n", " ").replace("\t"," "));
		}
		obj.put("page content", contentArray);
		
		String uniqueID = UUID.randomUUID().toString();
		
		try {
			URL url = new URL(
					"http://localhost:9999/library/document/"+uniqueID);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = obj.toJSONString();

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
