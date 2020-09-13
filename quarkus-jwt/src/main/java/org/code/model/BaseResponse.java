package org.code.model;

public class BaseResponse<T> implements  IBaseResponse<T>  {
    private final Status status;
    private final String error;
    private final T data;

    public BaseResponse(Status status, String error, T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public T getData() {
        return data;
    }

}
