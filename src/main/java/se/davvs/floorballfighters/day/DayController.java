package se.davvs.floorballfighters.day;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.DayPlayerRepository;
import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.jparepositories.GameRepository;
import se.davvs.floorballfighters.jparepositories.GameTeamMemberRepository;
import se.davvs.floorballfighters.jparepositories.PlayerRepository;
import se.davvs.floorballfighters.models.Day;
import se.davvs.floorballfighters.models.DayPlayer;
import se.davvs.floorballfighters.models.Game;
import se.davvs.floorballfighters.models.GameTeamMember;
import se.davvs.floorballfighters.models.Player;

@Controller
@RequestMapping(value="/day/")
@Transactional
public class DayController {
	@Resource DayRepository dayRepository;
	@Resource PlayerRepository playerRepository;
	@Resource GameRepository gameRepository;
	@Resource GameTeamMemberRepository gameTeamMemberRepository;
	@Resource DayPlayerRepository dayPlayerRepository;
	
	 @RequestMapping(value="/new", method = RequestMethod.POST)
	 public String createDay(Model model){

		 System.out.println("trololol");
		 Day today = new Day();

		 dayRepository.save(today);
		 System.out.println("got day " + today.getId());
		 return "redirect:/day/" + today.getId() + "/players";
	 }
	 
	 @RequestMapping(value="/{showDay}/players", method = RequestMethod.GET)
	 public String showDay(@PathVariable Integer showDay, Model model){
		 
		 System.out.println("day showing is " + showDay);
		 PlayersForm playersForm = new PlayersForm();
		 
		 CreatePlayerForm createPlayerForm = new CreatePlayerForm();
		 createPlayerForm.setDayId(showDay);
		 List<Player> allPlayers = playerRepository.findAll();
		 playersForm.setDayId(showDay);
		 model.addAttribute("allPlayers", allPlayers);
		 model.addAttribute("players", playersForm);
		 model.addAttribute("createPlayerForm", createPlayerForm);
		 return "day/players";
	 }

	 @RequestMapping(value="/{showDay}/createPlayer", method = RequestMethod.POST)
	 public String createDay(@PathVariable Integer showDay, CreatePlayerForm createPlayerForm, Model model){

		 System.out.println("creating " + createPlayerForm.getPlayerName());
		 Player p = new Player();
		 p.setName(createPlayerForm.getPlayerName());
		 playerRepository.save(p);
		 return "redirect:/day/" + showDay + "/players";
	 }

	 @RequestMapping(value="/{showDay}/updatePlayers", method = RequestMethod.POST)
	 public String createDay(@PathVariable Integer showDay, PlayersForm createPlayerForm){
		 if (createPlayerForm.getPlayers() != null){
			 Day day = dayRepository.findOne(showDay);
			 dayPlayerRepository.deleteInBatch(day.getDayPlayers());
	
			 for (Integer playerId : createPlayerForm.getPlayers()){
				 Player player = playerRepository.findOne(playerId);
				 Set<DayPlayer> dp = player.getDayPlayers();
				 DayPlayer newDayPlayer = new DayPlayer();
				 newDayPlayer.setDay(day);
				 newDayPlayer.setPlayer(player);
				 dp.add(newDayPlayer);
				 dayPlayerRepository.saveAndFlush(newDayPlayer);
			 }
		 }
		 return "redirect:/day/" + showDay + "/view";
	 }

	 @RequestMapping(value="/{showDay}/playerToggleTeam/{dayPlayerId}", method = RequestMethod.POST)
	 public String createDay(@PathVariable Integer showDay, @PathVariable Integer dayPlayerId, PlayersForm createPlayerForm){
		 DayPlayer dp = dayPlayerRepository.findOne(dayPlayerId);
		 Integer team = dp.getRequiredTeam();
		 if (team == null){
			 team = 2;
		 } else if (team == 2){
			 team = 1;
		 } else {
			 team = null;
		 }
		 dp.setRequiredTeam(team);
		 dayPlayerRepository.save(dp);
		 return "redirect:/day/" + showDay + "/view";
	 }

	 @RequestMapping(value="/{showDay}/view", method = RequestMethod.GET)
	 public String viewDay(@PathVariable Integer showDay, Model model){
		 Day day = dayRepository.findOne(showDay);
		 Hibernate.initialize(day.getGames());

		 for (DayPlayer dp : day.getDayPlayers()){
			 Hibernate.initialize(dp.getPlayer());
		 }
		 model.addAttribute("day", day);
		 model.addAttribute("showDay", showDay);
		 return "day/view";
	 }
	 
	 @RequestMapping(value="/{showDay}/matchMakeAllRandom", method = RequestMethod.POST)
	 public String showHome(@PathVariable Integer showDay, Model model){
		 Random r = new Random();
		 Day day = dayRepository.findOne(showDay);
		 Game game = new Game();
		 game.setTeam1Score(0);
		 game.setTeam2Score(0);
		 game.setDay(day);
		 gameRepository.save(game);

		 gameTeamMemberRepository.deleteInBatch(game.getGameTeamMembers());
		 
		 Set<GameTeamMember> gameTeamMembers = new HashSet<GameTeamMember>();
		 Set<DayPlayer> dayPlayers = day.getDayPlayers();
		 List<DayPlayer> vestMembers = new LinkedList<DayPlayer>();
		 List<DayPlayer> istMembers = new LinkedList<DayPlayer>();
		 for (DayPlayer dp : dayPlayers){
			 Integer reqTeam = dp.getRequiredTeam();
			 if (reqTeam != null){
				 if (dp.getRequiredTeam() == 1){
					 istMembers.add(dp);
				 } else {
					 vestMembers.add(dp);
				 }
			 }
		 }
		 List<Object> shuffleGameTeamMembers = Arrays.asList(dayPlayers.toArray());
		 Collections.shuffle(shuffleGameTeamMembers);
		 
		 boolean vest = false;
		 for (Object o : shuffleGameTeamMembers){
			 DayPlayer dp = (DayPlayer) o;
			 if (dp.getRequiredTeam() != null) {
				 continue;
			 }
			 
			 if (vestMembers.size() == istMembers.size()){
				 vest = (r.nextInt() % 2) == 0 && vestMembers.size() < 5;
			 } else {
				 vest = (vestMembers.size() < istMembers.size());
			 }

			 if (vest){
				 vestMembers.add(dp);
			 } else {
				 istMembers.add(dp);
			 }
		 }
		 
		 for (DayPlayer dp : vestMembers) {
			 GameTeamMember gameTeamMember = new GameTeamMember();
			 gameTeamMember.setGame(game);
			 gameTeamMember.setPlayer(dp.getPlayer());
			 gameTeamMember.setTeam(2);
			 gameTeamMembers.add(gameTeamMember);
			 gameTeamMemberRepository.save(gameTeamMember);
		 }
		 for (DayPlayer dp : istMembers) {
			 GameTeamMember gameTeamMember = new GameTeamMember();
			 gameTeamMember.setGame(game);
			 gameTeamMember.setPlayer(dp.getPlayer());
			 gameTeamMember.setTeam(1);
			 gameTeamMembers.add(gameTeamMember);
			 gameTeamMemberRepository.save(gameTeamMember);

		 }
		 
		 return "redirect:/game/" + game.getId() + "/view";
	 }

}
