/*
 * Copyright 2020 Marcus Gonzalez, Adam Houser, Jason Pettit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
