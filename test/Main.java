import in.hocg.validator.Validator;
import in.hocg.validator.validations.Size;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-16.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String[]> params = new HashMap<String, String[]>(){{
            put("name", new String[]{"az"});
        }};
        HashMap<String, String[]> rules = new HashMap<String, String[]>() {{
            put("name", new String[]{"required", "min:5", "regex:^[a-z]+$", "size:0"});
        }};
//        Errors errors = in.hocg.validator.Validator.makes(params, rules);

        Validator validator = new Validator();
        validator.make(params, rules);
        try {
            validator = validator.replacer("size", new Size());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        validator.make(params, rules);


//        print(errors.all());
        try {
            print(validator.errors().all());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Print
     * @param errors
     */
    public static void print(Map<String, Set<String>> errors) {
        if (errors.isEmpty()) {
            System.out.println("SUCCESS");
        }
        for (String key : errors.keySet()) {
            String info = String.format("%s => %s", key, errors.get(key));
            System.out.println(info);
        }
    }
}
