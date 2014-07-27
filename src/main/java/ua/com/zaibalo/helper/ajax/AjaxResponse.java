package ua.com.zaibalo.helper.ajax;

public abstract class AjaxResponse {

	private String status;
	
	public AjaxResponse(boolean success){
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

}