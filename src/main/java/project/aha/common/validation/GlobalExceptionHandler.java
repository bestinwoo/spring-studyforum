package project.aha.common.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.aha.common.ErrorResponse;
import project.aha.common.ResourceNotFoundException;

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

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundExceptions(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage(), "404"));
	}
}
