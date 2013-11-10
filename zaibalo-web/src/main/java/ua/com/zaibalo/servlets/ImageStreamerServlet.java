package ua.com.zaibalo.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.AppProperties;

@WebServlet("/image/*")
public class ImageStreamerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)";
	private static final String USERPHOTO_DIR_PATH = AppProperties.getProperty(ZaibaloConstants.USERPHOTO_DIR_PROP_NAME);
	private static final Pattern pattern = Pattern.compile(IMAGE_PATTERN);

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Get the absolute path of the image
		ServletContext sc = getServletContext();

		String imagePath = req.getPathInfo();
		Matcher matcher = pattern.matcher(imagePath);
		if (!matcher.matches()) {
			System.out.println(("Can't return unimage" + imagePath));
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		String filename = USERPHOTO_DIR_PATH + imagePath;

		// Get the MIME type of the image
		String mimeType = sc.getMimeType(filename);
		if (mimeType == null) {
			System.out.println("Could not get MIME type of " + filename);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		// Set content type
		resp.setContentType(mimeType);

		// Set content size
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("ImageStreamerServlet: Image not found: " + filename);
		}
		resp.setContentLength((int) file.length());

		// Open the file and output streams
		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		in.close();
		out.close();
	}
}
