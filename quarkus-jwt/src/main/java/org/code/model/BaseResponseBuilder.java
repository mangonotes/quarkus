package org.code.model;

public class BaseResponseBuilder<T> {
    private Status status;
    private String error;
    private T data;

    public BaseResponseBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public BaseResponseBuilder setError(String error) {
        this.error = error;
        return this;
    }

    public BaseResponseBuilder setData(T data) {
        this.data = data;
        return this;
    }

    public BaseResponseBuilder ofError(String error){
        this.error=error;
        this.status = Status.FAILURE;
        return this;
    }

    public BaseResponse createBaseResponse() {
        return new BaseResponse(status, error, data);
    }
}