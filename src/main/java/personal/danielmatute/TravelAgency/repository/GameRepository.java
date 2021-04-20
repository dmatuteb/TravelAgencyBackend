package personal.danielmatute.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.danielmatute.TravelAgency.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>  {

}
