package se.davvs.floorballfighters.summary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import se.davvs.floorballfighters.jparepositories.DayPlayerRepository;
import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.jparepositories.GameRepository;
import se.davvs.floorballfighters.jparepositories.GameTeamMemberRepository;
import se.davvs.floorballfighters.jparepositories.PlayerRepository;
import se.davvs.floorballfighters.models.Day;
import se.davvs.floorballfighters.models.DayPlayer;
import se.davvs.floorballfighters.models.Game;
import se.davvs.floorballfighters.models.GameTeamMember;
import se.davvs.floorballfighters.models.Goal;

@Service
public class SummaryService {
	@Resource DayRepository dayRepository;
	@Resource PlayerRepository playerRepository;
	@Resource GameRepository gameRepository;
	@Resource GameTeamMemberRepository gameTeamMemberRepository;
	@Resource DayPlayerRepository dayPlayerRepository;
	
	public List<PlayerSummary> getDaySpanScore(List<Day> days){
		return getSummaryForDays(days);
	}
	
	public List<PlayerSummary> getSummaryForDay(Day day){
		List<Day> days = new ArrayList<Day>();
		days.add(day);
		return getSummaryForDays(days);
	}
	
	public List<PlayerSummary> getSummaryForDays(List<Day> days){
		HashMap<Integer, PlayerSummary> playerSummary = new HashMap<Integer, PlayerSummary>();
		
		for (Day day : days) {
			
			Set<Game> games = day.getGames();
			Set<DayPlayer> dayPlayers = day.getDayPlayers();
			
			Integer participatedInTeam;
			
			for(DayPlayer dp : dayPlayers){
				PlayerSummary ps = playerSummary.get(dp.getPlayer().getId());
				if (ps == null){
					ps = new PlayerSummary();
					ps.setId(dp.getPlayer().getId());
				}
				ps.setId(dp.getPlayer().getId());
				ps.setName(dp.getPlayer().getName());
				
				for (Game g : games){
					int friendlyScore = 0, opponentScore = 0;
					participatedInTeam = null;
					Set<GameTeamMember> teamMembers = g.getGameTeamMembers();
					for (GameTeamMember tm : teamMembers){
						if (dp.getPlayer() == tm.getPlayer()){
							participatedInTeam = tm.getTeam();
							ps.setGames(ps.getGames() + 1);
							for (Goal goal : g.getGoals()){
								if (goal.getTeam() == participatedInTeam){
									ps.setTeamGoals(ps.getTeamGoals() + 1);
									friendlyScore ++;
									if (goal.getScorer() != null && goal.getScorer().getPlayer() == dp.getPlayer()){
										ps.setGoals(ps.getGoals() + 1);
									}
									if (goal.getAssister() != null && goal.getAssister().getPlayer() == dp.getPlayer()){
										ps.setAssists(ps.getAssists() + 1);
									}
								} else {
									ps.setOpponentGoals(ps.getOpponentGoals() + 1);
									opponentScore ++;
								}
							}
							break;
						}
					}
	
					if (friendlyScore > opponentScore){
						ps.setWins(ps.getWins() + 1);
					} else if (friendlyScore < opponentScore){
						ps.setLosses(ps.getLosses() + 1);
					} else {
						ps.setTies(ps.getTies() + 1);
					}
				}
				ps.setPlusMinus(ps.getTeamGoals() - ps.getOpponentGoals());
				calculateRankingPoints(ps);
				
				playerSummary.put(ps.getId(), ps);
			}
		}
		
		List<PlayerSummary> playerSummaryList = new ArrayList<PlayerSummary>(playerSummary.values());
		for (PlayerSummary ps : playerSummaryList){
			if (ps.getGames() > 0){
				ps.setRankingPointsPerGame((float)ps.getRankingPoints() / ps.getGames());
				ps.setMatchmaking((float)ps.getMatchmakingBase() / ps.getGames());
			} else {
				ps.setRankingPointsPerGame(0);
				ps.setMatchmaking(0);
			}
			
		}
		//TODO Copy from HashMap
		return sort(playerSummaryList);
	}

	public void calculateRankingPoints(PlayerSummary ps){
		int points = ps.getGames() * 5;
		points += ps.getWins() * 30;
		points += ps.getTies() * 5;
		points += ps.getPlusMinus();
		ps.setMatchmakingBase(points);
		
		points += ps.getGames() * 10;
		ps.setRankingPoints(points);
	}
	
	public List<PlayerSummary> sort(List<PlayerSummary> playerSummary){
		Collections.sort(playerSummary, new Comparator<PlayerSummary>(){
		     public int compare(PlayerSummary p1, PlayerSummary p2){
		         if(p1.getRankingPoints() == p2.getRankingPoints()){
		        	 return p1.getName().compareToIgnoreCase(p2.getName());
		         }
		         return p1.getRankingPoints() > p2.getRankingPoints() ? -1 : 1;
		     }
		});
		return playerSummary;
	}
}
