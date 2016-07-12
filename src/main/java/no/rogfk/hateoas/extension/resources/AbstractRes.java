package no.rogfk.hateoas.extension.resources;

import no.rogfk.hateoas.extension.HalResourceMetadata;

abstract class AbstractRes implements Res {
    protected HalResourceMetadata aspectMetadata;

    @Override
    public void setAspectMetadata(HalResourceMetadata aspectMetadata) {
        this.aspectMetadata = aspectMetadata;
    }
}
