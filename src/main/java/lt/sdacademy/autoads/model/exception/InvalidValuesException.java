package lt.sdacademy.autoads.model.exception;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvalidValuesException extends RuntimeException {

  Map<String, List<String>> errors = new HashMap<>();

  public InvalidValuesException addError(String field, String... messages) {
    if (errors.containsKey(field)) {
      errors.get(field).addAll(Arrays.asList(messages));
    } else {
      errors.put(field, Arrays.asList(messages));
    }
    return this;
  }
}
