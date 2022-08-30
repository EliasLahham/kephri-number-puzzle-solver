package com.eliaslahham;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class KephriNumberPuzzleSolver
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(KephriNumberPuzzleSolverPlugin.class);
		RuneLite.main(args);
	}
}