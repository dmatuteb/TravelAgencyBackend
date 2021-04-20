package io.github.dmatuteb.TravelAgency.exception;

public class AmusementParkNotFoundException extends RuntimeException {

    public AmusementParkNotFoundException(Long id) {
        super(String.format("Could not find amusement park with id %d", id));
    }

}
