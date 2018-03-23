package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.palapon2545.SMDMain.Function.Shop;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Main.pluginMain;

public class OnPlayerClickSign implements Listener {

	pluginMain pl;
	Shop shop;
	
	public OnPlayerClickSign(Shop shop) {
		this.shop = shop;
	}

	public OnPlayerClickSign(pluginMain pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Action act;
		act = e.getAction();
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL)
			e.setCancelled(true);
		if ((act == Action.RIGHT_CLICK_BLOCK) == false) {
			return;
		}
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		String playerName = player.getName();
		File userdata = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		long money = playerData.getLong("money");
		if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
			Sign s = (Sign) block.getState();
			if (s.getLine(0).contains("[sell]") || s.getLine(0).equalsIgnoreCase("[buy]")) {
				String s0 = s.getLine(0).toLowerCase();
				int sLine_amount = Integer.parseInt(s.getLine(1));
				String s2 = s.getLine(2).toLowerCase();
				String sLine_item = "";
				short sLine_data = 0;
				if (!s2.contains(":")) {
					sLine_item = s.getLine(2);
				} else {
					String[] line2 = s.getLine(2).split(":");
					sLine_item = line2[0];
					sLine_data = (short) Integer.parseInt(line2[1]);
				}
				long sLine_price = Integer.parseInt(s.getLine(3));
				if (!s0.isEmpty() && !s.getLine(1).isEmpty() && !s2.isEmpty() && !s.getLine(3).isEmpty()) {
					if (s0.endsWith("[sell]")) {
						shop.sell(player, sLine_item, sLine_amount, sLine_price, sLine_data);
					}
					if (s0.endsWith("[buy]")) {
						shop.buy(player, sLine_item, sLine_amount, sLine_price, sLine_data);
					}
				} else {
					return;
				}
			}
			int lcq = playerData.getInt("Quota.LuckyClick");
			if (s.getLine(0).equalsIgnoreCase("[luckyclick]")) {
				if (lcq < 1) {
					player.sendMessage(Prefix.sv + "You don't have enough quota!");
					pl.no(player);
					player.sendMessage(Prefix.sv + "Use " + ChatColor.AQUA + "/buyquota LuckyClick" + ChatColor.GRAY
							+ " to buy more quota.");
				} else {
					try {
						playerData.set("Quota.LuckyClick", lcq - 1);
						playerData.save(f);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					int r = new Random().nextInt(20);
					if (r == 0) {
						int r1 = new Random().nextInt(6);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.DIAMOND, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " DIAMOND");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.DIAMOND, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " DIAMOND");
						}
					}
					if (r == 1) {
						int r1 = new Random().nextInt(16);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.IRON_INGOT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " IRON_INGOT");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.IRON_INGOT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " IRON_INGOT");
						}
					}
					if (r == 2) {
						int r1 = new Random().nextInt(501);
						if (r1 == 0) {
							r1 = 1;
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " Coin(s)");
						}
						if (r1 > 0) {
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " Coin(s)");
						}
						try {
							playerData.set("money", money + r1);
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (r == 3) {
						int r1 = new Random().nextInt(21);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.EXP_BOTTLE, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " EXP_BOTTLE");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.EXP_BOTTLE, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " EXP_BOTTLE");
						}
					}
					if (r == 4) {
						int r1 = new Random().nextInt(11);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.GOLD_INGOT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " GOLD_INGOT");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.GOLD_INGOT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " GOLD_INGOT");
						}
					}
					if (r == 5) {
						int r1 = new Random().nextInt(201);
						if (r1 == 0) {
							r1 = 1;
						}
						try {
							playerData.set("money", money - r1);
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "-" + r1 + " Coin(s)");
					}
					if (r == 6) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10));
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "30 second CONFUSED Effect");
					}
					if (r == 7) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 10));
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "1 minute SLOW_DIGGING Effect");
					}
					if (r == 8) {
						int r1 = new Random().nextInt(65);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.DIRT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
									+ ChatColor.RED + r1 + " Dirt");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.DIRT, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
									+ ChatColor.RED + r1 + " Dirt");
						}
					}
					if (r == 9) {
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You don't get "
								+ ChatColor.RED + "ANYTHING");
					}
					if (r == 10) {
						int r1 = new Random().nextInt(16);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.INK_SACK, r1, (short) 4);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " LAPIS_LAZURI");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.INK_SACK, r1, (short) 4);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " LAPIS_LAZURI");
						}
					}
					if (r == 11) {
						int r1 = new Random().nextInt(4);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.EMERALD, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " EMERALD");
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.EMERALD, r1);
							player.getInventory().addItem(item);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " EMERALD");
						}
					}
					if (r == 12) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 10));
						player.sendMessage(Prefix.lc + ChatColor.RED + "Bad Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "1 Minute BLINDNESS Effect");
					}
					if (r == 13) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 10));
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + "10 second REGENERATION Effect");
					}
					if (r == 14) {
						ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + "1 NORMAL_GOLDEN_APPLE");
					}
					if (r == 15) {
						player.sendMessage(Prefix.lc + ChatColor.RED + "Bad Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "I'm Gay Message, LOL!");
						player.chat("I'm Gay~!");
					}
					if (r == 16) {
						int r1 = new Random().nextInt(21);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.REDSTONE, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " REDSTONE");
							player.getInventory().addItem(item);
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.REDSTONE, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " REDSTONE");
							player.getInventory().addItem(item);
						}
					}
					if (r == 17) {
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + "AIR " + ChatColor.GRAY + ChatColor.ITALIC + "(Seriously?)");
					}
					if (r == 18) {
						int r1 = new Random().nextInt(21);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.COAL, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " COAL");
							player.getInventory().addItem(item);
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.COAL, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " COAL");
							player.getInventory().addItem(item);
						}
					}
					if (r == 19) {
						int r1 = new Random().nextInt(31);
						if (r1 == 0) {
							r1 = 1;
							ItemStack item = new ItemStack(Material.COBBLESTONE, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " COBBLESTONE");
							player.getInventory().addItem(item);
						}
						if (r1 > 0) {
							ItemStack item = new ItemStack(Material.COBBLESTONE, r1);
							player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
									+ "You got " + ChatColor.YELLOW + r1 + " COBBLESTONE");
							player.getInventory().addItem(item);
						}
					}
					int lcq2 = playerData.getInt("Quota.LuckyClick");
					player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 10, 0);
					player.sendMessage(
							Prefix.sv + "You have " + ChatColor.LIGHT_PURPLE + lcq2 + " Lucky Click Quota leff!");
				}
			}
			if (s.getLine(0).equalsIgnoreCase("[buyquota]")) {
				int tprq = playerData.getInt("Quota.TPR");
				int homeq = playerData.getInt("Quota.Home");
				int luckyq = playerData.getInt("Quota.LuckyClick");
				if (s.getLine(2).equalsIgnoreCase("home")) {
					if (pl.isNumeric(s.getLine(1))) {
						if (money > (Integer.parseInt(s.getLine(1)) * 5000)) {
							try {
								playerData.set("Quota.Home", homeq + Integer.parseInt(s.getLine(1)));
								playerData.set("money", money - (Integer.parseInt(s.getLine(1)) * 5000));
								playerData.save(f);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							player.sendMessage(Prefix.sv + "You " + ChatColor.YELLOW + "paid "
									+ (Integer.parseInt(s.getLine(1)) * 5000) + " Coins" + ChatColor.GRAY
									+ " to bought " + ChatColor.GREEN + s.getLine(1) + "x Home Quota");
							pl.yes(player);
						} else {
							player.sendMessage(Prefix.sv + Prefix.nom);
							pl.no(player);
						}
					}
				} else if (s.getLine(2).equalsIgnoreCase("tpr")) {
					if (money > (Integer.parseInt(s.getLine(1)) * 300)) {
						try {
							playerData.set("Quota.TPR", tprq + Integer.parseInt(s.getLine(1)));
							playerData.set("money", money - (Integer.parseInt(s.getLine(1)) * 300));
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						player.sendMessage(Prefix.sv + "You " + ChatColor.YELLOW + "paid "
								+ (Integer.parseInt(s.getLine(1)) * 300) + " Coins" + ChatColor.GRAY + " to bought "
								+ ChatColor.GREEN + s.getLine(1) + "x TPR Quota");
						pl.yes(player);
					} else {
						player.sendMessage(Prefix.sv + Prefix.nom);
						pl.no(player);
					}
				}
				if (s.getLine(2).equalsIgnoreCase("luckyclick")) {
					if (money > (Integer.parseInt(s.getLine(1)) * 500)) {
						try {
							playerData.set("Quota.LuckyClick", luckyq + Integer.parseInt(s.getLine(1)));
							playerData.set("money", money - (Integer.parseInt(s.getLine(1)) * 500));
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						player.sendMessage(Prefix.sv + "You " + ChatColor.YELLOW + "paid "
								+ (Integer.parseInt(s.getLine(1)) * 500) + " Coins" + ChatColor.GRAY + " to bought "
								+ ChatColor.GREEN + s.getLine(1) + "x LuckyClick Quota");
						pl.yes(player);
					} else {
						player.sendMessage(Prefix.sv + Prefix.nom);
						pl.no(player);
					}
				}
			}
		} else {
			return;
		}
	}

	
}
