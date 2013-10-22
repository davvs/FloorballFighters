package se.davvs.floorballfighters.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "player", catalog = "floorballfighters")
public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Player() {}

    private Integer id;
    private String name;
    private Set<Day> days = new HashSet<Day>(0);
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
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "players")
	public Set<Day> getDays() {
		return days;
	}

	public void setDays(Set<Day> days) {
		this.days = days;
	}

	@OneToMany(mappedBy="player", fetch = FetchType.EAGER)
	public Set<GameTeamMember> getGameTeamMembers() {
		return gameTeamMembers;
	}

	public void setGameTeamMembers(Set<GameTeamMember> gameTeamMembers) {
		this.gameTeamMembers = gameTeamMembers;
	}
	

}
