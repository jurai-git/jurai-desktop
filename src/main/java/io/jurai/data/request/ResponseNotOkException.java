package io.jurai.data.request;

public class ResponseNotOkException extends Exception {
    private int code;

    public ResponseNotOkException(int code) {
        super(String.valueOf(code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
