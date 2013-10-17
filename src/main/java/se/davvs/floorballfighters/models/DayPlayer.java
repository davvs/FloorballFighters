package se.davvs.floorballfighters.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="day_player")
public class DayPlayer {
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
     
    @Column(name="did")
    private Integer dayId;
    
    @Column(name="pid")
    private Integer playerId;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayId() {
		return dayId;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}


}
