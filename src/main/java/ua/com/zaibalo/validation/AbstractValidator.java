package ua.com.zaibalo.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractValidator {

	protected List<String> validationErrors = new ArrayList<String>();
	
	public void validateNotEmptyString(String str, String message){
		if(str == null){
			return;
		}
		if("".equals(str.trim())){
			validationErrors.add(message);
		}
	}
	
	public void validateNotEmptyCollection(Collection<?> col, String message){
		if(col == null || col.isEmpty()){
			validationErrors.add(message);
		}
	}
	
	public String getErrors(){
		StringBuffer buf = new StringBuffer();
		for(String error: validationErrors){
			buf.append(error + "; ");
		}
		return buf.toString();
	}

}
