package ua.com.zaibalo.helper;

public class AjaxResponse {

	private String status;
	private String message;
	private Object object;
	
	public AjaxResponse(boolean success, String message){
		this.message = message;
		if(success){
			this.status = "success";
		}else{
			this.status = "fail";
		}
	}
	
	public AjaxResponse(boolean success, Object object){
		this.setObject(object);
		if(success){
			this.status = "success";
		}else{
			this.status = "fail";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}
}