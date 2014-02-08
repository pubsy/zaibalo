package ua.com.zaibalo.helper.ajax;

public class SuccessResponse extends AjaxResponse{

	private Object object;

	public SuccessResponse(){
		super(true);
	}
	
	public SuccessResponse(Object object){
		super(true);
		this.object = object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}
}
