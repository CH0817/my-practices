package com.rex.practice.model.message;

import com.rex.practice.model.message.base.Message;
import lombok.NonNull;

public class ErrorMessage extends Message {

    public ErrorMessage(@NonNull String message, @NonNull String redirectUrl) {
        super(message, redirectUrl, "error");
    }

}
