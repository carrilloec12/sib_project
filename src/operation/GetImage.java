package operation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetImage extends Operation {

	String link;

	public GetImage() {

	}

	public GetImage(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		link = tokens[1];
		folderPath = tokens[3];
	}

	@Override
	String compress() {
		return "GetImage " + link;
	}

	@Override
	void decompress() {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File dir = new File(sibPath + "/" + folderPath);
		dir.mkdir();
		Elements links = doc.getElementsByTag("img");
		for (Element link : links) {
			String imageLink = link.attr("src");
			if (imageLink.startsWith("http") == true) {
				System.out.println(imageLink);
				try {
					int index = imageLink.lastIndexOf("/");
					String fileName = imageLink.substring(index + 1);
					URL url = new URL(imageLink);
					InputStream is = url.openStream();
					OutputStream os = new FileOutputStream(sibPath + "/"
							+ folderPath + "/" + fileName);
					byte[] b = new byte[2048];
					int length;

					while ((length = is.read(b)) != -1) {
						os.write(b, 0, length);
					}

					is.close();
					os.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
