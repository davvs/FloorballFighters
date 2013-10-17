package se.davvs.floorballfighters.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	 @RequestMapping(value="/", method = RequestMethod.GET)
	 public String showHome(Model model){
		 //model.addAttribute("secretMessage", "I am secret dynamic content");
		 return "home/index";
	 }
}
