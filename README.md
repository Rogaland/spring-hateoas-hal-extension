# Spring HATEOAS HAL extension

[![Build Status](https://travis-ci.org/jarlehansen/spring-hateoas-hal-extension.svg?branch=master)](https://travis-ci.org/jarlehansen/spring-hateoas-hal-extension)

Small extension to Spring HATEOAS that enables HAL collections.

## Links
- [Reference Documentation](http://jarlehansen.github.io/spring-hateoas-hal-extension/docs/)
- [Apigility - HAL](https://apigility.org/documentation/api-primer/halprimer)
- [Spring HATEOAS Reference Documentation](http://docs.spring.io/spring-hateoas/docs/current/reference/html)
- [HAL Specification](http://stateless.co/hal_specification.html)

## Usage
- Add @EnableHalHypermediaSupport on the class containing @SpringBootApplication
- Add @HalResource to a method on a RestController. This will add paging (with self, next, prev, last, first) and pagination metadata. You can also add a custom pageSize.

## Development

Use 'mvn deploy' to publish a new version.
Update/create the maven settings.xml file with the Bintray credentials Use your API key as your password (not your login password):
```xml
<server>
  <id></id>
  <username></username>
  <password></password>
</server>
```
<br/>
**For more information:**
- [Settings.xml example](https://github.com/bintray/bintray-examples/blob/master/maven-example/settings.xml)
- [Publishing maven project to bintray](https://blog.bintray.com/2015/09/17/publishing-your-maven-project-to-bintray/)
