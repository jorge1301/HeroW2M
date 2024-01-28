package com.w2m.app.exception;

import java.io.Serial;

public class W2MException extends Exception {


    @Serial
    private static final long serialVersionUID = 6643967555795279842L;

    public W2MException(String message) {
        super(message);
    }
}
