package io.github.dmatuteb.TravelAgency.controller;

import io.github.dmatuteb.TravelAgency.repository.ImageRepository;
import io.github.dmatuteb.TravelAgency.resthateoas.ImageModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.github.dmatuteb.TravelAgency.entity.Image;
import io.github.dmatuteb.TravelAgency.exception.ImageNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;

    private final ImageModelAssembler imageModelAssembler;

    public ImageController(ImageRepository imageRepository, ImageModelAssembler imageModelAssembler) {
        this.imageRepository = imageRepository;
        this.imageModelAssembler = imageModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Image>> getImages() {
        List<EntityModel<Image>> images = this.imageRepository.findAll()
                .stream()
                .map(this.imageModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(images, linkTo(methodOn(ImageController.class).getImages()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Image> getImage(@PathVariable Long id) {
        return this.imageRepository.findById(id).map(this.imageModelAssembler::toModel)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveImage(@RequestBody MultipartFile multipartFile) throws IOException {
        EntityModel<Image> entityModel = this.imageModelAssembler.toModel(this.imageRepository.save(
                new Image(multipartFile.getBytes(), LocalDate.now())));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editImage(@RequestBody MultipartFile multipartFile, @PathVariable Long id) {
        Image updatedImage = this.imageRepository.findById(id).map(mappedImage -> {
            try {
                mappedImage.setImage(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.imageRepository.save(mappedImage);
        }).orElseGet(() -> {
            try {
                return this.imageRepository.save(new Image(id, multipartFile.getBytes(), LocalDate.now()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        });
        EntityModel<Image> entityModel = this.imageModelAssembler.toModel(updatedImage);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        this.imageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
