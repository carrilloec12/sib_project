package operation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;


public class GetLink extends Operation{

String link;
	
	public GetLink () {
		 
	}
	
	public GetLink(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		link = tokens[1];
		folderPath = tokens[2];
	}
	
	@Override
	String compress() {		
		return "GetLink " + link+ " -folder " + folderPath;
	}

	@Override
	void decompress()  {

		Reader r = null;
		try {
			
			
			URL u = new URL(link);
			InputStream in = u.openStream();
			r = new InputStreamReader(in);
			final File dir = new File(sibPath + "/" + folderPath);
			dir.mkdir();
			ParserDelegator hp = new ParserDelegator();
			hp.parse(r, new HTMLEditorKit.ParserCallback() {
				public void handleStartTag(HTML.Tag t, MutableAttributeSet a,
						int pos) {
					
					
					// System.out.println(t);
					if (t == HTML.Tag.A) {
						
						PrintWriter writer = null;
						File file = new File(dir, "linkresults.txt");
						try {
							writer = new PrintWriter(new FileWriter(file, true));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Enumeration attrNames = a.getAttributeNames();
						StringBuilder b = new StringBuilder();
						while (attrNames.hasMoreElements()) {
							Object key = attrNames.nextElement();
							if ("href".equals(key.toString())) {
								writer.println(a.getAttribute(key));
								//System.out.println(a.getAttribute(key));
							}
						}
						writer.close();
					}
					
					
				}
				
			}, true);
			if (r != null) {
				r.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}