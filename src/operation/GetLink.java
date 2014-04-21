package operation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;


public class GetLink extends Operation{

String link;
	
	//int count=0;
	
	
	public GetLink () {
		 
	}
	
	
	@Override
	String compress() {		
			return "Get Link " + link;
	}

	@Override
	void decompress() throws Exception  {

		try   {
			URL u = new URL(link);
			InputStream in = u.openStream();
			r = new InputStreamReader(in);

			ParserDelegator hp = new ParserDelegator();
			hp.parse(r, new HTMLEditorKit.ParserCallback() {
				public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
					// System.out.println(t);
					if(t == HTML.Tag.A)  {
						Enumeration attrNames = a.getAttributeNames();
						StringBuilder b = new StringBuilder();
						while(attrNames.hasMoreElements())    {
							Object key = attrNames.nextElement();
							if("href".equals(key.toString())) {
								//link = link + a.getAttribute(key) + "\n";
								System.out.println(a.getAttribute(key));
							}
						}
					}
				}
			}, true);
		}finally {
			if(r != null)  {
				r.close();
			}
		}   
}