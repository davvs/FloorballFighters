package se.davvs.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.GameRepository;
import se.davvs.floorballfighters.models.Game;
import se.davvs.floorballfighters.models.GameTeamMember;
import se.davvs.floorballfighters.models.Player;

@Controller
@RequestMapping(value="/game/")
public class GameController {

	@Resource GameRepository gameRepository;
	
	 @RequestMapping(value="/{showGame}/view", method = RequestMethod.GET)
	 public String showHome(@PathVariable Integer showGame, Model model) throws GameException{
		 Game game = gameRepository.findOne(showGame);
		 List<GameTeamMember> istPlayers = new LinkedList<GameTeamMember>();
		 List<GameTeamMember> vestPlayers = new LinkedList<GameTeamMember>();

		 for(GameTeamMember gameTeamMember : game.getGameTeamMembers()){
			 if (gameTeamMember.getTeam() == 1){
				 istPlayers.add(gameTeamMember);
			 } else if (gameTeamMember.getTeam() == 2) {
				 vestPlayers.add(gameTeamMember);
			 } else {
				 throw new GameException("GameTeamMember is invalid team:" + gameTeamMember.getTeam());
			 }
		 }
		 model.addAttribute("scoreIst", game.getTeam1Score());
		 model.addAttribute("scoreVest", game.getTeam2Score());
		 model.addAttribute("istPlayers", istPlayers);
		 model.addAttribute("vestPlayers", vestPlayers);
		 model.addAttribute("showGame", showGame);
		 model.addAttribute("showDay", game.getDay().getId());
		 return "game/view";
	 }
	 
	 @RequestMapping(value="/{showGame}/score/{team}/{scores}", method = RequestMethod.POST)
	 public String showHome(@PathVariable Integer showGame, @PathVariable String team, @PathVariable Integer scores, Model model) throws GameException{
		 Game game = gameRepository.findOne(showGame);
		 if (team.equals("ist")){
			 game.setTeam1Score(game.getTeam1Score() + scores);
		 } else if (team.equals("vest")){
			 game.setTeam2Score(game.getTeam2Score() + scores);
		 } else {
			 throw new GameException("Invalid team! " + team);
		 }
		 gameRepository.save(game);
		 return "redirect:/game/" + showGame + "/view";
	 }
}
