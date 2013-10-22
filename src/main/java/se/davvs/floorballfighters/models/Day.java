package se.davvs.floorballfighters.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="day", catalog = "floorballfighters", uniqueConstraints = {
		@UniqueConstraint(columnNames = "date") })
public class Day implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Day() {}

    private Integer id;
    private Date date;
    private Set<Player> players = new HashSet<Player>(0);
    private Set<Game> games = new HashSet<Game>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "date", unique = true)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "day_player", catalog = "floorballfighters",
			joinColumns = { 
				@JoinColumn(name = "did", nullable = false, updatable = true) }, 
				inverseJoinColumns = { @JoinColumn(name = "pid", nullable = false, updatable = true) })
	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
	@OneToMany(mappedBy="day", fetch = FetchType.EAGER)
	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

}
