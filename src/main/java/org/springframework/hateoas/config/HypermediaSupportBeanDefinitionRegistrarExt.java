package org.springframework.hateoas.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.StandardClassMetadata;

import java.util.HashMap;
import java.util.Map;

public class HypermediaSupportBeanDefinitionRegistrarExt extends HypermediaSupportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        StandardClassMetadata classMetadata = (StandardClassMetadata) metadata;
        AnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(classMetadata.getIntrospectedClass()) {
            @Override
            public Map<String, Object> getAnnotationAttributes(String annotationName) {
                Map<String, Object> values = new HashMap<>();
                values.put("type", new EnableHypermediaSupport.HypermediaType[]{EnableHypermediaSupport.HypermediaType.HAL});
                return values;
            }
        };
        super.registerBeanDefinitions(annotationMetadata, registry);
    }
}
