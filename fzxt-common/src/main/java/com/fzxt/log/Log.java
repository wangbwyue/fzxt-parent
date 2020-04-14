package com.fzxt.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
//表示这个注解可以用在类/接口上，还可以用在方法上
@Retention(RetentionPolicy.RUNTIME)
//表示这是一个运行时注解，即运行起来之后，才能获取注解中的相关信息。
@Documented
//表示当执行javadoc的时候，本注解会生成相关文档
@Inherited
//表示这个注解可以被子类继承
public @interface Log {
//    方法描述
        public String value() default "";
}
