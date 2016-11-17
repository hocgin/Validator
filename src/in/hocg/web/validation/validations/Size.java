package in.hocg.web.validation.validations;

import in.hocg.web.validation.Validation;

import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-17.
 *
 * size:value
 */
public class Size extends Validation {
    @Override
    public boolean validate(String filedName, String[] values, String[] parameters) {
        if (values != null) {
            for (String value : values) {
                if (value.length() > Long.valueOf(parameters[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String error(String filedName, Map<String, String[]> rule, String[] parameters) {
        return String.format("%s 的长度 不能小于 %s", filedName, parameters[0]);
    }

    @Override
    public String replace(String message, String filedName, Map<String, String[]> rule, String[] parameters) {
        return message.replace(":size", parameters[0]);
    }
}
