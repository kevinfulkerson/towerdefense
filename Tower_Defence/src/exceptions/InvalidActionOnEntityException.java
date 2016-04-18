package exceptions;

public class InvalidActionOnEntityException extends Exception{

	private static final long serialVersionUID = -2607995415288718833L;
	
	public InvalidActionOnEntityException() {
		super();
	}

	public InvalidActionOnEntityException(String message) {
		super(message);
	}
}
