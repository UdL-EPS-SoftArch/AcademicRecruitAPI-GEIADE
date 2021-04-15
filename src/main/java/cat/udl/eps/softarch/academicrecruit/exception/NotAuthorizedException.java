package cat.udl.eps.softarch.academicrecruit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason = "Authorization required")
public class NotAuthorizedException extends RuntimeException{
}
