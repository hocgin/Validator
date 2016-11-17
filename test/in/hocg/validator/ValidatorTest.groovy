package in.hocg.validator

import in.hocg.validator.validations.Size

/**
 * (๑`灬´๑)
 * hocgin(admin@hocg.in)
 * --------------------
 * Created 16-11-17.
 */
class ValidatorTest extends GroovyTestCase {
    Map<String, String[]> params = new HashMap<String, String[]>(){{
        put("name", ["az"] as String[]);
    }};
    HashMap<String, String[]> rules = new HashMap<String, String[]>() {{
        put("name", ["required", "min:5", "regex:^[a-z]+\$", "size:0"] as String[]);
    }};

    Validator validator;
    @Override
    void setUp() {
        validator = new Validator();

    }

    void testExtend() {
        println ">> 永久加入校验器 <<"
        validator.extend("size", new Size())
        assertTrue(validator.GLOBAL_VALIDATIONS.containsKey("size"))
    }

    void testReplacer() {
        println ">> 临时加入校验器 <<"
        println ">> 未加入 <<"
        validator.make(params, rules);
        print(validator.errors().all())
        println ">> 加入 <<"
        try {
            validator = validator.replacer("size", new Size());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        validator.make(params, rules);
        print(validator.errors().all())


        assertTrue(validator.localValidations.containsKey("size"))
    }

    void testAfter() {
        println ">> 执行结束钩子 <<"
        validator.after(new Validator.AfterListener() {
            void run(Validator validator) {
                println "校验结果: " + validator.passes()
            }
        })
        validator.make(params, rules)
        print(validator.errors().all())
    }


    void testMakes1() {
        validator.extend("size", new Size())
        def errors = Validator.makes(params, rules, [
                "required":"模板 required",
                "size":"模板 :size size",
        ], null, null)
        print errors.all()
    }

    void testMakes2() {
        validator.extend("size", new Size())
        def errors = Validator.makes(params, rules, [
                "required":"模板 required",
                "size":"模板 :size size",
        ], [
                "size":"[最高级别] 字段不能XXX"
        ], new Errors.AfterListener() {
            void run(Errors errors) {
                println "校验结果: " + errors.passed()
            }
        })
        print errors.all()
    }


    /**
     * Print
     * @param errors
     */
    void print(Map<String, List<String>> errors) {
        if (errors.isEmpty()) {
            System.out.println("SUCCESS");
        }
        for (String key : errors.keySet()) {
            String info = String.format("%s => %s", key, errors.get(key));
            System.out.println(info);
        }
    }
}
