package com.jose.exercice.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jose.exercice.forms.CommandForm;
import com.jose.exercice.services.CurrentGameService;

@Controller
public class GameController {

	
	private final CurrentGameService currentGameService;
	
	@Autowired
	public GameController(final CurrentGameService currentGameService) {
		this.currentGameService = currentGameService;		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMainPage(final Map<String, Object> model){
		model.put("commandForm", new CommandForm());
		return "index";
	}
	
	@RequestMapping(value = "/sendCommand", method = RequestMethod.POST)
	public String sendCommand(final CommandForm commandForm){
		commandForm.setOutput(currentGameService.analyseAndExecuteCommand(commandForm.getCommand()).getMessage());
		return "index";
	}
}
