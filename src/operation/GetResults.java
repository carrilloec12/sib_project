package operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetResults extends Operation {

	String link;
	 
	public GetResults() {
	}

	public GetResults(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		link = tokens[1];
		folderPath = tokens[2];
	}

	@Override
	String compress() {
		return "GetResults from " + link + " -folder " + folderPath;
	}

	@Override
	void decompress() {
		
		Document doc = null;
		String text;
		FileReader fileReader = null;
		int index;
		String fileName = "results.txt";
		String l = null;
		//File dir = new File(sibPath + "/" + folderPath);
		//dir.mkdir();
		
		try {
			fileReader = new FileReader(sibPath + "/" + folderPath + "/" + "linkresults.txt");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fileReader);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(sibPath + "/" + folderPath + "/"
					+ fileName);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		 try {
			while ((l = br.readLine()) != null) {
				System.out.print(l);
				System.out.print("\n");
				try {
					doc = Jsoup.connect(l).get();
				} catch (SocketTimeoutException e1) {
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
				
				// Get the text of the html body
			    text = doc.body().text();
				
				pw.println(text);
				pw.println("\n\n\n\n");
				
		   }
		  pw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	
	}

}

