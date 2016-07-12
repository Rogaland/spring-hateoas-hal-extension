package no.rogfk.hateoas.extension.annotations;

import no.rogfk.hateoas.extension.HalResourcesConfig;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.HalHateoasConfig;
import org.springframework.hateoas.config.HypermediaSupportBeanDefinitionRegistrarExt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({HypermediaSupportBeanDefinitionRegistrarExt.class, HalHateoasConfig.class, HalResourcesConfig.class})
public @interface EnableHalHypermediaSupport {
}
