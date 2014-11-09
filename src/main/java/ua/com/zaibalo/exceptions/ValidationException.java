package ua.com.zaibalo.exceptions;

public class ValidationException extends Exception{

    private static final long serialVersionUID = 1L;
    
    public ValidationException(String message){
    	super(message);
    }

}
