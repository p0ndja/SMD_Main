package me.palapon2545.SMDMain.EventListener;

import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import me.palapon2545.SMDMain.Main.pluginMain;

public class OnInventoryEvent implements Listener {
	
	pluginMain pl;

	public OnInventoryEvent(pluginMain pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {
			EnchantingInventory inv = (EnchantingInventory) e.getInventory();
			Dye d = new Dye();
			d.setColor(DyeColor.BLUE);
			ItemStack i = d.toItemStack();
			i.setAmount(0);
			inv.setItem(1, i);
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {
			EnchantingInventory inv = (EnchantingInventory) e.getInventory();
			Dye d = new Dye();
			d.setColor(DyeColor.BLUE);
			ItemStack i = d.toItemStack();
			i.setAmount(64);
			inv.setItem(1, i);
		}
	}

}
