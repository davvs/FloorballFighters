package se.davvs.game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.GameRepository;
import se.davvs.floorballfighters.jparepositories.GameTeamMemberRepository;
import se.davvs.floorballfighters.jparepositories.GoalRepository;
import se.davvs.floorballfighters.models.Game;
import se.davvs.floorballfighters.models.GameTeamMember;
import se.davvs.floorballfighters.models.Goal;

@Controller
@RequestMapping(value="/game/")
public class GameController {

	@Resource GameRepository gameRepository;
	@Resource GoalRepository goalRepository;
	@Resource GameTeamMemberRepository gameTeamMemberRepository;
	
	 @RequestMapping(value="/{showGame}/view", method = RequestMethod.GET)
	 public String showHome(@PathVariable Integer showGame, CustomScoreForm customScoreForm, Model model) throws GameException{
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
		 
		 Integer istScore = game.getTeam1Score();
		 Integer vestScore = game.getTeam2Score();
		 for (Goal goal : game.getGoals()) {
			 if (goal.getTeam() == 2) {
				 vestScore ++;
			 } else	if (goal.getTeam() == 1) {
				 istScore ++;
			 }
 		 }

		 
		 int middle = Math.round((float)AllScoreTypes.allScoreTypes.length / 2);
		 String[] scoreTypesLeft = Arrays.copyOfRange(AllScoreTypes.allScoreTypes, 0, middle);
		 String[] scoreTypesRight = Arrays.copyOfRange(AllScoreTypes.allScoreTypes, middle, AllScoreTypes.allScoreTypes.length);

		 
		 model.addAttribute("scoreIst", istScore);
		 model.addAttribute("scoreVest", vestScore);
		 model.addAttribute("istPlayers", istPlayers);
		 model.addAttribute("vestPlayers", vestPlayers);
		 model.addAttribute("showGame", showGame);
		 model.addAttribute("showDay", game.getDay().getId());
		 model.addAttribute("customScoreForm", customScoreForm);
		 model.addAttribute("scoreTypesLeft", scoreTypesLeft);
		 model.addAttribute("scoreTypesRight", scoreTypesRight);
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
	 
	 @RequestMapping(value="/{showGame}/score/custom", method = RequestMethod.POST)
	 public String showHome(@PathVariable Integer showGame, Model model, CustomScoreForm customScoreForm, BindingResult bindingResult) throws GameException{
		 if (bindingResult.hasErrors()){
			 return "redirect:/game/" + showGame + "/view";
		 }
		 Goal goal = new Goal();
		 GameTeamMember scorer  = null;
		 GameTeamMember assister = null;
		 if (customScoreForm.getScorer() != null){
			 scorer = gameTeamMemberRepository.findOne(customScoreForm.getScorer());
		 }
		 if (customScoreForm.getAssister() != null && customScoreForm.getAssister() != customScoreForm.getScorer()){
			 assister = gameTeamMemberRepository.findOne(customScoreForm.getAssister());
		 }
		 Game game = gameRepository.findOne(customScoreForm.getGameId());
		 
		 try{
			 String s = customScoreForm.getGoalType().replaceAll("goalType", "");
			 Integer goalType = Integer.valueOf(s)  - 1;
			 goal.setGoalType(goalType);
		 } catch (Exception e) {}

		 goal.setAssister(assister);
		 goal.setScorer(scorer);
		 goal.setTeam(customScoreForm.getTeam());
		 goal.setGame(game);
		 goalRepository.save(goal);
		 return "redirect:/game/" + customScoreForm.getGameId() + "/view";
	 }
}
 