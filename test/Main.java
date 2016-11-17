import in.hocg.web.validation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-16.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String[]> params = new HashMap<String, String[]>(){{
            put("name", null);
        }};
        Errors errors = in.hocg.web.validation.Validator.makes(params, new HashMap<String, String>() {{
            put("name", "required|min:5");
        }});
        print(errors.all());
    }


    public static void print(Map<String, List<String>> errors) {
        if (errors.isEmpty()) {
            System.out.println("SUCCESS");
        }
        for (String key : errors.keySet()) {
            String info = String.format("%s => %s", key, errors.get(key));
            System.out.println(info);
        }
    }
}
