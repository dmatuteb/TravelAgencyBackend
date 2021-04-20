package io.github.dmatuteb.TravelAgency.resthateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.controller.AmusementParkController;
import io.github.dmatuteb.TravelAgency.controller.CategoryController;
import io.github.dmatuteb.TravelAgency.controller.GameController;
import io.github.dmatuteb.TravelAgency.entity.Game;

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
