package se.davvs.floorballfighters.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.davvs.floorballfighters.models.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
