package com.example.ddopik.phlogbusiness.utiltes.rxeventbus;

public class RxEvent<T> {

    private final Type type;

    private final T object;

    public RxEvent(Type type, T object) {
        this.type = type;
        this.object = object;
    }

    public Type getType() {
        return type;
    }

    public T getObject() {
        return object;
    }

    public static enum Type {
        POPUP
    }
}
