package com.rex.practice.model.message;

import com.rex.practice.model.message.base.Message;
import lombok.NonNull;

public class InfoMessage extends Message {

    public InfoMessage(@NonNull String message, @NonNull String redirectUrl) {
        super(message, redirectUrl, "info");
    }

}
