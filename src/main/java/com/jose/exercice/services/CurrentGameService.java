package com.jose.exercice.services;

import com.jose.exercice.entities.CurrentGame;
import com.jose.exercice.wrappers.CommandOutPutWrapper;

public interface CurrentGameService {

	void saveCurrentGame(final CurrentGame currentGame);
	CommandOutPutWrapper analyseAndExecuteCommand(final String command);
}
