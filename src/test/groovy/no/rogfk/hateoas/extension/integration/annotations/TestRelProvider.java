package no.rogfk.hateoas.extension.integration.annotations;

import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Component;

@Component
public class TestRelProvider implements RelProvider{
    @Override
    public String getItemResourceRelFor(Class<?> aClass) {
        return null;
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> aClass) {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
