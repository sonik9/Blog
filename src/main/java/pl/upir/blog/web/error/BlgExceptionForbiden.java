package pl.upir.blog.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Vitalii on 25/09/2015.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denided")
public class BlgExceptionForbiden extends RuntimeException {
}
