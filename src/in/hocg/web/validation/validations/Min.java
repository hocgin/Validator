package in.hocg.web.validation.validations;

import in.hocg.web.validation.Validation;

import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-17.
 */
public class Min extends Validation {
    @Override
    public boolean validate(String ruleKey, String[] values, String[] parameters) {
        if (values == null) {
            return false;
        }
        for (String value : values) {
            if (!_isNumeric(value)) return false;
            if (Long.valueOf(value) >= Long.valueOf(parameters[0])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String error(String ruleKey, Map<String, String> rule, String[] parameters) {
        return String.format("%s 不能小于 %s", ruleKey, parameters[0]);
    }
}
