package project.aha.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result<T> implements BasicResponse {
    private T data;
    private int count;

    public Result(T data) {
        this.data = data;
        if(data instanceof List) {
            this.count = ((List<?>)data).size();

        } else {
            this.count = 1;
        }
    }
}
