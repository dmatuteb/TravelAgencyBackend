package personal.danielmatute.TravelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.danielmatute.TravelAgency.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}