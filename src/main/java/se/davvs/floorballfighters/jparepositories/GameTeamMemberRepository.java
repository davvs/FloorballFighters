package se.davvs.floorballfighters.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.davvs.floorballfighters.models.GameTeamMember;

public interface GameTeamMemberRepository extends JpaRepository<GameTeamMember, Integer> {
}
