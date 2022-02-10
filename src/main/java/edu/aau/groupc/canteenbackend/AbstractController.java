package edu.aau.groupc.canteenbackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractController {
    protected int parseOrThrowHttpException(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "string cannot be parsed integer");
        }
    }
}
