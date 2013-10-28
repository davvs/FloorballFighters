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
	private GameTeamMember scorer;
	private GameTeamMember assister;
	private Game game;
	private Integer goalType;
	private Integer team;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "scorerid", updatable = true)
	public GameTeamMember getScorer() {
		return scorer;
	}
	public void setScorer(GameTeamMember scorer) {
		this.scorer = scorer;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "assisterid", updatable = true)
	public GameTeamMember getAssister() {
		return assister;
	}
	public void setAssister(GameTeamMember assister) {
		this.assister = assister;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "gameid", updatable = true)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	@Column(name = "team")
	public Integer getTeam() {
		return team;
	}
	public void setTeam(Integer team) {
		this.team = team;
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
