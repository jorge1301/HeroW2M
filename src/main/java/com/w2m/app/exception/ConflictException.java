package com.w2m.app.exception;

import java.io.Serial;

public class ConflictException extends W2MException {
    @Serial
    private static final long serialVersionUID = -7769326816151490381L;

    public ConflictException(String message) {
        super(message);
    }
}
