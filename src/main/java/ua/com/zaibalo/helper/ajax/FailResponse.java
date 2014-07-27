package ua.com.zaibalo.helper.ajax;

public class FailResponse extends AjaxResponse{

	private String message;
	
	public FailResponse() {
		super(false);
	}
	
	public FailResponse(String message){
		this();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
