package personal.danielmatute.TravelAgency.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Long id) {
        super(String.format("Could not find image with id %d", id));
    }

}
