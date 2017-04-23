package com.jose.exercice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jose.exercice.entities.CurrentGame;
import com.jose.exercice.enums.CommandsEnum;
import com.jose.exercice.repositories.CurrentGameRepository;
import com.jose.exercice.wrappers.CommandOutputWrapper;
import com.jose.exercice.wrappers.GameProgressWrapper;

@Service
public class CurrentGameServiceBasic implements CurrentGameService {

	private final CurrentGameRepository currentGameRepository;
	
	@Autowired
	public CurrentGameServiceBasic(final CurrentGameRepository currentGameRepository) {
		this.currentGameRepository = currentGameRepository;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void saveCurrentGame(final CurrentGame currentGame) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public CommandOutputWrapper analyseAndExecuteCommand(final String command) {
		
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
				return new CommandOutputWrapper(true, "input error - please type 'print' for game details\n");
			}else{
				return new CommandOutputWrapper(true, "input error - please start a game through typing Start: <Name of Home Team> vs. <Name of Away Team>\n");
			}
			
		}
		
		
		
		
		return new CommandOutputWrapper(false, "Ok");
	}

	
	
}
