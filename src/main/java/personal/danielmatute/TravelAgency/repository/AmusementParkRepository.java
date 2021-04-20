package personal.danielmatute.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.danielmatute.TravelAgency.entity.AmusementPark;

@Repository
public interface AmusementParkRepository extends JpaRepository<AmusementPark, Long> {

}
