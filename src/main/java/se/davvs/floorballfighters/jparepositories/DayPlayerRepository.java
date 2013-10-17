package se.davvs.floorballfighters.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.davvs.floorballfighters.models.DayPlayer;

public interface DayPlayerRepository extends JpaRepository<DayPlayer, Integer> {
//	public List<Player> findByDayId(Integer dayId);
}
