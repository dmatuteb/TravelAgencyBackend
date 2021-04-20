package io.github.dmatuteb.TravelAgency.controller;

import io.github.dmatuteb.TravelAgency.entity.Category;
import io.github.dmatuteb.TravelAgency.exception.CategoryNotFoundException;
import io.github.dmatuteb.TravelAgency.repository.CategoryRepository;
import io.github.dmatuteb.TravelAgency.resthateoas.CategoryModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private final CategoryModelAssembler categoryModelAssembler;

    public CategoryController(CategoryRepository categoryRepository, CategoryModelAssembler categoryModelAssembler) {
        this.categoryRepository = categoryRepository;
        this.categoryModelAssembler = categoryModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Category>> getCategories() {
        List<EntityModel<Category>> countries = this.categoryRepository.findAll()
                .stream()
                .map(this.categoryModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(countries, linkTo(methodOn(CategoryController.class).getCategories()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Category> getCategory(@PathVariable Long id) {
        return this.categoryRepository.findById(id).map(this.categoryModelAssembler::toModel)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody Category category) {
        EntityModel<Category> entityModel = this.categoryModelAssembler.toModel(this.categoryRepository.save(category));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategory(@Valid @RequestBody Category category, @PathVariable Long id) {
        Category updatedCategory = this.categoryRepository.findById(id).map(mappedCategory -> {
            mappedCategory.setName(category.getName());
            return this.categoryRepository.save(mappedCategory);
        }).orElseGet(() -> {
            category.setId(id);
            return this.categoryRepository.save(category);
        });
        EntityModel<Category> entityModel = this.categoryModelAssembler.toModel(updatedCategory);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        this.categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
