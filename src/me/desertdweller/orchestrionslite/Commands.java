package me.desertdweller.orchestrionslite;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("giveinstrument") && sender.isOp() && sender instanceof Player) {
			if(args.length == 3) {
				for(Sound soundValue : Sound.values()) {
					if(soundValue.name().equals(args[1].toUpperCase())) {
						for(Material materialValue : Material.values()) {
							if(materialValue.name().equals(args[2].toUpperCase())) {
								ItemStack item = BasicInstrument.getInstrument(new ItemStack(Material.valueOf(args[2].toUpperCase())), Sound.valueOf(args[1].toUpperCase()), args[0].replace('_', ' '));
								Player player = (Player) sender;
								player.getInventory().addItem(item);
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED + "That material does not exist.");
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "That sound does not exist.");
				return true;
			}
			if(args.length == 4) {
				for(Sound soundValue : Sound.values()) {
					if(soundValue.name().equals(args[1].toUpperCase())) {
						for(Material materialValue : Material.values()) {
							if(materialValue.name().equals(args[2].toUpperCase())) {
								ItemStack item = BasicInstrument.getInstrument(new ItemStack(Material.valueOf(args[2].toUpperCase())), Sound.valueOf(args[1].toUpperCase()), args[0].replace('_', ' '), Integer.parseInt(args[3]));
								Player player = (Player) sender;
								player.getInventory().addItem(item);
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED + "That material does not exist.");
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "That sound does not exist.");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("giveinstrumentsingle") && sender.isOp() && sender instanceof Player) {
			if(args.length == 4) {
				for(Sound soundValue : Sound.values()) {
					if(soundValue.name().equals(args[1].toUpperCase())) {
						for(Material materialValue : Material.values()) {
							if(materialValue.name().equals(args[2].toUpperCase())) {
								if(args[3].equals("High") && args[3].equals("Low")) {
									sender.sendMessage(ChatColor.RED + "Use only 'High' or 'Low', for the final argument." );
									return true;
								}
								ItemStack item = BasicInstrument.getInstrument(new ItemStack(Material.valueOf(args[2].toUpperCase())), Sound.valueOf(args[1].toUpperCase()), args[0].replace('_', ' '), args[3]);
								Player player = (Player) sender;
								player.getInventory().addItem(item);
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED + "That material does not exist.");
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "That sound does not exist.");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("setmaterial") && sender.isOp() && sender instanceof Player) {
			Player p = (Player) sender;
			ItemStack offhand = p.getInventory().getItemInOffHand();
			ItemStack mainhand = p.getInventory().getItemInMainHand();
			if(offhand != null && mainhand != null) {
				mainhand.setType(offhand.getType());
				NBTItem offnbti = new NBTItem(offhand);
				NBTItem mainnbti = new NBTItem(mainhand);
				for(String key : offnbti.getKeys()) {
					mainnbti.setString(key, offnbti.getString(key));
				}
			}
		}
		return false;
	}
}
