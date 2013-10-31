package se.davvs.floorballfighters.summary;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.models.Day;

@Controller
@RequestMapping(value="/summary/")
public class SummaryController {
	@Resource DayRepository dayRepository;

	@Inject SummaryService summaryService;
	
	@RequestMapping(value="/day/{showDay}", method = RequestMethod.GET)
	public String viewDay(@PathVariable Integer showDay, Model model){
		Day day = dayRepository.findOne(showDay);

		model.addAttribute("summarizedPlayers", summaryService.getSummaryForDay(day));
		model.addAttribute("day", day);
		model.addAttribute("showDay", showDay);
		return "summary/day";
	 }
}
