package in.hocg.web.validation.validations;

import in.hocg.web.validation.Validation;

import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-17.
 */
public class Required extends Validation {

    @Override
    public boolean validate(String attribute, String[] value, String[] parameters) {
        return !(value == null || value.length == 0);
    }

    @Override
    public String error(String attribute, Map<String, String> rule, String[] parameters) {
        return String.format("%s 不能为空", attribute);
    }
}
