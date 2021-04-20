package personal.danielmatute.TravelAgency.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.danielmatute.TravelAgency.entity.AmusementPark;
import personal.danielmatute.TravelAgency.exception.CountryNotFoundException;
import personal.danielmatute.TravelAgency.repository.AmusementParkRepository;
import personal.danielmatute.TravelAgency.resthateoas.AmusementParkModelAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/amusement-parks")
public class AmusementParkController {

    private final AmusementParkRepository amusementParkRepository;

    private final AmusementParkModelAssembler amusementParkModelAssembler;

    public AmusementParkController(AmusementParkRepository amusementParkRepository, AmusementParkModelAssembler amusementParkModelAssembler) {
        this.amusementParkRepository = amusementParkRepository;
        this.amusementParkModelAssembler = amusementParkModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<AmusementPark>> getAmusementParks() {
         List<EntityModel<AmusementPark>> amusementParks = this.amusementParkRepository.findAll()
                .stream()
                .map(this.amusementParkModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(amusementParks, linkTo(methodOn(AmusementParkController.class).getAmusementParks()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AmusementPark> getAmusementPark(@PathVariable Long id) {
        return this.amusementParkRepository.findById(id).map(this.amusementParkModelAssembler::toModel)
                .orElseThrow(() -> new CountryNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveAmusementPark(@Valid @RequestBody AmusementPark amusementPark) {
        EntityModel<AmusementPark> entityModel = this.amusementParkModelAssembler.toModel(this.amusementParkRepository.save(amusementPark));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editAmusementPark(@Valid @RequestBody AmusementPark amusementPark, @PathVariable Long id) {
        AmusementPark updatedAmusementPark = this.amusementParkRepository.findById(id).map(mappedCountry -> {
            mappedCountry.setName(amusementPark.getName());
            mappedCountry.setWebsite(amusementPark.getWebsite());
            mappedCountry.setAddress(amusementPark.getAddress());
            mappedCountry.setCountry(amusementPark.getCountry());
            mappedCountry.setImage(amusementPark.getImage());
            return this.amusementParkRepository.save(mappedCountry);
        }).orElseGet(() -> {
            amusementPark.setId(id);
            return this.amusementParkRepository.save(amusementPark);
        });
        EntityModel<AmusementPark> entityModel = this.amusementParkModelAssembler.toModel(updatedAmusementPark);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAmusementPark(@PathVariable Long id) {
        this.amusementParkRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
