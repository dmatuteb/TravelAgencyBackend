package io.github.dmatuteb.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.controller.ImageController;
import io.github.dmatuteb.TravelAgency.entity.Image;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImageModelAssembler implements RepresentationModelAssembler<Image, EntityModel<Image>> {

    @Override
    public EntityModel<Image> toModel(Image image) {
        return EntityModel.of(
                image,
                linkTo(methodOn(ImageController.class).getImage(image.getId())).withSelfRel(),
                linkTo(methodOn(ImageController.class).getImages()).withRel("images")
        );
    }

}
