package io.github.dmatuteb.TravelAgency.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_generator")
    @SequenceGenerator(name = "image_generator", sequenceName = "image_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "image_data", length = 5000000, nullable = false)
    private byte[] image;

    @Basic
    private LocalDate createdOn;

    @OneToMany(mappedBy = "amusementParkImage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AmusementPark> amusementParks;

    public Image() {
    }

    public Image(byte[] image, LocalDate createdOn) {
        this.image = image;
        this.createdOn = createdOn;
    }

    public Image(Long id, byte[] image, LocalDate createdOn) {
        this.id = id;
        this.image = image;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

}
