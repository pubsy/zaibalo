package ua.com.zaibalo.servlets.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.spring.SpringPropertiesUtil;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class UpdateProfileRedirect {

	@Autowired
	private UsersDAO usersDAO;
	
	@SuppressWarnings("unchecked")
	public String run(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String displayName = null;
		String newPassword = null;
		String repeatPassword = null;
		File newAvatar = null;
		String about = null;
		boolean notifyOnPM = false;

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException(StringHelper.getLocalString("internal_server_error"));
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(1*1024*1024);
		
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		
		List<FileItem> fileItemsList = null;
		try {
			fileItemsList = (List<FileItem>) upload.parseRequest(request);
		} catch (FileUploadException e) {
			//TODO
			e.printStackTrace();
		}

		for (FileItem item : fileItemsList) {

			if (item.getSize() == 0) {
				continue;
			}
			
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString("utf-8");
				
				
				displayName = "user_display_name".equals(name) ? value : displayName;
				newPassword = "new_password".equals(name) ? value : newPassword;
				repeatPassword = "repeat_password".equals(name) ? value : repeatPassword;
				about = "about_user".equals(name) ? value : about;
				notifyOnPM = "notify_on_pm".equals(name) ? "on".equals(value) : notifyOnPM;

			} else {
				File tempFolder = new File("tmp");
				if (tempFolder.exists()) {
					recursiveDeleteFolder(tempFolder);
				}
				boolean mkdir = tempFolder.mkdir();
				if(!mkdir){
					throw new ServletException("Could not create a directory: " + tempFolder.getAbsolutePath());
				}

				newAvatar = new File("tmp" + File.separator + item.getName());
				try {
					item.write(newAvatar);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		StringBuffer status = new StringBuffer("");
		
		String returnStatus = updateUserDisplayName(user, displayName);
		status.append(returnStatus);
		
		returnStatus = updateUserPassword(user, newPassword, repeatPassword);
		status.append(returnStatus);
		
		returnStatus = updateUserAbout(user, about);
		status.append(returnStatus);
		
		returnStatus = updateUserNotifyOnPM(user, notifyOnPM);
		status.append(returnStatus);
		
		String basePath = request.getServletContext().getRealPath("");
		returnStatus = updateUserAvatar(user, newAvatar, basePath);
		status.append(returnStatus);
		
		request.setAttribute("update_status", status.toString());
		
		return "redirect:/secure/settings";
	}

	private String updateUserAbout(User user, String about){
		if(about == null || "".equals(about)){
			return "";
		}
		
		if(!about.equals(user.getAbout())){
			user.setAbout(about);
			usersDAO.updateUserAbout(user.getId(), about);
			return StringHelper.getLocalString("user_about_success");
		}
		
		return "";
		
	}
	
	private String updateUserNotifyOnPM(User user, boolean notifyOnPM){
		if(user.isNotifyOnPM() == notifyOnPM){
			return "";
		}else{
			user.setNotifyOnPM(notifyOnPM);
			usersDAO.updateUserNotifyOnPM(user.getId(), notifyOnPM);
			return StringHelper.getLocalString("email_notification_settings_changed");
		}
	}

	private String updateUserAvatar(User user, File savedFile, String path) throws IOException{
		if(savedFile == null){
			return "";
		}

		if(savedFile.length() > 1*1024*1024){
			long size = savedFile.length() / (1024 * 1024);
			return StringHelper.getLocalString("the_image_file_is_too_big", size);
		}
		
		int lastdot = savedFile.getName().lastIndexOf(".");
		int userId = user.getId();
		String ext = savedFile.getName().substring(lastdot + 1);
		
		if(!ext.equalsIgnoreCase("gif") && !ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png")){
			return StringHelper.getLocalString("usupported_image_format", ext);
		}
		
		BufferedImage img = ImageIO.read(savedFile);
		
		BufferedImage bigThumbnail = Scalr.resize(img, 100);
		BufferedImage smallThumbnail = Scalr.resize(img, 50);

		//File bigOutputfile = new File(path + File.separator + "img/userphoto/" + name  + "." + ext);
		String userPhoteDirPath = SpringPropertiesUtil.getProperty(ZaibaloConstants.USERPHOTO_DIR_PROP_NAME) + File.separator;
		File bigOutputfile = new File(userPhoteDirPath + userId  + "." + ext);
		if(bigOutputfile.exists()){
			bigOutputfile.delete();
		}
		ImageIO.write(bigThumbnail, ext, bigOutputfile);

		File smallOutputfile = new File(userPhoteDirPath + userId + ".thumbnail" + "." + ext);
		if(smallOutputfile.exists()){
			smallOutputfile.delete();
		}
		ImageIO.write(smallThumbnail, ext, smallOutputfile);
		
		user.setBigImgPath(userId  + "." + ext);
		user.setSmallImgPath(userId + ".thumbnail" + "." + ext);
		
		usersDAO.updateUserImage(user.getId(), userId  + "." + ext, userId + ".thumbnail" + "." + ext);
		
		return StringHelper.getLocalString("user_image_successfully_updated");
	}
	
	private String updateUserPassword(User user, String newPassword, String repeatPassword) {
		if(newPassword == null || "".equals(newPassword)){
			return "";
		}
		
		if(repeatPassword == null || "".equals(repeatPassword)){
			return "";
		}
		
		if(newPassword.equals(repeatPassword)){
			
			//Validate Latin password
			boolean valid = newPassword.matches("(\\w|\\p{Punct})+");
			if(!valid){
				return StringHelper.getLocalString("password_must_be_latin");
			}
			//-------
			
			String hashedPass = MD5Helper.getMD5Of(newPassword);
			if(!user.getPassword().equals(hashedPass)){
				user.setPassword(MD5Helper.getMD5Of(newPassword));
				usersDAO.updateUserPassword(user.getId(), newPassword);
				return StringHelper.getLocalString("user_password_success_updated");
			}else{
				return "";
			}
			
		}else{
			return StringHelper.getLocalString("new_password_and_repeat");
		}
		
		
	}

	private String updateUserDisplayName(User user, String value){
		if(value == null || "".equals(value)){
			return StringHelper.getLocalString("user_display_name_cant");
		}
		
		if(!value.equals(user.getDisplayName())){
			
			if(usersDAO.getUserByDisplayName(value) != null){
				return StringHelper.getLocalString("user_display_name_already_exists", value);
			}
			
			user.setDisplayName(value);
			usersDAO.updateUserDisplayName(user.getId(), value);
			return StringHelper.getLocalString("user_diaplay_name_success");
		}
		
		return "";
	}
	
	private void recursiveDeleteFolder(File dirPath) {
		String[] ls = dirPath.list();

		for (int idx = 0; idx < ls.length; idx++) {
			File file = new File(dirPath, ls[idx]);
			if (file.isDirectory())
				recursiveDeleteFolder(file);
			file.delete();
		}
		
		dirPath.delete();
	}
}
