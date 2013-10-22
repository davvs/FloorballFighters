package se.davvs.floorballfighters.home;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.davvs.floorballfighters.jparepositories.DayRepository;
import se.davvs.floorballfighters.models.Day;

@Controller
public class HomeController {
	@Resource DayRepository dayRepository;
	
	 @RequestMapping(value="/", method = RequestMethod.GET)
	 public String showHome(Model model){
		 List<Day> days = dayRepository.findAll();
		 model.addAttribute("days", days);
		 return "home/index";
	 }
}
