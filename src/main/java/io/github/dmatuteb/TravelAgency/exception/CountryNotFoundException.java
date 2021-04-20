package io.github.dmatuteb.TravelAgency.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(Long id) {
        super(String.format("Could not find country with id %d", id));
    }

}