package se.davvs.floorballfighters.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.davvs.floorballfighters.models.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
}
