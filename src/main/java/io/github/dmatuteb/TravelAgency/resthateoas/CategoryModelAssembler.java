package io.github.dmatuteb.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.controller.CategoryController;
import io.github.dmatuteb.TravelAgency.entity.Category;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {

    @Override
    public EntityModel<Category> toModel(Category category) {
        return EntityModel.of(
                category,
                linkTo(methodOn(CategoryController.class).getCategory(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories")
        );
    }

}
