package io.github.dmatuteb.TravelAgency.resthateoas;

import io.github.dmatuteb.TravelAgency.controller.CountryController;
import io.github.dmatuteb.TravelAgency.controller.ImageController;
import io.github.dmatuteb.TravelAgency.entity.AmusementPark;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.controller.AmusementParkController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AmusementParkModelAssembler implements RepresentationModelAssembler<AmusementPark, EntityModel<AmusementPark>> {

    @Override
    public EntityModel<AmusementPark> toModel(AmusementPark amusementPark) {
        return EntityModel.of(
                amusementPark,
                linkTo(methodOn(AmusementParkController.class).getAmusementPark(amusementPark.getId())).withSelfRel(),
                linkTo(methodOn(AmusementParkController.class).getAmusementParks()).withRel("amusement-parks"),
                WebMvcLinkBuilder.linkTo(methodOn(CountryController.class).getCountry(amusementPark.getCountry().getId())).withRel("countries"),
                WebMvcLinkBuilder.linkTo(methodOn(ImageController.class).getImage(amusementPark.getImage().getId())).withRel("images")
        );
    }

}
