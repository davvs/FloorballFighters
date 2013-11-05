package se.davvs.floorballfighters.game;


public class CustomScoreForm {
	private Integer dayId;
	private Integer team;
	private Integer gameId;
	private Integer scorer;
	private Integer assister;
	private String goalType;


	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	public Integer getTeam() {
		return team;
	}
	public void setTeam(Integer team) {
		this.team = team;
	}
	public Integer getDayId() {
		return dayId;
	}
	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}
	public Integer getScorer() {
		return scorer;
	}
	public void setScorer(Integer scorer) {
		this.scorer = scorer;
	}
	public Integer getAssister() {
		return assister;
	}
	public void setAssister(Integer assister) {
		this.assister = assister;
	}
	public String getGoalType() {
		return goalType;
	}
	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}
}
