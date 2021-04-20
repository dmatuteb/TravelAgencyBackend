package personal.danielmatute.TravelAgency.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class AmusementPark {

    @Id
    @Column(name = "amusement_park_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amusement_park_generator")
    @SequenceGenerator(name = "amusement_park_generator", sequenceName = "amusement_park_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "amusement_park_name", length = 20, nullable = false)
    @Size(max = 20, message = "The name should not be longer than 50 characters")
    @NotBlank(message = "The name should not be empty")
    private String name;

    @Column(name = "amusement_park_website", length = 60, nullable = false)
    @Size(max = 60, message = "The website address should not be longer than 60 characters")
    @NotBlank(message = "The website address should not be empty")
    private String website;

    @Column(name = "amusement_park_address", length = 1024, nullable = false)
    @Size(max = 1024, message = "The address should not be longer than 1024 characters")
    @NotBlank(message = "The address should not be empty")
    private String address;

    @ManyToOne
    @JoinColumn(name = "amusement_park_country_id", referencedColumnName = "country_id", nullable = false)
    @NotNull(message = "The country should not be empty")
    private Country amusementParkCountry;

    @ManyToOne
    @JoinColumn(name = "amusement_park_image_id", referencedColumnName = "image_id", nullable = false)
    @NotNull(message = "The image should not be empty")
    private Image amusementParkImage;

    @OneToMany(mappedBy = "gameAmusementPark", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Game> amusementParkGames;

    @OneToMany(mappedBy = "employeeAmusementPark", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    public AmusementPark() {
    }

    public AmusementPark(Long id, String name, String website, String address, Country country, Image image) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.address = address;
        this.amusementParkCountry = country;
        this.amusementParkImage = image;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Image getImage() {
        return amusementParkImage;
    }

    public void setImage(Image image) {
        this.amusementParkImage = image;
    }

    public Country getCountry() {
        return amusementParkCountry;
    }

    public void setCountry(Country country) {
        this.amusementParkCountry = country;
    }

}
