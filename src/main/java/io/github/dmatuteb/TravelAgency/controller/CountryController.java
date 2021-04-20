package io.github.dmatuteb.TravelAgency.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.dmatuteb.TravelAgency.entity.Country;
import io.github.dmatuteb.TravelAgency.exception.CountryNotFoundException;
import io.github.dmatuteb.TravelAgency.repository.CountryRepository;
import io.github.dmatuteb.TravelAgency.resthateoas.CountryModelAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    private final CountryModelAssembler countryModelAssembler;

    public CountryController(CountryRepository countryRepository, CountryModelAssembler countryModelAssembler) {
        this.countryRepository = countryRepository;
        this.countryModelAssembler = countryModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Country>> getCountries() {
        List<EntityModel<Country>> countries = this.countryRepository.findAll()
                .stream()
                .map(this.countryModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(countries, linkTo(methodOn(CountryController.class).getCountries()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Country> getCountry(@PathVariable Long id) {
        return this.countryRepository.findById(id).map(this.countryModelAssembler::toModel)
                .orElseThrow(() -> new CountryNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveCountry(@Valid @RequestBody Country country) {
        EntityModel<Country> entityModel = this.countryModelAssembler.toModel(this.countryRepository.save(country));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCountry(@Valid @RequestBody Country country, @PathVariable Long id) {
        Country updatedCountry = this.countryRepository.findById(id).map(mappedCountry -> {
            mappedCountry.setName(country.getName());
            return this.countryRepository.save(mappedCountry);
        }).orElseGet(() -> {
            country.setId(id);
            return this.countryRepository.save(country);
        });
        EntityModel<Country> entityModel = this.countryModelAssembler.toModel(updatedCountry);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        this.countryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}