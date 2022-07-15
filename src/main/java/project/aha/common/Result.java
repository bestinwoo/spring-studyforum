package project.aha.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> implements BasicResponse {
	private T data;
	private int count;

	public Result(T data) {
		this.data = data;
		if (data instanceof List) {
			this.count = ((List<?>)data).size();

		} else {
			this.count = 1;
		}
	}

	public Result(T data, int count) {
		this.data = data;
		this.count = count;
	}
}
