package personal.danielmatute.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import personal.danielmatute.TravelAgency.controller.CategoryController;
import personal.danielmatute.TravelAgency.entity.Category;

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
