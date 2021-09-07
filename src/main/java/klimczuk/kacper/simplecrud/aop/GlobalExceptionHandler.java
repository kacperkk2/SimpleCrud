package klimczuk.kacper.simplecrud.aop;

import klimczuk.kacper.simplecrud.exceptions.QuoteNotFoundException;
import klimczuk.kacper.simplecrud.exceptions.errors.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuoteNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleQuoteNotFoundException(QuoteNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorDTO errorDTO = new ErrorDTO(String.join(", ", errorMessages), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
