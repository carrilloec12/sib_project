package operation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class HttpDownloadUtility extends Operation {
	static String fileURL;
	static String saveDir;
	private static final int BUFFER_SIZE = 4096;
	
	public HttpDownloadUtility() {
	}
	
	public HttpDownloadUtility(String sib, String line){
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		fileURL = tokens[1];
		saveDir = tokens[3];
	}

	public static void downloadFile()
			throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
		httpConn.connect();
		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10,
							disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
						fileURL.length());
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out
					.println("No file to download. Server replied HTTP code: "
							+ responseCode);
		}
		httpConn.disconnect();
	}
	
	String compress() {
		return "DownloadFile " + fileURL + " -folder " + saveDir;
	}

	void decompress() {
		Reader r = null;
		try {
			URL u = new URL(fileURL);
			InputStream in = u.openStream();
			r = new InputStreamReader(in);
			final File dir = new File(sibPath + "/" + folderPath);
			dir.mkdir();
		System.out.println(new Date().toString());

		downloadFile();
		System.out.println(new Date().toString());
	} catch (IOException ex) {
		ex.printStackTrace();
	}
	}
}
