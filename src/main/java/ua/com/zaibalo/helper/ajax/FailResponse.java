package ua.com.zaibalo.helper.ajax;

import org.apache.commons.lang.StringUtils;

import ua.com.zaibalo.helper.StringHelper;

public class FailResponse extends AjaxResponse {

	private String message;

	public FailResponse() {
		super(false);
	}

	public FailResponse(String message) {
		this();
		this.message = StringUtils.isBlank(message) ? 
				StringHelper.getLocalString("internal_server_error") : 
				message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
