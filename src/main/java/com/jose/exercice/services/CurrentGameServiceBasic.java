package com.jose.exercice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.exercice.entities.CurrentGame;
import com.jose.exercice.enums.CommandsEnum;
import com.jose.exercice.repositories.CurrentGameRepository;
import com.jose.exercice.wrappers.CommandOutPutWrapper;
import com.jose.exercice.wrappers.GameProgressWrapper;

@Service
public class CurrentGameServiceBasic implements CurrentGameService {

	private final CurrentGameRepository currentGameRepository;
	
	@Autowired
	public CurrentGameServiceBasic(final CurrentGameRepository currentGameRepository) {
		this.currentGameRepository = currentGameRepository;
	}
	
	@Override
	public void saveCurrentGame(final CurrentGame currentGame) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public CommandOutPutWrapper analyseAndExecuteCommand(final String command) {
		
		//If the command contains at least one of the avaiable commands
		if(command.contains(CommandsEnum.values().toString())){
		
			
		//If the command was not found
		}else{
			final GameProgressWrapper gameProgressWrapper = new GameProgressWrapper();
			currentGameRepository.findAll().forEach(game ->{
				//Checks is there is a game in progress
				if(game.isProgress()){
					gameProgressWrapper.setProgress(true);
				}
			});
			if(gameProgressWrapper.isProgress()){
				return new CommandOutPutWrapper(true, "input error - please type 'print' for game details");
			}else{
				return new CommandOutPutWrapper(true, "input error - please start a game through typing Start: <Name of Home Team> vs. <Name of Away Team>");
			}
			
		}
		
		
		
		
		return new CommandOutPutWrapper(false, "Ok");
	}

	
	
}
