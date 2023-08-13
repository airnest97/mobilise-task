package com.mobilise.task.annotation;

import com.mobilise.task.annotation.validators.DoesNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD})
@Constraint(validatedBy = DoesNotExistValidator.class)
@Retention(RUNTIME)
public @interface DoesNotExist {
    String message() default "Non-unique record";

    String table();

    String columnName();

    String query() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
