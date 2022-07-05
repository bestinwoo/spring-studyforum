package project.aha.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse implements BasicResponse {
	private String errorMessage;
	private String errorCode;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = "400";
	}

	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}
