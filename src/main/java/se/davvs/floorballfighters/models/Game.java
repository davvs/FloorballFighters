package se.davvs.floorballfighters.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="game", catalog = "floorballfighters")
public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Game() {}

    private Integer id;
    private Integer team1Score =0;
	private Integer team2Score =0;
    private Day day;
    private Set<GameTeamMember> gameTeamMembers = new HashSet<GameTeamMember>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "did", updatable = true)
	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	@OneToMany(mappedBy="game", fetch = FetchType.EAGER)
	public Set<GameTeamMember> getGameTeamMembers() {
		return gameTeamMembers;
	}

	public void setGameTeamMembers(Set<GameTeamMember> gameTeamMembers) {
		this.gameTeamMembers = gameTeamMembers;
	}
	
	@Column(name = "team1Score", unique = false, nullable = false)
    public Integer getTeam1Score() {
		return team1Score;
	}

	public void setTeam1Score(Integer team1Score) {
		this.team1Score = team1Score;
	}

	@Column(name = "team2Score", unique = false, nullable = false)
	public Integer getTeam2Score() {
		return team2Score;
	}

	public void setTeam2Score(Integer team2Score) {
		this.team2Score = team2Score;
	}

}
