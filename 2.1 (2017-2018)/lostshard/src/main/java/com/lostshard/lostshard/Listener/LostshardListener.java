package com.lostshard.lostshard.Listener;

import org.bukkit.event.Listener;

import com.lostshard.lostshard.main.Lostshard;

public class LostshardListener implements Listener {

	private final Lostshard plugin;

	public LostshardListener(Lostshard plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	public Lostshard getPlugin() {
		return this.plugin;
	}
}