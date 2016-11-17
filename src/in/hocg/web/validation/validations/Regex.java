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
    public boolean validate(String filedName, String[] values, String[] parameters) {
        if (values != null) {
            for (String value : values) {
                if (!_isMatch(parameters[0], value)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String error(String filedName, Map<String, String[]> rule, String[] parameters) {
        return String.format("%s 不满足正则/%s/", filedName, parameters[0]);
    }

    @Override
    protected String[] parameters(String parametersStr) {
        return new String[]{parametersStr};
    }
}
