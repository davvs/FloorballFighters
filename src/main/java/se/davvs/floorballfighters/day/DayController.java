package se.davvs.floorballfighters.day;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.DayPlayerRepository;
import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.jparepositories.PlayerRepository;
import se.davvs.floorballfighters.models.Day;
import se.davvs.floorballfighters.models.DayPlayer;
import se.davvs.floorballfighters.models.Player;



@Controller
@RequestMapping(value="/day/")
public class DayController {
	@Resource DayRepository dayRepository;
	@Resource PlayerRepository playerRepository;
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
//		 dayPlayerRepository.findByDayId(showDay);
		 
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
	 public String createDay(@PathVariable Integer showDay, PlayersForm createPlayerForm, Model model){

		 for (Integer playerId : createPlayerForm.getPlayers()){
			 DayPlayer dp = new DayPlayer();
			 dp.setDayId(showDay);
			 dp.setPlayerId(playerId);
			 dayPlayerRepository.saveAndFlush(dp);
		 }
		 return "redirect:/day/" + showDay + "/view";
	 }
	 
	 @RequestMapping(value="/{showDay}/view", method = RequestMethod.GET)
	 public String viewDay(@PathVariable Integer showDay, Model model){
		 
		 model.addAttribute("showDay", showDay);
		 return "day/view";
	 }
}
