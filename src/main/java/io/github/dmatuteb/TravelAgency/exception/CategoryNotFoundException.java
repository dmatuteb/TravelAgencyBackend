package io.github.dmatuteb.TravelAgency.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super(String.format("Could not find category with id %d", id));
    }

}
