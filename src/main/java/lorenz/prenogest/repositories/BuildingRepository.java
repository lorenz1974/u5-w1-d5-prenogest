package lorenz.prenogest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lorenz.prenogest.entities.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
