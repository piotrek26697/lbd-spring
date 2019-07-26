package pl.fis.logic.errors.handlers;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.JsonObject;

import pl.fis.logic.errors.EntityNotFound;

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
		ResourceBundle languageMessages = ResourceBundle.getBundle("LanguageBundle", LocaleContextHolder.getLocale());
		
		JsonObject message = new JsonObject();
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();

		for (ObjectError error : errors)
		{
			String code = error.getCodes()[0];
			String errorName = code.substring(code.lastIndexOf('.') + 1);
			message.addProperty(languageMessages.getString(errorName), languageMessages.getString(errorName+"Message"));
		}
		return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
	}
}
