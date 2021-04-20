package personal.danielmatute.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import personal.danielmatute.TravelAgency.controller.AmusementParkController;
import personal.danielmatute.TravelAgency.controller.CategoryController;
import personal.danielmatute.TravelAgency.controller.GameController;
import personal.danielmatute.TravelAgency.entity.Game;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {

    @Override
    public EntityModel<Game> toModel(Game game) {
        return EntityModel.of(
                game,
                linkTo(methodOn(GameController.class).getGame(game.getId())).withSelfRel(),
                linkTo(methodOn(GameController.class).getGames()).withRel("games"),
                linkTo(methodOn(CategoryController.class).getCategory(game.getId())).withRel("categories"),
                linkTo(methodOn(AmusementParkController.class).getAmusementPark(game.getId())).withRel("amusement-parks")
        );
    }

}
