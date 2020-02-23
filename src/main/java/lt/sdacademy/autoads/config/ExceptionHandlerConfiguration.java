package lt.sdacademy.autoads.config;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.databind.JsonMappingException;
import javax.servlet.http.HttpServletResponse;
import lt.sdacademy.autoads.model.exception.InvalidValuesException;
import lt.sdacademy.autoads.model.exception.ValidationResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionHandlerConfiguration {

  @ExceptionHandler(value = InvalidValuesException.class)
  @ResponseBody
  private ValidationResult handleInvalidValues(
      HttpServletResponse response,
      InvalidValuesException exception
  ) {
    response.setStatus(SC_BAD_REQUEST);
    return new ValidationResult(exception.getErrors());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  private ValidationResult handlemethodargumentNotValid(
      HttpServletResponse response,
      MethodArgumentNotValidException exception
  ) {
    response.setStatus(SC_BAD_REQUEST);
    return new ValidationResult(
        exception.getBindingResult().getFieldErrors().stream().collect(
            groupingBy(
                FieldError::getField,
                mapping(FieldError::getDefaultMessage, toList())
            )
        )
    );
  }

  @ExceptionHandler(value = BindException.class)
  @ResponseBody
  private ValidationResult handleBinding(
      HttpServletResponse response,
      BindException exception
  ) {
    response.setStatus(SC_BAD_REQUEST);
    return new ValidationResult(
        exception.getBindingResult().getFieldErrors().stream().collect(
            groupingBy(
                FieldError::getField,
                mapping((error) -> {
                  if (!error.isBindingFailure()) {
                    return error.getDefaultMessage();
                  }
                  return "invalid value " + error.getRejectedValue();
                }, toList())
            )
        )
    );
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  @ResponseBody
  private String handleNotReadable(
      HttpServletResponse response,
      HttpMessageNotReadableException exception
  ) {
    response.setStatus(SC_BAD_REQUEST);
    if (exception.getCause().getClass().equals(JsonMappingException.class)) {
      JsonMappingException jsonException = (JsonMappingException) exception.getCause();
      return String.format("Invalid JSON at %s:%s", jsonException.getLocation().getLineNr(),
          jsonException.getLocation().getColumnNr());
    }
    return exception.getCause().getMessage();
  }

  @ExceptionHandler(value = HttpClientErrorException.class)
  @ResponseBody
  private String handleClientException(HttpServletResponse response,
      HttpClientErrorException exception) {
    response.setStatus(exception.getRawStatusCode());
    return exception.getMessage();
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  private String handleException(HttpServletResponse response, Exception exception) {
    response.setStatus(SC_INTERNAL_SERVER_ERROR);
    exception.printStackTrace();
    return exception.getMessage();
  }
}
