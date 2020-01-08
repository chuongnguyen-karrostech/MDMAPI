package com.MDMREST.util.message;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionReturn {

	private static final Logger log = LoggerFactory.getLogger(ActionReturn.class);

	public String result = "";
	public Object object = "";

	public ActionReturn() {
		super();
	}
	
	public ActionReturn(String result) {
		super();
		this.result = result;
	}

	public ActionReturn(String result, Object obj) {
		super();

		if (obj != null && obj.getClass() == ConstraintViolationException.class) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) obj)
					.getConstraintViolations();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				this.result = result;
				this.object = constraintViolation.getMessage();
				log.error(constraintViolation.getMessage(), constraintViolation);
				break;
			}
		} else if (obj != null && (obj.getClass() == Exception.class)) {
			this.result = result;
			this.object = ((Exception) obj).getMessage();
			if(this.object != null)
				log.error(this.object.toString(), (Exception) obj);
			else
				log.error(result, (Exception) obj);
		} else {
			this.result = result;
			this.object = obj;
			
			if(result.toLowerCase().toLowerCase().equals("false"))
				log.error(result, obj);
		}
	}

	public boolean resultIsId() {
		if (this.result.matches("^[0-9]*$"))
			return true;

		return false;
	}

	public static ActionReturn Response(String result) {
		return new ActionReturn(result);
	}

	public static ActionReturn Response(String result, Object obj) {
		return new ActionReturn(result, obj);
	}

}
