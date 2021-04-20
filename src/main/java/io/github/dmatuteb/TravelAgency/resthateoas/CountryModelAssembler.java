package io.github.dmatuteb.TravelAgency.resthateoas;

import io.github.dmatuteb.TravelAgency.controller.CountryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.entity.Country;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CountryModelAssembler implements RepresentationModelAssembler<Country, EntityModel<Country>> {

    @Override
    public EntityModel<Country> toModel(Country country) {
        return EntityModel.of(
                country,
                WebMvcLinkBuilder.linkTo(methodOn(CountryController.class).getCountry(country.getId())).withSelfRel(),
                linkTo(methodOn(CountryController.class).getCountries()).withRel("countries")
        );
    }

}
