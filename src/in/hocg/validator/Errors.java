package in.hocg.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-16.
 */
public class Errors {

    private Map<String, List<String>> errors = new HashMap<>();

    /**
     * 返回所有错误信息
     * @return
     */
    public Map<String, List<String>> all() {
        return this.errors;
    }

    /**
     * 是否验证成功
     * @return
     */
    public boolean passed() {
        return errors.isEmpty();
    }

    /**
     * 是否验证失败
     * @return
     */
    public boolean fail() {
        return !errors.isEmpty();
    }

    /**
     * 加入一条错误信息
     * @param ruleKey
     * @param message
     */
    public void add(String ruleKey, String message) {
        List<String> messages = errors.get(ruleKey);
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        this.errors.put(ruleKey, messages);
    }

    public interface AfterListener {
        void run(Errors errors);
    }
}
