package personal.danielmatute.TravelAgency.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_generator")
    @SequenceGenerator(name = "game_generator", sequenceName = "game_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "game_name", nullable = false)
    @Size(min = 1, max = 50)
    @NotBlank(message = "name is mandatory")
    private String name;

    @ManyToOne
    @JoinColumn(name = "game_amusementpark_id", referencedColumnName = "amusement_park_id", nullable = false)
    private AmusementPark gameAmusementPark;

    @ManyToOne
    @JoinColumn(name = "game_category_id", referencedColumnName = "category_id", nullable = false)
    private Category gameCategory;

    public Game() {
    }

    public Game(Long id, String name, AmusementPark amusementPark, Category category) {
        this.id = id;
        this.name = name;
        this.gameAmusementPark = amusementPark;
        this.gameCategory = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmusementPark getGameAmusementPark() {
        return gameAmusementPark;
    }

    public void setGameAmusementPark(AmusementPark gameAmusementPark) {
        this.gameAmusementPark = gameAmusementPark;
    }

    public Category getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(Category gameCategory) {
        this.gameCategory = gameCategory;
    }

}
