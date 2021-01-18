package personal.danielmatute.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.danielmatute.TravelAgency.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
