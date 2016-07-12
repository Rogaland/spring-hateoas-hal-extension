package no.rogfk.hateoas.extension.annotations;


import no.rogfk.hateoas.extension.HalResourcesConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(HalResourcesConfig.class)
public @interface EnableHalExtension {
}
