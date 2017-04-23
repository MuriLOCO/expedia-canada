package com.jose.exercice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jose.exercice.entities.CurrentGame;
import com.jose.exercice.entities.ScoreRegistry;
import com.jose.exercice.entities.Team;
import com.jose.exercice.enums.CommandsEnum;
import com.jose.exercice.repositories.CurrentGameRepository;
import com.jose.exercice.repositories.ScoreRegistryRepository;
import com.jose.exercice.repositories.TeamRepository;
import com.jose.exercice.wrappers.CommandOutputWrapper;
import com.jose.exercice.wrappers.GameProgressWrapper;

@Service
public class CurrentGameServiceBasic implements CurrentGameService {

	private final CurrentGameRepository currentGameRepository;
	private final TeamRepository teamRepository;
	private final ScoreRegistryRepository scoreRegistryRepository;
	
	@Autowired
	public CurrentGameServiceBasic(final CurrentGameRepository currentGameRepository,
			final TeamRepository teamRepository,
			final ScoreRegistryRepository scoreRegistryRepository) {
		this.currentGameRepository = currentGameRepository;
		this.teamRepository = teamRepository;
		this.scoreRegistryRepository = scoreRegistryRepository;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public CommandOutputWrapper analyseAndExecuteCommand(final String command) {
		//If the command contains at least one of the available commands
		//TODO: Still need (maybe) to cover more kinds of Typos and Errors
			
		if(command.toUpperCase().contains(CommandsEnum.START.toString())
				|| command.toUpperCase().contains(CommandsEnum.MINUTE.toString())
				|| command.toUpperCase().contains(CommandsEnum.TEAM.toString())
				|| command.toUpperCase().contains(CommandsEnum.NAME_OF_SCORER.toString())
				|| command.toUpperCase().contains(CommandsEnum.VS.toString())
				|| command.toUpperCase().contains(CommandsEnum.PRINT.toString())
				|| command.toUpperCase().contains(CommandsEnum.END.toString())
				|| command.toUpperCase().matches(".*\\d+.*")){
			
			final GameProgressWrapper gameProgressWrapper = new GameProgressWrapper();
			currentGameRepository.findAll().forEach(game ->{
				//Checks is there is a game in progress
				if(game.isProgress()){
					gameProgressWrapper.setProgress(true);
				}
			});
			
			if(gameProgressWrapper.isProgress() && !command.toUpperCase().matches(".*\\d+.*") 
					&& command.toUpperCase().contains(CommandsEnum.PRINT.toString()) 
					&& command.toUpperCase().contains(CommandsEnum.END.toString())){
				return new CommandOutputWrapper("A game is already in progress.\n");
			}
			
			if(command.toUpperCase().startsWith(CommandsEnum.START.toString())){
				try{
					if(gameProgressWrapper.isProgress()){
						return new CommandOutputWrapper("A game is currently in progress!\n");
					}
					final String[] commandSplit1 = command.toUpperCase().split(CommandsEnum.START.toString());
					final String[] commandSplit2 = commandSplit1[1].split(CommandsEnum.VS.toString());
					final String homeTeamName = commandSplit2[0].trim();
					final String awayTeamName = commandSplit2[1].trim();
					
					final Team homeTeam = new Team();
					homeTeam.setTeamName(homeTeamName);
					
					final Team awayTeam = new Team();
					awayTeam.setTeamName(awayTeamName);
					
					final CurrentGame currentGame = new CurrentGame();
					currentGame.setHomeTeam(homeTeam);
					currentGame.setAwayTeam(awayTeam);
					currentGame.setProgress(true);
					
					currentGameRepository.save(currentGame);
					
					return new CommandOutputWrapper("Game Started!\nHome Team: " + homeTeamName + "\nAway Team: " + awayTeamName + "\n");
				}catch (Exception e) {										
					if(gameProgressWrapper.isProgress()){
						return new CommandOutputWrapper("input error - please type 'print' for game details\n");
					}else{
						return new CommandOutputWrapper("input error - please start a game through typing Start: <Name of Home Team> vs. <Name of Away Team>\n");
					}
				}
			
			}else if(command.toUpperCase().equals(CommandsEnum.PRINT.toString())){
				if(!gameProgressWrapper.isProgress()){
					return new CommandOutputWrapper("No game currently in progress.");
				}
				final StringBuffer buffer  = new StringBuffer();
				currentGameRepository.findOneByProgress(true).getScoreRegistryList().forEach(score->{
					buffer.append(score.getStringToShow())
					.append("\n");
				});
				
				return new CommandOutputWrapper(buffer.toString());
				
			}else if(command.toUpperCase().equals(CommandsEnum.END.toString()))	{
				if(!gameProgressWrapper.isProgress()){
					return new CommandOutputWrapper("No game currently in progress.");
				}
				
				final CurrentGame currentGame = currentGameRepository.findOneByProgress(true);				
				currentGameRepository.delete(currentGame);				
				return new CommandOutputWrapper("Current game ended!\n");
			}else{
				
				try{
					if(!gameProgressWrapper.isProgress()){
						return new CommandOutputWrapper("No game currently in progress.");
					}
					//Here I split using the Current Game Team Names
					final CurrentGame currentGame = currentGameRepository.findOneByProgress(true);				
					final String homeTeamName = currentGame.getHomeTeam().getTeamName();;
					final String awayTeamName = currentGame.getAwayTeam().getTeamName();
					try{						
						final String[] commandSplit = command.toUpperCase().split(homeTeamName);
					
						final String minute = commandSplit[0].trim();					
						final String nameOfScorer = commandSplit[1].trim();	
						
						final Team awayTeam = teamRepository.findByTeamName(currentGame.getAwayTeam().getTeamName());
						final Team homeTeam = teamRepository.findByTeamName(currentGame.getHomeTeam().getTeamName());				
						final List<ScoreRegistry> scoreRegistryAwayTeamList = scoreRegistryRepository.findByTeamScorer(awayTeam);	
						final List<ScoreRegistry> scoreRegistryHomeTeamList = scoreRegistryRepository.findByTeamScorer(homeTeam);					
						final Team team = teamRepository.findByTeamName(homeTeamName);
						final ScoreRegistry score = new ScoreRegistry();
						score.setMinute(Integer.parseInt(minute));
						score.setTeamScorer(team);
						score.setNameOfScorer(nameOfScorer);	
						final long newScore = scoreRegistryHomeTeamList != null && !scoreRegistryHomeTeamList.isEmpty() ? scoreRegistryHomeTeamList.get(scoreRegistryHomeTeamList.size() - 1).getScore() + 1 : 0 + 1;
						score.setScore(newScore);					
						
						
						final StringBuffer buffer = new StringBuffer();
						buffer.append(currentGame.getHomeTeam().getTeamName() + " ")					
						.append(newScore + " ")						
						.append("(" + nameOfScorer + " ")
						.append(minute + "')")
						.append(" vs ")
						.append(awayTeamName + " ")						
						.append(scoreRegistryAwayTeamList != null && !scoreRegistryAwayTeamList.isEmpty() ? scoreRegistryAwayTeamList.get(scoreRegistryAwayTeamList.size() - 1).getScore() : 0);
						
						score.setStringToShow(buffer.toString());
						currentGame.getScoreRegistryList().add(score);
						
					}catch (Exception e) {						
						final String[] commandSplit = command.toUpperCase().split(awayTeamName);
						
						final String minute = commandSplit[0].trim();						
						final String nameOfScorer = commandSplit[1].trim();
						final Team awayTeam = teamRepository.findByTeamName(currentGame.getAwayTeam().getTeamName());
						final Team homeTeam = teamRepository.findByTeamName(currentGame.getHomeTeam().getTeamName());
						final List<ScoreRegistry> scoreRegistryAwayTeamList = scoreRegistryRepository.findByTeamScorer(awayTeam);	
						final List<ScoreRegistry> scoreRegistryHomeTeamList = scoreRegistryRepository.findByTeamScorer(homeTeam);
						final Team team = teamRepository.findByTeamName(awayTeamName);
						final ScoreRegistry score = new ScoreRegistry();
						score.setMinute(Integer.parseInt(minute));
						score.setTeamScorer(team);
						score.setNameOfScorer(nameOfScorer);	
						final long newScore = scoreRegistryAwayTeamList != null && !scoreRegistryAwayTeamList.isEmpty() ? scoreRegistryAwayTeamList.get(scoreRegistryAwayTeamList.size() - 1).getScore() + 1 : 0 + 1;
						score.setScore(newScore);					
						
						
					
						final StringBuffer buffer = new StringBuffer();
						buffer.append(currentGame.getHomeTeam().getTeamName() + " ")						
						.append(scoreRegistryHomeTeamList != null && !scoreRegistryHomeTeamList.isEmpty() ? scoreRegistryHomeTeamList.get(scoreRegistryHomeTeamList.size() - 1).getScore() : 0)			
						.append(" vs ")
						.append(awayTeamName + " ")						
						.append(newScore + " ")						
						.append("(" + nameOfScorer + " ")
						.append(minute + "')");
						
						
						score.setStringToShow(buffer.toString());
						currentGame.getScoreRegistryList().add(score);
						
					}					
					currentGameRepository.save(currentGame);
				}catch (Exception e) {									
					if(gameProgressWrapper.isProgress()){
						return new CommandOutputWrapper("input error - please type 'print' for game details\n");
					}else{
						return new CommandOutputWrapper("input error - please start a game through typing Start: <Name of Home Team> vs. <Name of Away Team>\n");
					}				
			}
		}
			
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
				return new CommandOutputWrapper("input error - please type 'print' for game details\n");
			}else{
				return new CommandOutputWrapper("input error - please start a game through typing Start: <Name of Home Team> vs. <Name of Away Team>\n");
			}
			
		}
		
		
		
		
		return new CommandOutputWrapper("OK\n");
	}

	
	
}
