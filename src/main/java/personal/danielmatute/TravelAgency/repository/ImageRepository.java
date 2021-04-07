package personal.danielmatute.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.danielmatute.TravelAgency.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
