package no.rogfk.hateoas.extension;

import no.rogfk.hateoas.extension.annotations.HalResource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class HalResourceMetadata {
    static final String PARAM_PAGE = "page";
    static final String PARAM_PAGE_SIZE = "pageSize";

    private final Class<?> controller;
    private final Method requestMethod;

    private final int pageSize;
    private final int maxPageSize;
    private final boolean allowClientPageSize;
    private final boolean allowAllQueryParams;
    private final String[] allowedQueryParams;

    private final String[] paramNames;
    private final Object[] args;

    private HalResourceMetadata(ProceedingJoinPoint proceedingJoinPoint) {
        controller = proceedingJoinPoint.getThis().getClass();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        requestMethod = signature.getMethod();

        HalResource halResource = requestMethod.getAnnotation(HalResource.class);
        pageSize = halResource.pageSize();
        maxPageSize = halResource.maxPageSize();
        allowClientPageSize = halResource.allowClientPageSize();
        allowAllQueryParams = halResource.allowAllQueryParams();
        allowedQueryParams = halResource.allowedQueryParams();

        paramNames = signature.getParameterNames();
        args = proceedingJoinPoint.getArgs();
    }

    static HalResourceMetadata of(ProceedingJoinPoint proceedingJoinPoint) {
        return new HalResourceMetadata(proceedingJoinPoint);
    }

    public int pageSize() {
        if (allowClientPageSize) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String pageSizeParam = request.getParameter(PARAM_PAGE_SIZE);
            if (pageSizeParam != null) {
                try {
                    int clientPageSize = Integer.parseInt(pageSizeParam);
                    if (maxPageSize == -1 || clientPageSize <= maxPageSize) {
                        return clientPageSize;
                    } else {
                        return maxPageSize;
                    }
                } catch (NumberFormatException ignore) {
                }
            }
        }

        return pageSize;
    }

    public List<Link> getLinks(int page, int pageCount) {
        Link resourceLink = ControllerLinkBuilder.linkTo(controller, requestMethod).withSelfRel();

        UriComponentsBuilder baseComponent = UriComponentsBuilder.fromHttpUrl(resourceLink.getHref());
        HalLinkBuilder halLinkBuilder = getHalLinkBuilder(page, pageCount, baseComponent);

        List<Link> links = new ArrayList<>();
        links.add(halLinkBuilder.self());
        links.add(halLinkBuilder.first());
        links.add(halLinkBuilder.next());
        links.add(halLinkBuilder.previous());
        links.add(halLinkBuilder.last());
        return links;
    }

    private HalLinkBuilder getHalLinkBuilder(int page, int pageCount, UriComponentsBuilder baseComponent) {
        if (allowAllQueryParams) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            String queryString = attributes.getRequest().getQueryString();
            return new HalLinkBuilder(baseComponent, page, pageCount, queryString);
        } else {
            if (allowedQueryParams.length > 0) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Arrays.asList(allowedQueryParams).stream().forEach(param -> {
                    String value = request.getParameter(param);
                    baseComponent.queryParam(param, value);
                });
            }
            return new HalLinkBuilder(baseComponent, page, pageCount);
        }
    }

    public List<Link> getLinks() {
        Link resourceLink = ControllerLinkBuilder.linkTo(controller, requestMethod).withSelfRel();
        return Collections.singletonList(new Link(resourceLink.getHref(), Link.REL_SELF));
    }

    public List<Link> getLinks(int pageCount) {
        return getLinks(getPageParam(), pageCount);
    }

    public int getPageParam() {
        return getRequiredParam(PARAM_PAGE);
    }

    private int getRequiredParam(String paramName) {
        Optional<Integer> param = getParam(paramName);
        if (param.isPresent()) {
            return param.get();
        } else {
            throw new IllegalStateException("Missing required page argument (@RequestParam int " + paramName + ")");
        }
    }

    public Optional<Integer> getOptionalParam(String paraName) {
        return getParam(paraName);
    }

    private Optional<Integer> getParam(String paramName) {
        Annotation[][] parameterAnnotations = requestMethod.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == RequestParam.class) {
                    RequestParam requestParam = (RequestParam) annotation;
                    String param = paramNames[i];
                    if (requestParam.value().equals(paramName) || requestParam.name().equals(paramName)
                            || param.equals(paramName)) {
                        Object page = args[i];
                        if (page instanceof Integer) {
                            return Optional.of((Integer) args[i]);
                        } else {
                            throw new IllegalArgumentException("The @RequestParam " + paramName + " must be of type int");
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }
}
