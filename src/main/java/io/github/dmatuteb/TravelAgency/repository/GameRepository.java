package io.github.dmatuteb.TravelAgency.repository;

import io.github.dmatuteb.TravelAgency.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>  {

}
