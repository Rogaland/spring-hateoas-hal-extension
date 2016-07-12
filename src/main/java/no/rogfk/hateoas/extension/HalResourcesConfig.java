package no.rogfk.hateoas.extension;

import no.rogfk.hateoas.extension.resources.HalResFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HalResourcesConfig {

    @Bean
    public HalResFactory halResFactory() {
        return new HalResFactory();
    }

    @Bean
    public HalResourcesAspect pagedResourceAspect() {
        return new HalResourcesAspect(halResFactory());
    }
}
