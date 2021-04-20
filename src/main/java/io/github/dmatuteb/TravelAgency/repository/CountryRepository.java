package io.github.dmatuteb.TravelAgency.repository;

import io.github.dmatuteb.TravelAgency.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
