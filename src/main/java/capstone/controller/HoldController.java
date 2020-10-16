package capstone.controller;

import capstone.domain.Hold;
import capstone.domain.Picked;
import capstone.service.HoldService;
import capstone.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class HoldController {
  @Autowired
  private HoldService holdService;
  
  @GetMapping("")
  public String index(Model model) throws Exception {
	  List<Hold> holds = holdService.libraryNotPicked();
	  model.addAttribute("holdList", holds);
	  return "index";
  }
  
  @GetMapping(value="/picked")
  public String picked(Model model) {
    List<Picked> holds = holdService.libraryPicked();
      model.addAttribute("holdList", holds);
      return "picked";
  }

}
