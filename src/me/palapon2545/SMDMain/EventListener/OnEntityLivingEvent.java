package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;
import net.md_5.bungee.api.ChatColor;

public class OnEntityLivingEvent implements Listener {

	pluginMain pl;
	
	public static boolean isInverting = false;

	public OnEntityLivingEvent(pluginMain pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void blockPhysicsEvent(BlockPhysicsEvent e) {
		if (e.getBlock().getType() == Material.SAND || e.getBlock().getType() == Material.GRAVEL) {
			if (Bukkit.getWorld(e.getBlock().getWorld().getName()).getBlockAt(new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY() - 1, e.getBlock().getLocation().getZ())).getType() == Material.AIR);
			if (e.getBlock().getLocation().getY() > 128)
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if (event.getBlock().getType() == Material.WATER || event.getBlock().getType() == Material.LAVA) {
			if (isInverting)
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void entityDamageEvent(EntityDamageEvent e) {
		//Bukkit.broadcastMessage("entityDamage, Type:" + e.getEntity().getName() + " ,Cause:" + e.getCause() + " ,Damage:" + e.getDamage());
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			FileConfiguration playerData = YamlConfiguration.loadConfiguration(
					new File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName()),
							File.separator + "config.yml"));
			if (playerData.getBoolean("god"))
				e.setCancelled(true);

		}
	}
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Location l = p.getLocation();
		
		if (l.getBlock().getType() != Material.AIR) {
			l = new Location(p.getWorld(), l.getX(), (double) p.getWorld().getHighestBlockYAt(l), l.getZ());
		}
		
		Block b = l.getBlock();
		//b.setType(Material.CHEST);
		
		Chest c = (Chest) b.getState();
		
		List<ItemStack> a = e.getDrops();
		for (ItemStack x : a) {
			c.getBlockInventory().addItem(x);
			c.update(true);
		}
	}
	
	@EventHandler
	public void entityDeathEvent(EntityDeathEvent e) {
		
	}

	@EventHandler
	public void entitySpawnEvent(EntitySpawnEvent e) {
		
	}

}
