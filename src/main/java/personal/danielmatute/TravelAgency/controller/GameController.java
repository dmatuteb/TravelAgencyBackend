package personal.danielmatute.TravelAgency.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.danielmatute.TravelAgency.entity.Game;
import personal.danielmatute.TravelAgency.exception.GameNotFoundException;
import personal.danielmatute.TravelAgency.repository.GameRepository;
import personal.danielmatute.TravelAgency.resthateoas.GameModelAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository gameRepository;

    private final GameModelAssembler gameModelAssembler;

    public GameController(GameRepository gameRepository, GameModelAssembler gameModelAssembler) {
        this.gameRepository = gameRepository;
        this.gameModelAssembler = gameModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Game>> getGames() {
        List<EntityModel<Game>> games = this.gameRepository.findAll()
                .stream()
                .map(this.gameModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(games, linkTo(methodOn(GameController.class).getGames()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Game> getGame(@PathVariable Long id) {
        return this.gameRepository.findById(id).map(this.gameModelAssembler::toModel)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveGame(@Valid @RequestBody Game game) {
        EntityModel<Game> entityModel = this.gameModelAssembler.toModel(this.gameRepository.save(game));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editGame(@Valid @RequestBody Game game, @PathVariable Long id) {
        Game updatedGame = this.gameRepository.findById(id).map(mappedGame -> {
            mappedGame.setName(game.getName());
            mappedGame.setGameCategory(game.getGameCategory());
            mappedGame.setGameAmusementPark(game.getGameAmusementPark());
            return this.gameRepository.save(mappedGame);
        }).orElseGet(() -> {
            game.setId(id);
            return this.gameRepository.save(game);
        });
        EntityModel<Game> entityModel = this.gameModelAssembler.toModel(updatedGame);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        this.gameRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
