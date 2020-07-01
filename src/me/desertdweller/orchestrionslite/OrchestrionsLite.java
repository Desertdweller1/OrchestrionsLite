package me.desertdweller.orchestrionslite;

import org.bukkit.plugin.java.JavaPlugin;

import com.dre.brewery.Brewery;

import me.desertdweller.orchestrions.Orchestrions;
import net.md_5.bungee.api.ChatColor;

public class OrchestrionsLite extends JavaPlugin{

	@Override
	public void onEnable(){
		saveDefaultConfig();
		getConfig();

		if(getOrchestrions() != null) {
			System.out.println(ChatColor.RED + "[OrchestrionsLite] Orchestrions has been found, OrchestrionsLite is redundant and should be removed!");
			return;
		}
		
		if(getBrewery() != null) {
			System.out.println(ChatColor.YELLOW + "[GrandFeasts] Brewery found!");
		}

		getCommand("giveinstrument").setExecutor(new Commands());
		getCommand("setmaterial").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new BasicInstrument(), this);
	}
	
	
	public Orchestrions getOrchestrions() {
		return (Orchestrions) this.getServer().getPluginManager().getPlugin("Orchestrions");
	}
	
	public Brewery getBrewery() {
		return (Brewery) this.getServer().getPluginManager().getPlugin("Brewery");
	}
}
