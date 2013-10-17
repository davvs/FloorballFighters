package se.davvs.floorballfighters.day;

import java.util.List;

public class PlayersForm {
	private Integer dayId;

	private List<Integer> players;
	
	public Integer getDayId() {
		return dayId;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}
	
	public List<Integer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Integer> players) {
		this.players = players;
	}
}
