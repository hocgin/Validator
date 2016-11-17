package in.hocg.web.validation.validations;

import in.hocg.web.validation.Validation;

import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-17.
 */
public class Regex extends Validation {
    @Override
    public boolean validate(String ruleKey, String[] values, String[] parameters) {
        return false;
    }

    @Override
    public String error(String ruleKey, Map<String, String> rule, String[] parameters) {
        return null;
    }
}
