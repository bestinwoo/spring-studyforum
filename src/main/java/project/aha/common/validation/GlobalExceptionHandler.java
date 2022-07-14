package project.aha.common.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.aha.common.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	//Reqeust Body Validation Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest()
			.body(new ErrorResponse(exception.getBindingResult().getFieldError().getDefaultMessage()));
	}

	//Reqeust Param or ModelAttribute Validation Exception
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(BindException exception) {
		return ResponseEntity.badRequest()
			.body(new ErrorResponse(exception.getBindingResult().getFieldError().getDefaultMessage()));
	}
}
