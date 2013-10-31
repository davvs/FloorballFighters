package se.davvs.floorballfighters.summary;

public class PlayerSummary {
	private String name = "";
	private Integer goals = 0;
	private Integer assists = 0;
	private Integer teamGoals = 0;
	private Integer opponentGoals = 0;
	private Integer plusMinus = 0;
	
	private Integer games = 0;
	private Integer wins = 0;
	private Integer losses = 0;
	private Integer ties = 0;
	
	private Integer rankingPoints = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	public Integer getAssists() {
		return assists;
	}

	public void setAssists(Integer assists) {
		this.assists = assists;
	}

	public Integer getTeamGoals() {
		return teamGoals;
	}

	public void setTeamGoals(Integer teamGoals) {
		this.teamGoals = teamGoals;
	}

	public Integer getOpponentGoals() {
		return opponentGoals;
	}

	public void setOpponentGoals(Integer opponentGoals) {
		this.opponentGoals = opponentGoals;
	}

	public Integer getPlusMinus() {
		return plusMinus;
	}

	public void setPlusMinus(Integer plusMinus) {
		this.plusMinus = plusMinus;
	}

	public Integer getGames() {
		return games;
	}

	public void setGames(Integer games) {
		this.games = games;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public Integer getTies() {
		return ties;
	}

	public void setTies(Integer ties) {
		this.ties = ties;
	}

	public Integer getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(Integer rankingPoints) {
		this.rankingPoints = rankingPoints;
	}

}
