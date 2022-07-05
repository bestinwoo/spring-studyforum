package project.aha.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse implements BasicResponse {
	private String errorCode;
	private List<String> errorMessages;

	public ErrorResponse(String errorMessage) {
		this.errorMessages = new ArrayList<>();
		this.errorMessages.add(errorMessage);
		this.errorCode = "400";
	}

	public ErrorResponse(List<String> errorMessages, String errorCode) {
		this.errorMessages = errorMessages;
		this.errorCode = errorCode;
	}

	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessages = new ArrayList<>();
		this.errorMessages.add(errorMessage);
		this.errorCode = errorCode;
	}

}
