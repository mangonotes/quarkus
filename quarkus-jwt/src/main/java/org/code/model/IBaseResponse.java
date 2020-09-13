package org.code.model;

public interface IBaseResponse<T> {
    Status getStatus();
    String getError();
    T getData();

}
