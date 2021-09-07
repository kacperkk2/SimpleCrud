package klimczuk.kacper.simplecrud.exceptions.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private final String message;
    private final int status;
    private final String timestamp;

    public ErrorDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
