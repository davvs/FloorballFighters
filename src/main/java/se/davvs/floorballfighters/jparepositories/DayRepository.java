package se.davvs.floorballfighters.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.davvs.floorballfighters.models.Day;

public interface DayRepository extends JpaRepository<Day, Integer> {
}
