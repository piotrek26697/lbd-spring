package pl.fis.logic;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.JsonObject;

@ControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<String> entityNotFoundHandler(EntityNotFound ex)
	{
		JsonObject message = new JsonObject();
		message.addProperty("status", HttpStatus.NOT_FOUND.toString());
		message.addProperty("message", "resource '" + ex.getMessage() + "' not found");

		return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> constraingViolation(MethodArgumentNotValidException ex)
	{
		JsonObject message = new JsonObject();
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();

		for (ObjectError error : errors)
		{
			String code = error.getCodes()[0];
			message.addProperty(code.substring(code.lastIndexOf('.') + 1), error.getDefaultMessage());
		}
		return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
	}
}
