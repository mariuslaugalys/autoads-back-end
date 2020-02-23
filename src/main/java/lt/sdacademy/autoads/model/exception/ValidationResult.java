package lt.sdacademy.autoads.model.exception;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationResult {

  private Map<String, List<String>> validationErrors;
}
