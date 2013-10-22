package se.davvs.floorballfighters.day;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.jparepositories.GameRepository;
import se.davvs.floorballfighters.jparepositories.GameTeamMemberRepository;
import se.davvs.floorballfighters.jparepositories.PlayerRepository;
import se.davvs.floorballfighters.models.Day;
import se.davvs.floorballfighters.models.Game;
import se.davvs.floorballfighters.models.GameTeamMember;
import se.davvs.floorballfighters.models.Player;

@Controller
@RequestMapping(value="/day/")
public class DayController {
	@Resource DayRepository dayRepository;
	@Resource PlayerRepository playerRepository;
	@Resource GameRepository gameRepository;
	@Resource GameTeamMemberRepository gameTeamMemberRepository;
	
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

	 @Transactional
	 @RequestMapping(value="/{showDay}/updatePlayers", method = RequestMethod.POST)
	 public String createDay(@PathVariable Integer showDay, PlayersForm createPlayerForm){

		 Day day = dayRepository.findOne(showDay);
		 Set<Player> dayPlayers = day.getPlayers();
		 dayPlayers.clear();
		 for (Integer playerId : createPlayerForm.getPlayers()){
			 Player player = playerRepository.findOne(playerId);
			 Set<Day> playerDays = player.getDays();
			 playerDays.add(day);
			 dayPlayers.add(player);
			 player.setDays(playerDays);
			 playerRepository.saveAndFlush(player);
		 }
		 day.setPlayers(dayPlayers);
		 dayRepository.saveAndFlush(day);
		 return "redirect:/day/" + showDay + "/view";
	 }
	 
	 @RequestMapping(value="/{showDay}/view", method = RequestMethod.GET)
	 public String viewDay(@PathVariable Integer showDay, Model model){
		 Day day = dayRepository.findOne(showDay);
		 model.addAttribute("day", day);
		 model.addAttribute("showDay", showDay);
		 return "day/view";
	 }
	 
	 @RequestMapping(value="/{showDay}/matchMakeAllRandom", method = RequestMethod.POST)
	 public String showHome(@PathVariable Integer showDay, Model model){
		 Day day = dayRepository.findOne(showDay);
		 Game game = new Game();
		 game.setTeam1Score(0);
		 game.setTeam2Score(0);
		 game.setDay(day);
		 gameRepository.save(game);
		 Set<GameTeamMember> gameTeamMembers = new HashSet<GameTeamMember>();
		 
		 List<Object> shuffleGameTeamMembers = Arrays.asList(day.getPlayers().toArray());
		 Collections.shuffle(shuffleGameTeamMembers);
		 
		 boolean vest = false;
		 for (Object o : shuffleGameTeamMembers){
			 Player player = (Player) o;
			 GameTeamMember gameTeamMember = new GameTeamMember();
			 gameTeamMember.setGame(game);
			 gameTeamMember.setPlayer(player);
			 gameTeamMember.setTeam(vest ? 1 : 0);
			 vest = !vest;
			 gameTeamMembers.add(gameTeamMember);
			 Set<GameTeamMember> playerGameTeamMember = player.getGameTeamMembers();
			 playerGameTeamMember.add(gameTeamMember);
			 player.setGameTeamMembers(playerGameTeamMember);
			 gameTeamMemberRepository.save(gameTeamMembers);
		 }

		 return "redirect:/game/" + game.getId() + "/view";
	 }
}
