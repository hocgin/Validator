package in.hocg.web.validation;

import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-16.
 */
public abstract class Validation {

    /**
     * 校验
     * @param ruleKey 校验字段名
     * @param values 值
     * @param parameters 校验方式附加参数
     * @return true表示通过
     */
    public abstract boolean validate(String ruleKey, String[] values, String[] parameters);

    /**
     * 用于对错误信息中占位符替换
     * @param message 错误信息模板
     * @param ruleKey 校验字段名
     * @param rule 规则
     * @param parameters 校验方式附加参数
     * @return 完整错误信息
     */
    public String replace(String message, String ruleKey, Map<String, String> rule, String[] parameters) {
        return message;
    }

    /**
     * 默认错误信息
     * @param ruleKey 校验字段名
     * @param rule 规则
     * @param parameters 校验方式附加参数
     * @return 完整错误信息
     */
    public abstract String error(String ruleKey, Map<String, String> rule, String[] parameters);

    protected boolean _isNumeric(String str) {
        return str.matches("[0-9]+");
    }
}
