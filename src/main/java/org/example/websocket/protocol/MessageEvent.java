package org.example.websocket.protocol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.websocket.controller.resource.MessageType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public abstract class MessageEvent<T> implements Serializable {

    private String roomId;
    private MessageType type;
    private T payload;
    private LocalDateTime publishedAt;

    @NotNull
    public abstract String getContext();
}
