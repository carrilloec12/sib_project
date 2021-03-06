package operation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetText extends Operation {

	String link;

	public GetText() {
	}

	public GetText(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		link = tokens[1];
		folderPath = tokens[3];
	}

	@Override
	String compress() {
		return "GetText " + link + " -folder " + folderPath;
	}

	@Override
	void decompress() {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Get the text of the html body
		String text = doc.body().text();

		try {
			// Create a folder
			File dir = new File(sibPath + "/" + folderPath);
			dir.mkdir();

			// Get the file name
			int index = link.lastIndexOf("/");
			String fileName = link.substring(index + 1);

			// Create a new file in our system with path = sibPath/folderPath/fileName
			PrintWriter pw = new PrintWriter(sibPath + "/" + folderPath + "/"
					+ fileName);
			pw.println(text);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
