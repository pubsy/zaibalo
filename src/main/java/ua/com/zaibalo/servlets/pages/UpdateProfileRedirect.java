package ua.com.zaibalo.servlets.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class UpdateProfileRedirect {

	private static final String TEMP_FOLDER_PATH = "/tmp/";
	@Autowired
	private UsersDAO usersDAO;
	
	public String run(User user, List<FileItem> fileItemsList) throws ServletException, IOException {
		String displayName = null;
		String newPassword = null;
		String repeatPassword = null;
		File newAvatar = null;
		String about = null;
		boolean notifyOnPM = false;

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
				newAvatar = new File(TEMP_FOLDER_PATH + item.getName());
				try {
					item.write(newAvatar);
				} catch (Exception e) {
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
		
		returnStatus = updateUserAvatar(user, newAvatar);
		status.append(returnStatus);
		
		return status.toString();
	}

	private String updateUserAbout(User user, String about){
		if(about == null || "".equals(about)){
			return "";
		}
		
		if(!about.equals(user.getAbout())){
			user.setAbout(about);
			usersDAO.updateUserAbout(user, about);
			return StringHelper.getLocalString("user_about_success");
		}
		
		return "";
		
	}
	
	private String updateUserNotifyOnPM(User user, boolean notifyOnPM){
		if(user.isNotifyOnPM() == notifyOnPM){
			return "";
		}else{
			user.setNotifyOnPM(notifyOnPM);
			usersDAO.updateUserNotifyOnPM(user, notifyOnPM);
			return StringHelper.getLocalString("email_notification_settings_changed");
		}
	}

	private String updateUserAvatar(User user, File savedFile) throws IOException{
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
		
		if(!ext.equalsIgnoreCase("gif") && !ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png") && !ext.equalsIgnoreCase("jpeg")){
			return StringHelper.getLocalString("usupported_image_format", ext);
		}
		
		BufferedImage img = ImageIO.read(savedFile);
		
		BufferedImage bigThumbnail = Scalr.resize(img, 100);
		BufferedImage smallThumbnail = Scalr.resize(img, 50);

		//String userPhoteDirPath = SpringPropertiesUtil.getProperty(ZaibaloConstants.USERPHOTO_DIR_PROP_NAME) + File.separator;
		String bigOutputFileKey = TEMP_FOLDER_PATH + userId  + "." + ext;
		File bigOutputfile = new File(bigOutputFileKey);
		if(bigOutputfile.exists()){
			bigOutputfile.delete();
		}
		ImageIO.write(bigThumbnail, ext, bigOutputfile);
		
		AmazonS3 s3Client = new AmazonS3Client();
		String bucketName = "z-avatars";
		s3Client.putObject(new PutObjectRequest(bucketName, userId  + "." + ext, bigOutputfile));
		
		File smallOutputfile = new File(TEMP_FOLDER_PATH + userId + ".thumbnail" + "." + ext);
		if(smallOutputfile.exists()){
			smallOutputfile.delete();
		}
		ImageIO.write(smallThumbnail, ext, smallOutputfile);
		
		s3Client.putObject(new PutObjectRequest(bucketName, userId + ".thumbnail" + "." + ext, smallOutputfile));		
		
		user.setBigImgPath(User.IMAGES_PATH_URL + userId  + "." + ext);
		usersDAO.updateUserImage(user, User.IMAGES_PATH_URL + userId  + "." + ext);
		
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
				usersDAO.updateUserPassword(user, newPassword);
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
			usersDAO.updateUserDisplayName(user, value);
			return StringHelper.getLocalString("user_diaplay_name_success");
		}
		
		return "";
	}

}
