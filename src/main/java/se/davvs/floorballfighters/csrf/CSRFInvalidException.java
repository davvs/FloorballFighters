package se.davvs.floorballfighters.csrf;

import java.io.IOException;

public class CSRFInvalidException extends IOException {

	private static final long serialVersionUID = 2407828977959763406L;

	public CSRFInvalidException(String message){
		super(message);
	}
	
}
