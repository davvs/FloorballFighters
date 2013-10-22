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
	 public String showHome(@PathVariable Integer showGame, Model model){
		 Game game = gameRepository.findOne(showGame);
		 List<GameTeamMember> istPlayers = new LinkedList<GameTeamMember>();
		 List<GameTeamMember> vestPlayers = new LinkedList<GameTeamMember>();

		 for(GameTeamMember gameTeamMember : game.getGameTeamMembers()){
			 if (gameTeamMember.getTeam() == 0){
				 istPlayers.add(gameTeamMember);
			 } else {
				 vestPlayers.add(gameTeamMember);
			 }
		 }
		 model.addAttribute("istPlayers", istPlayers);
		 model.addAttribute("vestPlayers", vestPlayers);
		 return "game/view";
	 }
}
