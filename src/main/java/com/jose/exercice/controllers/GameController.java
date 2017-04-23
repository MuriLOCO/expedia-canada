package com.jose.exercice.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jose.exercice.entities.OutputBox;
import com.jose.exercice.forms.CommandForm;
import com.jose.exercice.services.CurrentGameService;
import com.jose.exercice.services.OutputBoxService;

@Controller
public class GameController {

	private final CurrentGameService currentGameService;
	private final OutputBoxService outputBoxService;
	
	@Autowired
	public GameController(final CurrentGameService currentGameService, final OutputBoxService outputBoxService) {
		this.currentGameService = currentGameService;	
		this.outputBoxService = outputBoxService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMainPage(final Map<String, Object> model){
		model.put("commandForm", new CommandForm());
		return "index";
	}
	
	@RequestMapping(value = "/sendCommand", method = RequestMethod.POST)
	public String sendCommand(final CommandForm commandForm){
				
		final OutputBox outputBox = new OutputBox();
		outputBox.setOutput(currentGameService.analyseAndExecuteCommand(commandForm.getCommand()).getMessage());
		outputBoxService.saveOutPutBox(outputBox);			
		outputBox.setOutput(outputBoxService.concatenateAllOutputBoxex());	
		commandForm.setOutput(outputBox.getOutput());
		
		return "index";
	}
}
