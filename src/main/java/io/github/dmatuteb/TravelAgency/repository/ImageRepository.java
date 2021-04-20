package io.github.dmatuteb.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.dmatuteb.TravelAgency.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
