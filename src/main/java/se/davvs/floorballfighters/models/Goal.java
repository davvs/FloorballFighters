package se.davvs.floorballfighters.models;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="goals", catalog = "floorballfighters")
public class Goal {

	private Integer id;
	private GameTeamMember gameTeamMember;
	private Integer goalType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "gpid", updatable = true)
	public GameTeamMember getGameTeamMember() {
		return gameTeamMember;
	}
	public void setGameTeamMember(GameTeamMember gameTeamMember) {
		this.gameTeamMember = gameTeamMember;
	}
	
	@Column(name = "goaltype")
	public Integer getGoalType() {
		return goalType;
	}
	public void setGoalType(Integer goalType) {
		this.goalType = goalType;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
