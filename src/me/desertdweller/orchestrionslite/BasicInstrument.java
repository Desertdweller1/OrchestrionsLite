package me.desertdweller.orchestrionslite;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BasicInstrument implements Listener{
	private static OrchestrionsLite plugin = OrchestrionsLite.getPlugin(OrchestrionsLite.class);
	private static HashMap<Player, Float> playerVolumes = new HashMap<Player, Float>(); 

	public static ItemStack getInstrument(ItemStack item, Sound sound, String name) {
		NBTItem nbti = new NBTItem(item);
		nbti.setString("Plugin", "Orchestrions");
		nbti.setString("Material", "Instrument");
		nbti.setString("Sound", sound.name());
		item = nbti.getItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right-Click to play a note.");
		lore.add(ChatColor.GRAY + "The height of where you are looking");
		lore.add(ChatColor.GRAY + "changes the pitch.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getInstrument(ItemStack item, Sound sound, String name, int octaveDiff) {
		NBTItem nbti = new NBTItem(item);
		nbti.setString("Plugin", "Orchestrions");
		nbti.setString("Material", "Instrument");
		nbti.setString("Sound", sound.name());
		nbti.setInteger("Octave Difference", octaveDiff);
		item = nbti.getItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right-Click to make sound.");
		lore.add(ChatColor.GRAY + "The hieght of where you are looking");
		lore.add(ChatColor.GRAY + "changes the pitch.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getInstrument(ItemStack item, Sound sound, String name, String register) {
		NBTItem nbti = new NBTItem(item);
		nbti.setString("Plugin", "Orchestrions");
		nbti.setString("Material", "Instrument");
		nbti.setString("Sound", sound.name());
		nbti.setString("Register", register);
		item = nbti.getItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right-Click to make sound.");
		lore.add(ChatColor.GRAY + "The hieght of where you are looking");
		lore.add(ChatColor.GRAY + "changes the pitch.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getItem() == null)
			return;
		NBTItem nbti = new NBTItem(e.getItem());
		if(nbti.hasKey("Plugin") && nbti.getString("Plugin").equals("Orchestrions") && nbti.hasKey("Material") && nbti.getString("Material").equals("Instrument")) {
			//Playing instrument
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				InstrumentUseEvent event = new InstrumentUseEvent(e.getPlayer());
				plugin.getServer().getPluginManager().callEvent(event);
				e.setCancelled(true);
				//float soundPitch = ((180 - (e.getPlayer().getLocation().getPitch() + 90)) / 120) + 0.5f; //Infinite note values
				float degreesPerNote = (180f / (float) (NoteFinder.getNoteAmount() - 1));
				float noteAngle = 180 - (e.getPlayer().getLocation().getPitch() + 90f);
				int curOctave = 0;
				if(nbti.hasKey("Octave Difference")) 
					curOctave = nbti.getInteger("Octave Difference");
				if(e.getPlayer().isSneaking()) {
					curOctave -= 1;
				}
				int noteID = (int) (noteAngle/degreesPerNote);
				if(nbti.hasKey("Register")) {
					noteID = (int) ((float) (noteID/2) + (float)(curOctave + 1)*(float) (noteID/2));
				}
				float soundPitch = NoteFinder.getNotePitch(curOctave, noteID);

				if(nbti.hasKey("Register")) {
					if(nbti.getString("Register").equals("High"))
						soundPitch = NoteFinder.getNotePitch(0, noteID);
					if(nbti.getString("Register").equals("Low"))
						soundPitch = NoteFinder.getNotePitch(-1, noteID);
				}
				
				//if(plugin.getBrewery() != null && getRandomNumber(1,3) == 1) //Check if player is drunk
				//	soundPitch = (float) (getRandomNumber(50, 200))/100;
				
				e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + NoteFinder.getNoteName(noteID) + " : " + noteID));
				if(playerVolumes.containsKey(e.getPlayer())) {
					e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.valueOf(nbti.getString("Sound")), playerVolumes.get(e.getPlayer()), soundPitch);
				}else {
					e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.valueOf(nbti.getString("Sound")), 2, soundPitch);
				}
			}else if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				e.setCancelled(true);
				if(playerVolumes.containsKey(e.getPlayer())) {
					float vol = playerVolumes.get(e.getPlayer()) + 0.25f;
					if(vol > plugin.getConfig().getInt("MaxInstrumentVolume")) {
						vol = 0.25f;
					}
					playerVolumes.put(e.getPlayer(), vol);
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "Volume: " + vol));
				}else {
					float vol = 2.25f;
					playerVolumes.put(e.getPlayer(), vol);
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "Volume: " + vol));
				}
			}
		}
	}
	
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
