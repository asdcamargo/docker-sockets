package decamargo.tutorials.java.socket.ws;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class FormMessage {

    private final String message;
    private final MessageType type;

    @Getter
    @AllArgsConstructor
    enum MessageType {
        SUCCESS("alert-success"), ERROR("alert-warning");

        private String value;
    }
}
