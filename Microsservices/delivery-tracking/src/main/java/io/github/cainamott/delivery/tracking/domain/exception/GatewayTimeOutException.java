package io.github.cainamott.delivery.tracking.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class GatewayTimeOutException extends RuntimeException{
    public GatewayTimeOutException() {
    }

    public GatewayTimeOutException(Throwable cause) {
        super(cause);
    }
}
