package personal.danielmatute.TravelAgency.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_generator")
    @SequenceGenerator(name = "country_generator", sequenceName = "country_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank(message = "The name should not be empty")
    @Size(max = 50, message = "The name should not be longer than 50 characters")
    @Column(name = "country_name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "amusementParkCountry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AmusementPark> amusementParks;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
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

}