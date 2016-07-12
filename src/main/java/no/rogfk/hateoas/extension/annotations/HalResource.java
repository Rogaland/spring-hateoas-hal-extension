package no.rogfk.hateoas.extension.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HalResource {
    int pageSize() default 20;

    boolean allowClientPageSize() default true;

    int maxPageSize() default -1;

    String[] allowedQueryParams() default {};

    boolean allowAllQueryParams() default false;
}
