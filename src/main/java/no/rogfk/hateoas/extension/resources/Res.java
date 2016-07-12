package no.rogfk.hateoas.extension.resources;


import no.rogfk.hateoas.extension.HalResourceMetadata;

public interface Res {

    void setAspectMetadata(HalResourceMetadata aspectMetadata);

    Object execute();
}
