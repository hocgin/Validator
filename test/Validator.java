import in.hocg.web.validation.Errors;
import in.hocg.web.validation.StringsUtil;
import in.hocg.web.validation.Validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-16.
 * <p>
 * rules格式: 指令:参数,参数|指令:参数,参数
 * <p>
 * 校验方法 Validation.make(request, map rules, map errors, map customAttributes)
 * Validation
 * Validation.replacer() 临时增加
 * Validation.extend() 扩展
 * <p>
 * --- 接口 ---
 * 校验规则接口 validateFoo($attribute, $value, $parameters)
 * 定义占位符  replaceFoo($message, $attribute, $rule, $parameters)
 */
public class Validator implements Cloneable {

    private Map<String, Validation> validations = new HashMap<>();
    private Errors errors;
    private Listener afterListener;
    private Map<String, String> rules;
    private Map<String, String> messages;
    private Map<String, String> customAttributes;

    /**
     *
     * @param request
     * @param rules 规则
     * @param messages 规则对应的模板 {规则:模板}
     * @param customAttributes 最高级别的错误信息
     */
    public Validator make(Map<String, String[]> request,
                          Map<String, String> rules,
                          Map<String, String> messages,
                          Map<String, String> customAttributes) {
        errors = new Errors();
        rules = addRules(rules).getRules();
        messages = addMessages(messages).getMessages();
        customAttributes = addCustomAttributes(customAttributes).getCustomAttributes();

        for (String ruleKey : rules.keySet()) {
            String ruleValue = rules.get(ruleKey);
            String[] attributes = ruleValue.split("|");
            String[] value = request.get(ruleKey);
            String[] parameters = null;
            for (String attribute : attributes) {
                Validation validation = validations.get(attribute);
                if (attribute.contains(":")) {
                    String[] params = attribute.split(":");
                    if (params.length > 1
                            && params[1].contains(",")) {
                        parameters = params[1].split(",");
                        // 清理参数的前后空格
                        parameters = StringsUtil.trimElement(parameters);
                    } else {
                        parameters = new String[]{params[1].trim()};
                    }
                }
                if (!validation.validate(attribute, value, parameters)) { // 校验失败
                    String message;
                    if (customAttributes.isEmpty()
                            ||StringsUtil.isEmpty(message = customAttributes.get(attribute))) {
                        String template = messages.get(attribute);
                        if (!StringsUtil.isEmpty(template)) {
                            // 模板定义的错误信息
                            message = validation.replace(template, attribute, rules, parameters);
                        } else {
                            // 默认错误信息
                            message = validation.error(attribute, rules, parameters);
                        }
                    }
                    errors.add(ruleKey, message);
                }
            }
        }
        if (afterListener != null) {
            afterListener.run(this);
        }
        return this;
    }
















    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    /**
     * 新增一条Rule
     * @param ruleKey
     * @param ruleValue
     * @return
     */
    public Validator addRule(String ruleKey, String ruleValue) {
        if (this.rules == null) {
            this.rules = new HashMap<>();
        }
        this.rules.put(ruleKey, ruleValue);
        return this;
    }

    /**
     * 新增多条Rule
     * @param rules
     * @return
     */
    public Validator addRules(Map<String, String> rules) {
        if (this.rules == null) {
            this.rules = new HashMap<>();
        }
        for (String key : rules.keySet()) {
            this.rules.put(key, rules.get(key));
        }
        return this;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }


    /**
     * 新增单条message
     * @param ruleKey
     * @param message
     * @return
     */
    public Validator addMessage(String ruleKey, String message) {
        if (this.messages == null) {
            this.messages = new HashMap<>();
        }
        this.messages.put(ruleKey, message);
        return this;
    }

    /**
     * 新增多条messages
     * @param messages
     * @return
     */
    public Validator addMessages(Map<String, String> messages) {
        if (this.messages == null) {
            this.messages = new HashMap<>();
        }
        for (String key : messages.keySet()) {
            this.messages.put(key, messages.get(key));
        }
        return this;
    }

    public Map<String, String> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(Map<String, String> customAttributes) {
        this.customAttributes = customAttributes;
    }

    /**
     * 新增单条 customAttribute
     * @param ruleKey
     * @param customAttribute
     * @return
     */
    public Validator addCustomAttribute(String ruleKey, String customAttribute) {
        if (this.customAttributes == null) {
            this.customAttributes = new HashMap<>();
        }
        this.customAttributes.put(ruleKey, customAttribute);
        return this;
    }

    /**
     * 新增多条 customAttributes
     * @param customAttributes
     * @return
     */
    public Validator addCustomAttributes(Map<String, String> customAttributes) {
        if (this.customAttributes == null) {
            this.customAttributes = new HashMap<>();
        }
        for (String key : customAttributes.keySet()) {
            this.customAttributes.put(key, customAttributes.get(key));
        }
        return this;
    }

    /**
     * 全局扩展校验器
     * @param attribute 拦截规则
     * @param validation 拦截器
     * @return
     */
    public Validator extend(String attribute, Validation validation) {
        validations.put(attribute, validation);
        return this;
    }

    /**
     * 临时扩展校验器
     * @param attribute 拦截规则
     * @param validation 拦截器
     * @return 扩展后的校验器
     * @throws CloneNotSupportedException
     */
    public Validator replacer(String attribute, Validation validation) throws CloneNotSupportedException {
        Validator clone = (Validator) this.clone();
        return clone.extend(attribute, validation);
    }


    /**
     * 钩子方法 校验完成后回调
     * @param listener
     * @return
     */
    public Validator after(Listener listener) {
        this.afterListener = listener;
        return this;
    }

    /**
     * 返回错误信息对象
     * @return
     * @throws Exception 需要在make()之后调用
     */
    public Errors errors() throws Exception {
        if (this.errors == null) {
            throw new Exception("Please usa this.make() before.");
        }
        return this.errors;
    }

    interface Listener {
        void run(Validator validation);
    }

    /**
     * 是否失败
     * @return
     */
    public boolean fails() {
        return this.errors.fail();
    }

    /**
     * 是否成功
     * @return
     */
    public boolean passes() {
        return this.errors.passed();
    }

    /**
     * 获取所有错误信息
     * @return
     */
    public Map<String, List<String>> messages() {
        return this.errors.all();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
