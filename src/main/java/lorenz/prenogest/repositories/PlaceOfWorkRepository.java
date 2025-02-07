package lorenz.prenogest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lorenz.prenogest.entities.Building;
import lorenz.prenogest.entities.PlaceOfWork;

public interface PlaceOfWorkRepository extends JpaRepository<PlaceOfWork, Long> {

    List<PlaceOfWork> findByBuilding(Building buildingId);

}
