package com.jose.exercice.services;

import com.jose.exercice.entities.CurrentGame;
import com.jose.exercice.wrappers.CommandOutputWrapper;

public interface CurrentGameService {

	void saveCurrentGame(final CurrentGame currentGame);
	CommandOutputWrapper analyseAndExecuteCommand(final String command);
}
