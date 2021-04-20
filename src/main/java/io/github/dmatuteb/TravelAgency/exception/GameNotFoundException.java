package io.github.dmatuteb.TravelAgency.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long id) {
        super(String.format("Could not find game with id %d", id));
    }

}
