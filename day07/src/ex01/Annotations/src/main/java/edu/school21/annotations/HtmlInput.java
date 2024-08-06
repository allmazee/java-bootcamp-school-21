package edu.school21.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.SOURCE)
public @interface HtmlInput {
    String type();
    String name();
    String placeholder();
}
