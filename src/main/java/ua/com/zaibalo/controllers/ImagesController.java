package ua.com.zaibalo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.zaibalo.spring.SpringPropertiesUtil;

@Controller
public class ImagesController {

	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = { "/image/{name}.{extension}" }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> image(@PathVariable("name") String name, @PathVariable("extension") String extension,
			@RequestParam(value = "size", required = false) String size) throws IOException{
		
		if(size != null && size.toLowerCase().equals("small")){
			name += ".thumbnail";
		}
		
		name += "." + extension;
		
		String userphotoPath = SpringPropertiesUtil.getProperty("userphoto.path");
	    
	    Path path = Paths.get(userphotoPath + name);
	    InputStream in = Files.newInputStream(path);

	    final HttpHeaders headers = new HttpHeaders();
	    if(name.toLowerCase().endsWith(".jpg")){
	    	headers.setContentType(MediaType.IMAGE_JPEG);
	    } else if(name.toLowerCase().endsWith(".gif")){
	    	headers.setContentType(MediaType.IMAGE_GIF);
	    } else if(name.toLowerCase().endsWith(".png")){
	    	headers.setContentType(MediaType.IMAGE_PNG);
	    }

	    byte[] byteArray = IOUtils.toByteArray(in);
	    
	    return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);
	}
}
