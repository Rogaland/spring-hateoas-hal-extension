package org.springframework.hateoas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HateoasConfiguration.class)
public class HalHateoasConfig {
}
