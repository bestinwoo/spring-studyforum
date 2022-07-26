package project.aha.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse implements BasicResponse {
	private String errorCode;
	private String errorMessage;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = "400";
	}

	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

}
