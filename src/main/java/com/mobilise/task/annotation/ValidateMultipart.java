package com.mobilise.task.annotation;

import com.mobilise.task.annotation.validators.MultipartValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = MultipartValidator.class)
public @interface ValidateMultipart {
    String IMAGE_EXTENSIONS = ".png.jpeg.jpg";

    String message() default "invalid.file";

    String extensions() default IMAGE_EXTENSIONS;

    long maxSize() default 10485760L;

    long maxUploads() default 3;

    /**
     * Specifies the acceptable file name length
     * By default -1 indicates that the validation ignores checking the file name length
     * Take note that if this is done, then it's prevalent that you rename the file before storing its name
     * as some clients are fond of sending a full filesystem path as file name.
     * @return accepted maximum length of file name
     */
    int maxFileName() default -1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}