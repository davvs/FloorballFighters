package se.davvs.floorballfighters.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Set<DayPlayer> dayPlayers = new HashSet<DayPlayer>(0);
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

	@OneToMany(mappedBy="day", fetch = FetchType.EAGER)
	public Set<DayPlayer> getDayPlayers() {
		return dayPlayers;
	}

	public void setDayPlayers(Set<DayPlayer> dayPlayers) {
		this.dayPlayers = dayPlayers;
	}
	
	@OneToMany(mappedBy="day", fetch = FetchType.EAGER)
	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

}
