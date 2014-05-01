package operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import com.google.gson.Gson;

public class Retrieve_links extends Operation {

	String keyword;
	final static String apiKey = "AIzaSyDw1BBesKh8qRGxe80zoPJPqcQPSy2GQMg";
	final static String customSearchEngineKey = "003151189391423629409:4ydag-rh9wc";
	final static  String searchURL = "https://www.googleapis.com/customsearch/v1?";
	
	public Retrieve_links() {
	}

	public Retrieve_links(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		keyword = tokens[1];
		folderPath = tokens[3];
	}

	@Override
	String compress() {
		return "Retrieve_links from " + keyword + " -folder " + folderPath;
	}

	@Override
	void decompress() {
		
		String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey+"&q=";
		final File dir = new File(sibPath + "/" + folderPath);
		dir.mkdir();
		
		String[] sentence;
		String search;
		String link;
		String result = null;
		String match = "\"link\":";
	
		int index = 0;
		int start; int numOfResults;
		
		start = 1; numOfResults = 5;
		
		PrintWriter writer = null;
		File file = new File(dir, "linkresults.txt");
		try {
			writer = new PrintWriter(new FileWriter(file, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String keys[]=keyword.split(",");
		for(String key:keys)
		{   
			toSearch += key +"+"; //append the keywords to the url
		}
		
		//specify response format as json
		toSearch+="&alt=json";

		//specify starting result number
		toSearch+="&start="+start;

		//specify the number of results you need from the starting position
		toSearch+="&num="+numOfResults;

	    try
	    {
	        URL url=new URL(toSearch);
	        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
	        BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        StringBuffer buffer=new StringBuffer();
	        while((line=br.readLine())!=null){
	            buffer.append(line);
	        }
	        
	        result = buffer.toString();
	    } catch(Exception e){
	        e.printStackTrace();
	    }
	    

	    sentence = result.split(" ");
		for(String word: sentence)
		{
		    if(word.equals(match)){
		        link = sentence[index+1].replace("\"", "");
		        link = link.replace(",", "");
		    	writer.println(link);
		    }
		    
		    index = index+1;
		}
		writer.close();
	}

}
 