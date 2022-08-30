/*
 * Copyright (c) 2022, Elias <https://github.com/EliasLahham>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.eliaslahham;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@PluginDescriptor(
	name = "Kephri Number Puzzle Solver"
)
public class KephriNumberPuzzleSolverPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private KephriNumberPuzzleSolverConfig config;

	public static Map<String, String> numberToSolution;
	static {
		numberToSolution = new HashMap<>();
		numberToSolution.put("20", "N");
		numberToSolution.put("21", "E");
		numberToSolution.put("22", "N");
		numberToSolution.put("23", "C");
		numberToSolution.put("24", "M");
		numberToSolution.put("25", "D");
		numberToSolution.put("26", "N");
		numberToSolution.put("27", "A");
		numberToSolution.put("28", "F");
		numberToSolution.put("29", "M");
		numberToSolution.put("30", "W");
		numberToSolution.put("31", "K");
		numberToSolution.put("32", "DE");
		numberToSolution.put("33", "NA");
		numberToSolution.put("34", "DM");
		numberToSolution.put("35", "OA");
		numberToSolution.put("36", "KD");
		numberToSolution.put("37", "KAC");
		numberToSolution.put("38", "DN");
		numberToSolution.put("39", "DNB");
		numberToSolution.put("40", "DO");
		numberToSolution.put("41", "DOC");
		numberToSolution.put("42", "AMD");
		numberToSolution.put("43", "DO");
		numberToSolution.put("44", "ABM");
		numberToSolution.put("45", "WAN");
	}

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		if (chatMessage.getType() == ChatMessageType.GAMEMESSAGE && chatMessage.getMessage().contains("has been hastily chipped into the stone"))
		{
			String solution = "<col=e00a19>" + numberToSolution.get(chatMessage.getMessage().split(" ")[2].substring(12, 14)) + "</col";
			if (config.showSimplifiedSolutionMessage())
			{
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Solution: " + solution, null);
			} else {
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "<col=e00a19>Step 1:</col> Start on the tile in front of the ancient tablet.", null);
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "<col=e00a19>Step 2:</col> Click on the follow letter(s) in order: " + solution, null);
				client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "<col=e00a19>Step 3:</col> If it's still not solved, run North towards the door.", null);
			}
		}
	}


	@Provides
	KephriNumberPuzzleSolverConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KephriNumberPuzzleSolverConfig.class);
	}
}
