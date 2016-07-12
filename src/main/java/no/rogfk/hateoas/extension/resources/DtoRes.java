package no.rogfk.hateoas.extension.resources;


import no.rogfk.hateoas.extension.HalSingleResource;

class DtoRes extends AbstractRes {
    private final Object response;

    DtoRes(Object response) {
        this.response = response;
    }

    @Override
    public Object execute() {
        HalSingleResource halSingleResource = new HalSingleResource(response);
        halSingleResource.add(aspectMetadata.getLinks());
        return halSingleResource;
    }


}
