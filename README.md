## VALIDATOR
> Java 字段校验器

## Usa
### 1. 装载`必须`
```groovy
Validator.load([
                "required": new Required(),
                "min": new Min(),
                "regex": new Regex()
        ])
```
### 2. 使用
```groovy
    def params = [
            "name":["az"] as String[]
    ];
    def rules = [
            "name":["required", "min:5", "regex:^[a-z]+\$", "size:0"] as String[]
    ];
    def errors = Validator.makes(params, rules)
    errors.all() // 错误信息
    errors.passed() // 是否通过验证
```
#### 2.1 自定义验证器
> 参考 `Required.class` or `Regex.class`
> You need extends Validation class.

#### 2.2 装载临时验证器
``` groovy
def validator = validator.replacer("size", new Size());
```

#### 2.3 装载全局验证器
```groovy
validator.extend("size", new Size())
```

### 3. 错误信息级别
> 验证器定义默认描述(error()) < make传入指令消息模板(messages) < 定制错误消息(customAttributes)

### 4. 校验钩子
```groovy
# 多例时使用
validator.after(new Validator.AfterListener() {
    ...
})
```

Bye.Bye.
## LICENSE
[MIT LICENSE](/LICENSE)