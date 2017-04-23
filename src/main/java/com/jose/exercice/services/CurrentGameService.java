package com.jose.exercice.services;

import com.jose.exercice.wrappers.CommandOutputWrapper;

public interface CurrentGameService {

	CommandOutputWrapper analyseAndExecuteCommand(final String command);
}
