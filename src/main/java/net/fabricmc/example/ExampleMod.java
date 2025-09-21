package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.command.ModCommands;
import net.fabricmc.example.conf.config;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class ExampleMod implements ModInitializer {
	@Override
	public void onInitialize() {
		config.setSize(0.3);
		config.setHitboxesEnabled(true);
		config.setGuiOpen(false);
		
		// Register commands
		ModCommands.register(ClientCommandManager.DISPATCHER);
	}
}
