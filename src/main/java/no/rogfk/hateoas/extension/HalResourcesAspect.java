package no.rogfk.hateoas.extension;

import no.rogfk.hateoas.extension.resources.Res;
import no.rogfk.hateoas.extension.resources.HalResFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HalResourcesAspect {

    private final HalResFactory halResFactory;

    public HalResourcesAspect(HalResFactory halResFactory) {
        this.halResFactory = halResFactory;
    }

    @Around("@annotation(no.rogfk.hateoas.extension.annotations.HalResource)")
    public Object executeEndpoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object response = proceedingJoinPoint.proceed();

        Res resource = halResFactory.create(response);
        resource.setAspectMetadata(HalResourceMetadata.of(proceedingJoinPoint));

        return resource.execute();
    }
}