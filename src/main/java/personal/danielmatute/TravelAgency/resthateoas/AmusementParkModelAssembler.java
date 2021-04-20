package personal.danielmatute.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import personal.danielmatute.TravelAgency.controller.AmusementParkController;
import personal.danielmatute.TravelAgency.controller.CountryController;
import personal.danielmatute.TravelAgency.controller.ImageController;
import personal.danielmatute.TravelAgency.entity.AmusementPark;

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
                linkTo(methodOn(CountryController.class).getCountry(amusementPark.getCountry().getId())).withRel("countries"),
                linkTo(methodOn(ImageController.class).getImage(amusementPark.getImage().getId())).withRel("images")
        );
    }

}
