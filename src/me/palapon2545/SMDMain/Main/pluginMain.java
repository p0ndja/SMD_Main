package me.palapon2545.SMDMain.Main;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileDeleteStrategy;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.palapon2545.SMDMain.Core.AFK;
import me.palapon2545.SMDMain.Core.Login;
import me.palapon2545.SMDMain.Core.Money;
import me.palapon2545.SMDMain.Core.PlayerDatabase;
import me.palapon2545.SMDMain.Core.Rank;
import me.palapon2545.SMDMain.EventListener.OnEntityLivingEvent;
import me.palapon2545.SMDMain.EventListener.OnInventoryEvent;
import me.palapon2545.SMDMain.EventListener.OnPlayerCommunication;
import me.palapon2545.SMDMain.EventListener.OnPlayerConnection;
import me.palapon2545.SMDMain.EventListener.OnPlayerMovement;
import me.palapon2545.SMDMain.Function.ActionBarAPI;
import me.palapon2545.SMDMain.Function.AutoSaveWorld;
import me.palapon2545.SMDMain.Function.Blockto113;
import me.palapon2545.SMDMain.Function.BarAPI_api;
import me.palapon2545.SMDMain.Function.Countdown;
import me.palapon2545.SMDMain.Function.Donate;
import me.palapon2545.SMDMain.Function.FreeItem;
import me.palapon2545.SMDMain.Function.Function;
import me.palapon2545.SMDMain.Function.Ping;
import me.palapon2545.SMDMain.Function.Shop;
import me.palapon2545.SMDMain.Function.Effect18to113;
import me.palapon2545.SMDMain.Function.Sound18to113;
import me.palapon2545.SMDMain.Function.Sound18to19;
import me.palapon2545.SMDMain.Function.VersionJa;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class pluginMain extends JavaPlugin implements Listener {

	File tempFile = new File(getDataFolder() + File.separator + "temp.yml");
	FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);

	public void regEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new OnPlayerConnection(this), this);
		pm.registerEvents(new OnPlayerCommunication(this), this);
		pm.registerEvents(new OnPlayerMovement(this), this);
		pm.registerEvents(new OnInventoryEvent(this), this);
		pm.registerEvents(new OnEntityLivingEvent(this), this);
	}

	public static boolean isNumeric(String str) {
		if (str == null)
			return false;

		for (int i = 0; i < str.length(); i++)
			if (Character.isDigit(str.charAt(i)) == false)
				return false;

		return true;
	}

	public final Logger logger = Logger.getLogger("Minecraft");

	public void addList(String key, String... element) {
		List<String> list = getConfig().getStringList(key);
		list.addAll(Arrays.asList(element));
		getConfig().set(key, list);
		saveConfig();
	}

	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().contains("'s data"))
			e.setCancelled(true);

		if (e.getView().getTitle().contains("Free")) {
			if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {

				p.getInventory().addItem(new ItemStack(Blockto113.WOOD_SWORD.bukkitblock(), 1));
				p.getInventory().addItem(new ItemStack(Blockto113.WOOD_AXE.bukkitblock(), 1));
				p.getInventory().addItem(new ItemStack(Blockto113.WOOD_PICKAXE.bukkitblock(), 1));
				p.getInventory().addItem(new ItemStack(Blockto113.WOOD_SPADE.bukkitblock(), 1));
				p.getInventory().addItem(new ItemStack(Blockto113.WOOD_HOE.bukkitblock(), 1));
				p.getInventory().addItem(new ItemStack(Material.BREAD, 16));

				String playerName = p.getName();
				File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
				File f = new File(userdata, File.separator + "config.yml");

				FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
				long money = playerData.getLong("money");
				int tprq = playerData.getInt("Quota.TPR");
				int lcq = playerData.getInt("Quota.LuckyClick");
				int homeq = playerData.getInt("Quota.Home");
				try {
					playerData.set("Quota.Home", homeq + 3);
					playerData.set("Quota.TPR", tprq + 10);
					playerData.set("Quota.LuckyClick", lcq + 15);
					playerData.set("money", money + 1000);
					playerData.save(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				p.closeInventory();
				getConfig().set("free_item." + playerName, true);
				saveConfig();
				p.sendMessage(
						Prefix.server + "You recived " + ChatColor.GREEN + "1x Starter Kit" + ChatColor.GRAY + ".");
				p.sendMessage("+ 1x " + ChatColor.GOLD + "WOODEN_SWORD");
				p.sendMessage("+ 1x " + ChatColor.GOLD + "WOODEN_AXE");
				p.sendMessage("+ 1x " + ChatColor.GOLD + "WOODEN_PICKAXE");
				p.sendMessage("+ 1x " + ChatColor.GOLD + "WOODEN_SPADE");
				p.sendMessage("+ 1x " + ChatColor.GOLD + "WOODEN_HOE");
				p.sendMessage("+ 1000x " + ChatColor.GREEN + "Coins");
				p.sendMessage("+ 3x " + ChatColor.RED + "Sethome Limit Extention");
				p.sendMessage("+ 15x " + ChatColor.LIGHT_PURPLE + "LuckyClick Quota");
				p.sendMessage("+ 10x " + ChatColor.AQUA + "TPR Quota");
				e.setCancelled(true);
			} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
				p.closeInventory();
				e.setCancelled(true);
			} else {
				e.setCancelled(true);
			}
		}

		/*
		 * if (e.getInventory() instanceof EnchantingInventory) { Dye d = new Dye();
		 * d.setColor(DyeColor.BLUE); ItemStack i = d.toItemStack(); if
		 * (e.getCurrentItem().getType() == i.getType()) e.setCancelled(true); }
		 */
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		String message = "";
		if (CommandLabel.equalsIgnoreCase("force") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":force")) {
			if (sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender
					|| sender instanceof Player) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("all")) {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (p.isOp() || p.hasPermission("main.force")) {
								message = "";
								for (int i = 1; i != args.length; i++)
									message += args[i] + " ";
								message = message.replaceAll("&", Prefix.Ampersand);
								for (Player p1 : Bukkit.getOnlinePlayers()) {
									p1.chat(message);
								}

								p.sendMessage(
										Prefix.server + "You forced all online player: " + ChatColor.WHITE + message);
								Function.yes(p);
							} else {
								p.sendMessage(Prefix.permission);
								Function.no(p);
							}

						} else {
							message = "";
							for (int i = 1; i != args.length; i++)
								message += args[i] + " ";
							message = message.replaceAll("&", Prefix.Ampersand);
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.chat(message);
							}
							logger.info("[" + StockInt.pluginName + "] '/force' : You forced all online player: "
									+ message);
						}
					} else if (args[0].equalsIgnoreCase("console")) {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (p.isOp() || p.hasPermission("main.force")) {
								message = "";
								for (int i = 1; i != args.length; i++)
									message += args[i] + " ";
								message = message.replaceAll("&", Prefix.Ampersand);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);
								p.sendMessage(Prefix.server + "You forced player " + ChatColor.GREEN + "CONSOLE"
										+ ChatColor.GRAY + " : " + ChatColor.WHITE + message);
							} else {
								p.sendMessage(Prefix.permission);
								Function.no(p);
							}
						} else {
							message = "";
							for (int i = 1; i != args.length; i++)
								message += args[i] + " ";
							message = message.replaceAll("&", Prefix.Ampersand);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);
							logger.info("[" + StockInt.pluginName + "] '/force' : You ran command: " + message);
						}
					} else if (Bukkit.getServer().getPlayer(args[0]) != null) {
						Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
						String targetPlayerName = targetPlayer.getName();
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (p.isOp() || p.hasPermission("main.force")) {
								message = "";
								for (int i = 1; i != args.length; i++)
									message += args[i] + " ";
								message = message.replaceAll("&", Prefix.Ampersand);
								targetPlayer.chat(message);
								p.sendMessage(Prefix.server + "You forced player " + ChatColor.GREEN + targetPlayerName
										+ ChatColor.GRAY + " : " + ChatColor.WHITE + message);
								Function.yes(p);
							} else {
								p.sendMessage(Prefix.permission);
								Function.no(p);
							}
						} else {
							message = "";
							for (int i = 1; i != args.length; i++)
								message += args[i] + " ";
							message = message.replaceAll("&", Prefix.Ampersand);
							targetPlayer.chat(message);
							logger.info("[" + StockInt.pluginName + "] '/force' : You forced " + targetPlayerName + ": "
									+ ChatColor.AQUA + message);
						}
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							p.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(p);
						} else {
							logger.info("[" + StockInt.pluginName + "] '/force' : Player " + args[0] + " not found");
						}
					}
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.sendMessage(Prefix.server + Prefix.type + "/force [player|console|all] [message]");
						Function.no(p);
					} else {
						logger.info(
								"[" + StockInt.pluginName + "] '/force' : Type: /force [player|console|all] [message]");
					}
				}
			}
		}
		if (CommandLabel.equalsIgnoreCase("countdown")
				|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":countdown")
				|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":cd") || CommandLabel.equalsIgnoreCase("cd")) {
			if (!(sender instanceof Player) || (sender instanceof Player
					&& (sender.isOp() || sender.hasPermission("main.*") || sender.hasPermission("main.countdown")
							|| Rank.getPriority(((Player) sender).getPlayer()) >= 3))) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("start")) {
						String countdownMessage = "null";
						String countdownMessageToPlayer = "";
						if (args.length >= 2) {
							if (isNumeric(args[1])) {
								long i = Long.parseLong(args[1]);
								if (args.length > 2) {
									for (int m = 2; m != args.length; m++)
										message += args[m] + " ";
									message = message.replaceAll("&", Prefix.Ampersand);
									countdownMessage = message;
									countdownMessageToPlayer = "with message " + ChatColor.GREEN + message;
								}
								StockInt.CountdownLength = i;
								StockInt.CountdownStartLength = i;
								StockInt.CountdownMessage = countdownMessage;

								sender.sendMessage(Prefix.server + "Set timer to " + ChatColor.YELLOW + args[1]
										+ " seconds " + countdownMessageToPlayer);
							} else {
								sender.sendMessage(Prefix.server + ChatColor.YELLOW + args[1] + Prefix.non);
							}
						} else {
							sender.sendMessage(Prefix.server + Prefix.type + "/countdown start [second] [message]");
						}
					} else if (args[0].equalsIgnoreCase("stop")) {
						sender.sendMessage(Prefix.server + "Stopped Countdown");

						if (StockInt.BarAPIHook == true) {
							BarAPI_api.sendBarAll(Prefix.cd + "Countdown has been cancelled");
							BarAPI_api.removeBarAll();
						} else {
							ActionBarAPI.sendToAll(Prefix.cd + "Countdown has been cancelled");
						}

						StockInt.CountdownLength = -2;
						StockInt.CountdownStartLength = -2;
						StockInt.CountdownMessage = "null";

					} else {
						sender.sendMessage(Prefix.server + Prefix.type + "/countdown [start/stop] [second]");
						if (sender instanceof Player)
							Function.no((Player) sender);
					}
				} else {
					sender.sendMessage(Prefix.server + Prefix.type + "/countdown [start/stop] [second]");
					if (sender instanceof Player)
						Function.no((Player) sender);
				}
			} else {
				sender.sendMessage(Prefix.permission);
				if (sender instanceof Player)
					Function.no((Player) sender);
			}
		}
		if (CommandLabel.equalsIgnoreCase("clearchat")
				|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":clearchat")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("main.clearchat") || p.hasPermission("main.*") || p.isOp()) {
					int x1 = 1;
					int x2 = 500;
					for (long x = x1; x <= x2; x++) {
						for (Player p1 : Bukkit.getOnlinePlayers()) {
							p1.sendMessage("");
						}
					}
					Bukkit.broadcastMessage(
							Prefix.server + "Chat has been cleaned by " + ChatColor.GREEN + p.getName());
				} else {
					p.sendMessage(Prefix.permission);
					Function.no(p);
				}
			} else {
				int x1 = 1;
				int x2 = 500;
				for (long x = x1; x <= x2; x++) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage("");
					}
				}
				Bukkit.broadcastMessage(Prefix.server + "Chat has been cleaned by " + ChatColor.GREEN + "CONSOLE");
			}
		}
		if (CommandLabel.equalsIgnoreCase("b") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":bc")
				|| CommandLabel.equalsIgnoreCase("b")
				|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":broadcast")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.broadcast")) {
					if (args.length == 0 || args[0].isEmpty()) {
						player.sendMessage(Prefix.server + Prefix.type + "/broadcast [text]");
						Function.no(player);
					} else if (args.length != 0) {
						for (String part : args) {
							if (message != "")
								message += " ";
							message += part;
						}
						message = message.replaceAll("&", Prefix.Ampersand);
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Broadcast> " + ChatColor.WHITE + message);
						Bukkit.broadcastMessage("");
					} else {
						player.sendMessage(Prefix.permission);
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			} else {
				if (args.length != 0) {
					for (String part : args) {
						if (message != "")
							message += " ";
						message += part;
					}
					message = message.replaceAll("&", Prefix.Ampersand);
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Broadcast> " + ChatColor.WHITE + message);
					Bukkit.broadcastMessage("");
				} else {
					logger.info("[" + StockInt.pluginName + "] '/broadcast' : Type : '/broadcast [message]");
				}
			}
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			String playerName = player.getName();
			File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
			File f = new File(userdata, File.separator + "config.yml");
			FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
			if (CommandLabel.equalsIgnoreCase("setspawn")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":setspawn")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.warp")
						|| Rank.getPriority(player) >= 5) {
					Location pl = player.getLocation();
					double plx = pl.getX();
					double ply = pl.getY();
					double plz = pl.getZ();
					double plyaw = pl.getYaw();
					double plpitch = pl.getPitch();
					String plworld = pl.getWorld().getName();
					getConfig().set("spawn" + "." + "spawn" + ".world", plworld);
					getConfig().set("spawn" + "." + "spawn" + ".x", plx);
					getConfig().set("spawn" + "." + "spawn" + ".y", ply);
					getConfig().set("spawn" + "." + "spawn" + ".z", plz);
					getConfig().set("spawn" + "." + "spawn" + ".yaw", plyaw);
					getConfig().set("spawn" + "." + "spawn" + ".pitch", plpitch);
					saveConfig();
					player.sendMessage(Prefix.portal + " Setspawn Complete!");
					Function.yes(player);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("spawn") || CommandLabel.equalsIgnoreCase("ts")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":spawn")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":ts")) {
				String spawn = getConfig().getString("spawn");
				if (spawn != null) {
					Double x = getConfig().getDouble("spawn" + "." + "spawn" + ".x");
					Double y = getConfig().getDouble("spawn" + "." + "spawn" + ".y");
					Double z = getConfig().getDouble("spawn" + "." + "spawn" + ".z");
					float yaw = (float) getConfig().getDouble("spawn" + "." + "spawn" + ".yaw");
					float pitch = (float) getConfig().getDouble("spawn" + "." + "spawn" + ".pitch");
					String world = getConfig().getString("spawn" + "." + "spawn" + ".world");
					World p = Bukkit.getWorld(world);
					Location loc = new Location(p, x, y, z);
					loc.setPitch(pitch);
					loc.setYaw(yaw);
					player.teleport(loc);
					player.sendMessage(
							Prefix.portal + "Teleported to " + ChatColor.YELLOW + "Spawn" + ChatColor.GRAY + ".");
					Function.egg(player);
				} else {
					player.sendMessage(Prefix.portal + "Spawn location not found!");
					Function.no(player);

				}
			}
			if (CommandLabel.equalsIgnoreCase("sethome") || CommandLabel.equalsIgnoreCase("sh")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":sh")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":sethome")) {
				String name = "";
				if (args.length == 0) {
					name = player.getWorld().getName();
				}
				if (args.length == 1) {
					name = args[0];
				}
				int homeq = playerData.getInt("Quota.Home");
				File path = new File(getDataFolder(),
						File.separator + "PlayerDatabase/" + playerName + "/HomeDatabase");
				File[] files = path.listFiles();
				if (files.length >= homeq) {
					player.sendMessage(Prefix.portal + "Your sethome reach limit " + ChatColor.RED + ChatColor.BOLD
							+ ChatColor.UNDERLINE + "(" + homeq + ")");
					player.sendMessage(Prefix.portal + "Please remove your other home");
					player.sendMessage(Prefix.portal + "or buy more sethome " + ChatColor.LIGHT_PURPLE + "(/buyquota)"
							+ ChatColor.GRAY + ".");
					Function.no(player);
				} else {
					File userdata2 = new File(getDataFolder(),
							File.separator + "PlayerDatabase/" + playerName + "/HomeDatabase");
					File f2 = new File(userdata2, File.separator + name + ".yml");
					FileConfiguration playerData2 = YamlConfiguration.loadConfiguration(f2);
					if (!f2.exists()) {
						Location pl = player.getLocation();
						double plx = pl.getX();
						double ply = pl.getY();
						double plz = pl.getZ();
						double plpitch = pl.getPitch();
						double plyaw = pl.getYaw();
						int x = (int) plx;
						int y = (int) ply;
						int z = (int) plz;
						String plw = pl.getWorld().getName();
						try {
							playerData2.createSection("home");
							playerData2.set("home.name", name);
							playerData2.set("home.world", plw);
							playerData2.set("home.x", plx);
							playerData2.set("home.y", ply);
							playerData2.set("home.z", plz);
							playerData2.set("home.pitch", plpitch);
							playerData2.set("home.yaw", plyaw);
							playerData2.save(f2);
						} catch (IOException e) {
							e.printStackTrace();
						}
						player.sendMessage(Prefix.portal + ChatColor.GRAY + "Add " + ChatColor.YELLOW + name
								+ ChatColor.GOLD + " [X: " + x + ", Y: " + y + ", Z: " + z + ", World: " + plw + "]"
								+ ChatColor.GRAY + " as your new home location.");
						player.sendMessage(Prefix.portal + Prefix.type + "'/home " + name + "'" + ChatColor.GRAY
								+ " to get back here.");
						Function.yes(player);
					} else {
						player.sendMessage(Prefix.portal + "Home " + ChatColor.YELLOW + name + ChatColor.GRAY
								+ " is already using");
						Function.no(player);
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("home") || CommandLabel.equalsIgnoreCase("h")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":home")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":h")) {
				String name = "";
				if (args.length == 0) {
					name = player.getWorld().getName();
				}
				if (args.length == 1) {
					name = args[0];
				}
				File userdata2 = new File(getDataFolder(),
						File.separator + "PlayerDatabase/" + playerName + "/HomeDatabase");
				File f2 = new File(userdata2, File.separator + name + ".yml");
				FileConfiguration playerData2 = YamlConfiguration.loadConfiguration(f2);
				if (f2.exists()) {
					double x = playerData2.getDouble("home.x");
					double y = playerData2.getDouble("home.y");
					double z = playerData2.getDouble("home.z");
					double pitch = playerData2.getDouble("home.pitch");
					double yaw = playerData2.getDouble("home.yaw");
					String world = playerData2.getString("home.world");
					World p = Bukkit.getWorld(world);
					Location loc = new Location(p, x, y, z);
					loc.setPitch((float) pitch);
					loc.setYaw((float) yaw);
					player.teleport(loc);
					player.sendMessage(
							Prefix.portal + "Teleported to home " + ChatColor.YELLOW + name + ChatColor.GRAY + ".");
					Function.egg(player);
				} else {
					player.sendMessage(
							Prefix.portal + "Home " + ChatColor.YELLOW + name + ChatColor.GRAY + " not found.");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("listhome") || CommandLabel.equalsIgnoreCase("lh")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":listhome")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":lh")) {
				File path = new File(getDataFolder(),
						File.separator + "PlayerDatabase/" + playerName + "/HomeDatabase");
				File[] files = path.listFiles();
				player.sendMessage(Prefix.portal + "List of your home " + ChatColor.YELLOW + "(" + files.length + ")"
						+ ChatColor.GRAY + " :");
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						String l = files[i].getName().replaceAll(".yml", "");
						FileConfiguration playerData2 = YamlConfiguration.loadConfiguration(files[i]);

						int x = playerData2.getInt("home.x");
						int y = playerData2.getInt("home.y");
						int z = playerData2.getInt("home.z");
						String world = playerData2.getString("home.world");

						player.sendMessage("- " + ChatColor.YELLOW + l + ChatColor.GOLD + " [X: " + x + ", Y: " + y
								+ ", Z: " + z + ", World: " + world + "]");
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("removehome") || CommandLabel.equalsIgnoreCase("rh")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":rh")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":removehome")) {
				String name = "";
				if (args.length == 0) {
					name = player.getWorld().getName();
				}
				if (args.length == 1) {
					name = args[0];
				}
				File userdata2 = new File(getDataFolder(),
						File.separator + "PlayerDatabase/" + playerName + "/HomeDatabase");
				File f2 = new File(userdata2, File.separator + name + ".yml");
				if (f2.exists()) {
					f2.delete();
					player.sendMessage(
							Prefix.portal + "Remove home " + ChatColor.YELLOW + name + ChatColor.GRAY + " complete!");
					Function.yes(player);
				} else {
					player.sendMessage(
							Prefix.portal + "Home " + ChatColor.YELLOW + name + ChatColor.GRAY + " not found.");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("warp") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":warp")) {
				if (args.length == 0) {
					player.sendMessage(
							Prefix.server + "List warp: " + ChatColor.GREEN + getConfig().getStringList("listwarp"));
				}
				if (args.length == 1) {
					File warpdata = new File(getDataFolder(), File.separator + "WarpDatabase/");
					File f1 = new File(warpdata, File.separator + args[0] + ".yml");
					FileConfiguration warpData = YamlConfiguration.loadConfiguration(f1);
					if (f1.exists()) {
						double plx = warpData.getDouble("x");
						double ply = warpData.getDouble("y");
						double plz = warpData.getDouble("z");
						double plyaw = warpData.getDouble("yaw");
						double plpitch = warpData.getDouble("pitch");
						World plw = Bukkit.getWorld(warpData.getString("world"));
						Location loc = new Location(plw, plx, ply, plz);
						loc.setPitch((float) plpitch);
						loc.setYaw((float) plyaw);
						player.teleport(loc);
						player.sendMessage(Prefix.server + "Teleported to Warp " + ChatColor.GREEN + args[0]);
						Function.yes(player);
					} else {
						player.sendMessage(
								Prefix.server + "Warp " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " not found!");
						Function.no(player);
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("setwarp")) {
				if (player.isOp() || player.hasPermission("main.setwarp") || player.hasPermission("main.*")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 1) {
						File warpdata = new File(getDataFolder(), File.separator + "WarpDatabase/");
						File f1 = new File(warpdata, File.separator + args[0] + ".yml");
						FileConfiguration warpData = YamlConfiguration.loadConfiguration(f1);
						if (!f1.exists()) {
							Location pl = player.getLocation();
							double plx = pl.getX();
							double ply = pl.getY();
							double plz = pl.getZ();
							double plpitch = pl.getPitch();
							double plyaw = pl.getYaw();
							String plw = pl.getWorld().getName();
							try {
								warpData.set("x", plx);
								warpData.set("y", ply);
								warpData.set("z", plz);
								warpData.set("yaw", plyaw);
								warpData.set("pitch", plpitch);
								warpData.set("world", plw);
								warpData.save(f1);
							} catch (IOException e) {
								e.printStackTrace();
							}
							addList("listwarp", args[0]);
							player.sendMessage(Prefix.server + "Set warp " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " complete!");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + "Warp " + ChatColor.RED + args[0] + ChatColor.GRAY
									+ " already using!");
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/setwarp [name]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("removewarp")) {
				if (player.isOp() || player.hasPermission("main.warp") || player.hasPermission("main.*")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 1) {
						File warpdata = new File(getDataFolder(), File.separator + "WarpDatabase/");
						File f1 = new File(warpdata, File.separator + args[0] + ".yml");
						if (f1.exists()) {
							f1.delete();
							removeList("listwarp", args[0]);
							player.sendMessage(Prefix.server + "Remove warp " + ChatColor.YELLOW + args[0]
									+ ChatColor.GRAY + " complete!");
							Function.yes(player);
						} else {
							player.sendMessage(
									Prefix.server + "Warp " + ChatColor.RED + args[0] + ChatColor.GRAY + " not found!");
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/removewarp [name]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("gamemode")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":gamemode")
					|| CommandLabel.equalsIgnoreCase("gm")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":gm")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.gamemode")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 0) {
						player.sendMessage(Prefix.server + Prefix.type + "/gamemode [mode] [player] (/gm)");
						player.sendMessage(ChatColor.GREEN + "Available Mode: ");
						player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Survival , S , 0");
						player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Creative , C , 1");
						player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Adventure , A , 2");
						player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Spectator , SP , 3");
					}
					if (args.length > 0) {
						String target = playerName;
						if (args.length == 2) {
							target = args[1];
						}
						Player targetPlayer = Bukkit.getPlayer(target);
						if (targetPlayer != null) {
							if (args[0].equalsIgnoreCase("1") || args[0].startsWith("c")) {
								targetPlayer.setGameMode(GameMode.CREATIVE);
								targetPlayer.sendMessage(Prefix.server + "Your gamemode has been updated to "
										+ ChatColor.GREEN + "Creative.");
								Function.yes(targetPlayer);
							} else if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")
									|| args[0].startsWith("su")) {
								targetPlayer.setGameMode(GameMode.SURVIVAL);
								targetPlayer.sendMessage(Prefix.server + "Your gamemode has been updated to "
										+ ChatColor.YELLOW + "Survival.");
								Function.yes(targetPlayer);
							} else if (args[0].equalsIgnoreCase("2") || args[0].startsWith("a")) {
								targetPlayer.setGameMode(GameMode.ADVENTURE);
								targetPlayer.sendMessage(Prefix.server + "Your gamemode has been updated to "
										+ ChatColor.LIGHT_PURPLE + "Adventure.");
								Function.yes(targetPlayer);
							} else if (args[0].equalsIgnoreCase("3") || args[0].startsWith("sp")) {
								targetPlayer.setGameMode(GameMode.SPECTATOR);
								targetPlayer.sendMessage(Prefix.server + "Your gamemode has been updated to "
										+ ChatColor.AQUA + "Spectator.");
								Function.yes(targetPlayer);
							} else {
								targetPlayer
										.sendMessage(Prefix.server + Prefix.type + "/gamemode [mode] [player] (/gm)");
								targetPlayer.sendMessage(ChatColor.GREEN + "Available Mode: ");
								targetPlayer.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Survival , S , 0");
								targetPlayer.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Creative , C , 1");
								targetPlayer
										.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Adventure , A , 2");
								targetPlayer
										.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Spectator , SP , 3");
							}
						} else {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[1] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("heal") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":heal")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.heal")
						|| Rank.getPriority(player) >= 1) {
					String target = playerName;

					if (args.length == 1) {
						target = args[0];
					}

					if (args[0].equalsIgnoreCase("all")) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							for (PotionEffect Effect : p.getActivePotionEffects()) {
								p.removePotionEffect(Effect.getType());
							}
							p.setHealth(p.getMaxHealth());
							p.setFoodLevel(40);
							p.sendMessage(Prefix.server + ChatColor.LIGHT_PURPLE + "You have been healed!");
							Function.yes(p);
						}
						player.sendMessage(Prefix.server + ChatColor.LIGHT_PURPLE + "You healed " + ChatColor.YELLOW
								+ "all online player" + "!");
						Function.yes(player);
					} else {
						Player targetPlayer = Bukkit.getPlayer(target);
						if (targetPlayer != null && target != "all") {
							targetPlayer.setFoodLevel(40);
							for (PotionEffect Effect : player.getActivePotionEffects()) {
								targetPlayer.removePotionEffect(Effect.getType());
							}
							targetPlayer.setHealth(20);
							targetPlayer.setFoodLevel(40);
							targetPlayer.setAllowFlight(true);
							targetPlayer.sendMessage(Prefix.server + ChatColor.LIGHT_PURPLE + "You have been healed!");
							Function.yes(targetPlayer);
							if (targetPlayer.getName() != playerName) {
								player.sendMessage(Prefix.server + ChatColor.LIGHT_PURPLE + "You healed "
										+ ChatColor.YELLOW + targetPlayer.getName() + "!");
								Function.yes(player);
							}
						} else {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("flyspeed")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":flyspeed")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.fly")
						|| Rank.getPriority(player) >= 1) {
					try {
						float speed = Float.parseFloat(args[0]);
						player.setFlySpeed(speed);
						Function.orb(player);
						player.sendMessage(Prefix.server + "Your flyspeed has been set to " + ChatColor.GREEN + speed
								+ ChatColor.GRAY + ".");
					} catch (NumberFormatException e) {
						player.sendMessage(
								Prefix.server + "That " + ChatColor.RED + args[0] + ChatColor.GRAY + " is not number.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("fly") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":fly")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.fly")
						|| Rank.getPriority(player) >= 1) {
					String target = playerName;
					if (args.length == 1) {
						target = args[0];
					}
					Player targetPlayer = Bukkit.getPlayer(target);
					if (targetPlayer != null) {
						if (targetPlayer.getAllowFlight() == false) {
							targetPlayer.setAllowFlight(true);
							Function.orb(targetPlayer);
							targetPlayer.sendMessage(Prefix.server + "You are now " + ChatColor.GREEN + "flyable.");
							if (target != playerName) {
								Function.orb(player);
								player.sendMessage(Prefix.server + "You " + ChatColor.GREEN + "grant "
										+ ChatColor.YELLOW + playerName + "'s ability " + ChatColor.GRAY + "to fly. ");
							}
						} else {
							targetPlayer.setAllowFlight(false);
							Function.orb(targetPlayer);
							targetPlayer.sendMessage(Prefix.server + "You are " + ChatColor.RED + "no-longer flyable.");
							if (target != playerName) {
								Function.orb(player);
								player.sendMessage(Prefix.server + "You " + ChatColor.RED + "revoke " + ChatColor.YELLOW
										+ playerName + "'s ability " + ChatColor.GRAY + "to fly. ");

							}
						}
					} else {
						player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("stuck")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":stuck")) {
				Location pl = player.getLocation();
				double x = pl.getX();
				double y = (pl.getY() + 0.1);
				double z = pl.getZ();
				double pitch = pl.getPitch();
				double yaw = pl.getYaw();
				World p = pl.getWorld();
				Location loc = new Location(p, x, y, z);
				loc.setPitch((float) pitch);
				loc.setYaw((float) yaw);
				player.teleport(loc);
				player.sendMessage(Prefix.server + ChatColor.YELLOW + "You have been resend your location.");
				Function.anvil(player);
			}
			if (CommandLabel.equalsIgnoreCase("day") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":day")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.time")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					player.sendMessage(Prefix.server + "Set time to " + ChatColor.GOLD + "Day " + ChatColor.GRAY
							+ ChatColor.ITALIC + "(1000 ticks)");
					Function.yes(player);
					w.setTime(1000);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("midday")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":midday")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.time")
						|| Rank.getPriority(player) >= 5) {
					player.sendMessage(Prefix.server + "Set time to " + ChatColor.GOLD + "Midday " + ChatColor.GRAY
							+ ChatColor.ITALIC + "(6000 ticks)");
					World w = ((Player) sender).getWorld();
					Function.yes(player);
					w.setTime(6000);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("night")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":night")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.time")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					player.sendMessage(Prefix.server + "Set time to " + ChatColor.GOLD + "Night " + ChatColor.GRAY
							+ ChatColor.ITALIC + "(13000 ticks)");
					Function.yes(player);
					w.setTime(13000);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("midnight")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":midnight")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.time")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					player.sendMessage(Prefix.server + "Set time to " + ChatColor.GOLD + "Midnight " + ChatColor.GRAY
							+ ChatColor.ITALIC + "(18000 ticks)");
					Function.yes(player);
					w.setTime(18000);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("ping") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":ping")) {
				String target = playerName;
				if (args.length == 1) {
					target = args[0];
				}
				Player targetPlayer = Bukkit.getPlayer(target);
				if (targetPlayer != null) {
					int ping = Ping.get(player);
					ChatColor color = ChatColor.WHITE;
					if (ping < 31) {
						color = ChatColor.AQUA;
					} else if (ping > 30 && ping < 81) {
						color = ChatColor.GREEN;
					} else if (ping > 80 && ping < 151) {
						color = ChatColor.GOLD;
					} else if (ping > 150 && ping < 501) {
						color = ChatColor.RED;
					} else if (ping > 500) {
						color = ChatColor.DARK_RED;
					} else {
						color = ChatColor.WHITE;
					}

					if (playerName == targetPlayer.getName()) {
						player.sendMessage(Prefix.server + "Your ping is " + color + ping + ChatColor.GRAY + " ms.");
					} else {
						player.sendMessage(Prefix.server + ChatColor.YELLOW + targetPlayer.getName() + "'s ping"
								+ ChatColor.GRAY + " is " + color + ping + ChatColor.GRAY + " ms.");
					}
				} else {
					player.sendMessage(
							Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " not found.");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("world")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":world")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.world")
						|| (Rank.getPriority(player) >= 2)) {
					double x = player.getLocation().getX();
					double y = player.getLocation().getY();
					double z = player.getLocation().getZ();
					double pitch = player.getLocation().getPitch();
					double yaw = player.getLocation().getYaw();
					if (args.length == 2 || args.length == 1) {
						if (Bukkit.getWorld(args[0]) != null) {
							World w = Bukkit.getWorld(args[0]);
							if (args.length == 1) {
								Location loc = new Location(w, x, y, z);
								loc.setPitch((float) pitch);
								loc.setYaw((float) yaw);
								player.teleport(loc);
								player.sendMessage(Prefix.server + "Sent " + ChatColor.YELLOW + playerName
										+ ChatColor.GRAY + " to world " + ChatColor.AQUA + args[0]);
								Function.egg(player);
							} else if (args.length == 2 && !args[1].isEmpty()) {
								if (Bukkit.getServer().getPlayer(args[1]) != null) {
									Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
									String targetPlayerName = targetPlayer.getName();
									Location loc = new Location(w, x, y, z);
									loc.setPitch((float) pitch);
									loc.setYaw((float) yaw);
									player.teleport(loc);
									player.sendMessage(Prefix.server + "Sent " + ChatColor.YELLOW + targetPlayerName
											+ ChatColor.GRAY + " to world " + ChatColor.AQUA + args[0]);
									targetPlayer.sendMessage(
											Prefix.server + "You have been sent to world " + ChatColor.GREEN + args[0]
													+ ChatColor.GRAY + " by " + ChatColor.YELLOW + playerName);
									Function.egg(player);
								} else {
									player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[1]
											+ ChatColor.GRAY + " not found.");
									Function.no(player);
								}
							} else {
								player.sendMessage(Prefix.server + Prefix.type + "/world [world] [player]");
								Function.no(player);
							}
						} else {
							player.sendMessage(Prefix.server + "World " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/world [world]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("sun")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.climate")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					if (args.length == 1) {
						if (Bukkit.getServer().getWorld(args[0]) != null) {
							w = Bukkit.getServer().getWorld(args[0]);
						} else {
							w = ((Player) sender).getWorld();
						}
					}
					w.setThundering(false);
					w.setStorm(false);
					player.sendMessage(Prefix.server + "Set weather to " + ChatColor.GOLD + "Sunny" + ChatColor.GRAY
							+ " at world " + ChatColor.GREEN + w.getName() + ChatColor.GRAY + ".");
					Function.yes(player);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("rain")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.climate")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					if (args.length == 1) {
						if (Bukkit.getServer().getWorld(args[0]) != null) {
							w = Bukkit.getServer().getWorld(args[0]);
						} else {
							w = ((Player) sender).getWorld();
						}
					}
					w.setThundering(false);
					w.setStorm(true);
					player.sendMessage(Prefix.server + "Set weather to " + ChatColor.AQUA + "Rain" + ChatColor.GRAY
							+ " at world " + ChatColor.GREEN + w.getName() + ChatColor.GRAY + ".");
					Function.yes(player);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("storm")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.climate")
						|| Rank.getPriority(player) >= 5) {
					World w = ((Player) sender).getWorld();
					if (args.length == 1) {
						if (Bukkit.getServer().getWorld(args[0]) != null) {
							w = Bukkit.getServer().getWorld(args[0]);
						} else {
							w = ((Player) sender).getWorld();
						}
					}
					w.setThundering(true);
					w.setStorm(true);
					player.sendMessage(Prefix.server + "Set weather to " + ChatColor.BLUE + "Storm" + ChatColor.GRAY
							+ " at world " + ChatColor.GREEN + w.getName() + ChatColor.GRAY + ".");
					Function.yes(player);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("tpr") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpr")
					|| CommandLabel.equalsIgnoreCase("tprequest")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tprequest")) {
				int tprq = playerData.getInt("Quota.TPR");
				if (args.length == 1) {
					if (tprq < 1) {
						player.sendMessage(Prefix.portal + "You don't have enough " + ChatColor.YELLOW + "TPR Quota!");
						player.sendMessage(Prefix.portal + "Use " + ChatColor.AQUA + "/buyquota TPR" + ChatColor.GRAY
								+ " to buy more quota.");
						Function.no(player);
					} else {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
							String targetPlayerName = targetPlayer.getName();
							if (targetPlayerName == playerName) {
								player.sendMessage(Prefix.portal + "You can't teleport to yourself!");
							} else if (targetPlayerName != playerName) {
								getConfig().set("Teleport." + targetPlayerName, playerName);
								saveConfig();
								player.sendMessage(Prefix.portal + ChatColor.GREEN + "You sent teleportion request to "
										+ ChatColor.YELLOW + targetPlayerName);
								targetPlayer.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + playerName
										+ ChatColor.GRAY + " sent teleportion request to you");
								targetPlayer.sendMessage(Prefix.portal + ChatColor.GREEN + "/tpaccept "
										+ ChatColor.YELLOW + playerName + ChatColor.GRAY + " to" + ChatColor.GREEN
										+ " accept " + ChatColor.GRAY + "teleportion request.");
								targetPlayer.sendMessage(Prefix.portal + ChatColor.RED + "/tpdeny " + ChatColor.YELLOW
										+ playerName + ChatColor.GRAY + " to" + ChatColor.RED + " deny "
										+ ChatColor.GRAY + "teleportion request.");
								targetPlayer.sendMessage(Prefix.portal + ChatColor.RED
										+ "This teleportation can interrupt by other player request!");
								Function.orb(player);
							}
						} else {
							player.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					}
				} else {
					player.sendMessage(Prefix.portal + Prefix.type + "/tpr [player]");
				}
			}
			if (CommandLabel.equalsIgnoreCase("tpaccept")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpaccept")
					|| CommandLabel.equalsIgnoreCase("tpa")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpa")
					|| CommandLabel.equalsIgnoreCase("tpyes")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpyes")
					|| CommandLabel.equalsIgnoreCase("tpy")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpy")) {
				if (args.length == 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
						String targetPlayerName = targetPlayer.getName();
						File userdata1 = new File(getDataFolder(),
								File.separator + "PlayerDatabase/" + targetPlayerName);
						File f1 = new File(userdata1, File.separator + "config.yml");
						FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
						int tprq = playerData1.getInt("Quota.TPR");
						if (getConfig().getString("Teleport." + playerName) == (targetPlayerName)) {
							getConfig().set("Teleport." + playerName, "None");
							saveConfig();
							player.sendMessage(
									Prefix.portal + ChatColor.GREEN + "You accepted teleportion request from "
											+ ChatColor.YELLOW + targetPlayerName + ".");
							targetPlayer.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + playerName
									+ ChatColor.GREEN + " accepted " + ChatColor.GRAY + "your teleportion request.");
							double x = player.getLocation().getX();
							double y = player.getLocation().getY();
							double z = player.getLocation().getZ();
							double yaw = player.getLocation().getYaw();
							double pitch = player.getLocation().getPitch();
							World w = player.getLocation().getWorld();
							Location loc = new Location(w, x, y, z);
							loc.setPitch((float) pitch);
							loc.setYaw((float) yaw);
							targetPlayer.teleport(loc);
							try {
								playerData1.set("Quota.TPR", tprq - 1);
								playerData1.save(f1);
							} catch (IOException e) {
								e.printStackTrace();
							}
							int tprq2 = playerData1.getInt("Quota.TPR");
							targetPlayer.sendMessage(
									Prefix.portal + "You have " + ChatColor.YELLOW + tprq2 + " TPR Quota left.");
						} else if (getConfig().getString("Teleport." + targetPlayerName) == ("None")) {
							player.sendMessage(Prefix.portal + "You didn't have any request from anyone");
						} else {
							player.sendMessage(Prefix.portal + "You don't have any teleportion request from "
									+ ChatColor.YELLOW + targetPlayerName + ".");
						}
					} else {
						player.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.portal + Prefix.type + "/tpaccept [player]");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("tpdeny")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpdeny")
					|| CommandLabel.equalsIgnoreCase("tpd")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpd")
					|| CommandLabel.equalsIgnoreCase("tpn")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpn")
					|| CommandLabel.equalsIgnoreCase("tpno")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tpno")) {
				if (args.length == 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
						String targetPlayerName = targetPlayer.getName();
						if (getConfig().getString("Teleport." + playerName) == (targetPlayerName)) {
							getConfig().set("Teleport." + playerName, "None");
							saveConfig();
							player.sendMessage(Prefix.portal + ChatColor.RED + "You deny teleportion request from "
									+ ChatColor.YELLOW + targetPlayerName + ".");
							targetPlayer.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + playerName
									+ ChatColor.RED + " deny " + ChatColor.GRAY + "your teleportion request.");
						} else if (getConfig().getString("Teleport." + targetPlayerName).equalsIgnoreCase("None")) {
							player.sendMessage(Prefix.portal + "You didn't have any request from anyone");
						} else {
							player.sendMessage(Prefix.portal + "You don't have any teleportion request from "
									+ ChatColor.YELLOW + targetPlayerName + ".");
						}
					} else {
						player.sendMessage(Prefix.portal + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.portal + "Type: " + ChatColor.GREEN + "/tpdeny [player]");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("mute")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.mute")
						|| Rank.getPriority(player) >= 3) {
					if (args.length > 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
							String targetPlayerName = targetPlayer.getName();
							File userdata1 = new File(getDataFolder(),
									File.separator + "PlayerDatabase/" + targetPlayerName);
							File f1 = new File(userdata1, File.separator + "config.yml");
							FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
							Boolean muteis = playerData1.getBoolean("mute.is");
							if (muteis == false) {
								message = "";
								for (int i = 1; i != args.length; i++)
									message += args[i] + " ";
								message = message.replaceAll("&", Prefix.Ampersand);
								Bukkit.broadcastMessage(ChatColor.BLUE + "Chat> " + ChatColor.GRAY + "Player "
										+ ChatColor.YELLOW + playerName + ChatColor.RED + " revoke " + ChatColor.YELLOW
										+ targetPlayerName + "'s ability " + ChatColor.GRAY + "to chat. ");
								Bukkit.broadcastMessage(ChatColor.BLUE + "Chat> " + ChatColor.GRAY + "Reason: "
										+ ChatColor.YELLOW + message);
								targetPlayer.sendMessage(Prefix.server + "You have been muted.");
								Function.orb(targetPlayer);
								try {
									playerData1.set("mute.is", true);
									playerData1.set("mute.reason", message);
									playerData1.save(f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (muteis == true) {
								Bukkit.broadcastMessage(ChatColor.BLUE + "Chat> " + ChatColor.GRAY + "Player "
										+ ChatColor.YELLOW + playerName + ChatColor.GREEN + " grant " + ChatColor.YELLOW
										+ targetPlayerName + "'s ability " + ChatColor.GRAY + "to chat. ");
								player.sendMessage(
										Prefix.server + "You " + ChatColor.GREEN + "grant " + ChatColor.YELLOW
												+ targetPlayerName + "'s ability " + ChatColor.GRAY + "to chat. ");
								targetPlayer.sendMessage(Prefix.server + "You have been unmuted.");
								Function.orb(targetPlayer);
								try {
									playerData1.set("mute.is", false);
									playerData1.set("mute.reason", "none");
									playerData1.save(f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} else {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/mute [player] [reason]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("warn")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.warn")
						|| Rank.getPriority(player) >= 3) {
					if (args.length > 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Player targetPlayer = player.getServer().getPlayer(args[0]);
							String targetPlayerName = targetPlayer.getName();
							File userdata1 = new File(getDataFolder(),
									File.separator + "PlayerDatabase/" + targetPlayerName);
							File f1 = new File(userdata1, File.separator + "config.yml");
							FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
							int cWarn = playerData1.getInt("warn");
							message = "";
							for (int i = 1; i != args.length; i++)
								message += args[i] + " ";
							message = message.replaceAll("&", Prefix.Ampersand);
							cWarn += 1;
							if (cWarn == 4) {
								cWarn = 3;
								Bukkit.broadcastMessage(Prefix.server + targetPlayerName + " has been banned");
								Bukkit.broadcastMessage(Prefix.server + "Reason: " + ChatColor.YELLOW + message);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
										"ban " + targetPlayerName + " " + message);
							} else {
								Bukkit.broadcastMessage(
										Prefix.server + targetPlayerName + " has been warned (" + cWarn + ")");
								Bukkit.broadcastMessage(Prefix.server + "Reason: " + ChatColor.YELLOW + message);
							}
							try {
								playerData1.set("warn", cWarn);
								playerData1.save(f1);
							} catch (IOException e) {
								e.printStackTrace();
							}
							for (Player p : Bukkit.getOnlinePlayers()) {
								Function.orb(p);
							}
						} else {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}

					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/warn [player] [reason]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("resetwarn")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.warn")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Player targetPlayer = player.getServer().getPlayer(args[0]);
							String targetPlayerName = targetPlayer.getName();
							File userdata1 = new File(getDataFolder(),
									File.separator + "PlayerDatabase/" + targetPlayerName);
							File f1 = new File(userdata1, File.separator + "config.yml");
							FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
							message = "";
							for (int i = 1; i != args.length; i++) // catch args[0]
																	// -> i = 0
								message += args[i] + " ";
							message = message.replaceAll("&", Prefix.Ampersand);
							Bukkit.broadcastMessage(Prefix.server + ChatColor.YELLOW + playerName + ChatColor.GRAY
									+ " reset " + targetPlayerName + "'s warned (0)");
							try {
								playerData1.set("warn", 0);
								playerData1.save(f1);
							} catch (IOException e) {
								e.printStackTrace();
							}
							for (Player p : Bukkit.getOnlinePlayers()) {
								Function.orb(p);
							}
						} else {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/resetwarn [player]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("buyquota")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":buyquota")) {
				long money = playerData.getLong("money");
				int tprq = playerData.getInt("Quota.TPR");
				int lcq = playerData.getInt("Quota.LuckyClick");
				int homeq = playerData.getInt("Quota.Home");
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("tpr")) {
						if (money > 3000) {
							try {
								playerData.set("Quota.TPR", tprq + 15);
								playerData.set("money", money - 3000);
								playerData.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}
							player.sendMessage(Prefix.server + "You " + ChatColor.YELLOW + "paid 3000 Coins"
									+ ChatColor.GRAY + " to bought " + ChatColor.GREEN + "15x TPR Quota");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + Prefix.nom);
							Function.no(player);
						}
					} else if (args[0].equalsIgnoreCase("luckyclick")) {
						if (money > 1500) {
							try {
								playerData.set("Quota.LuckyClick", lcq + 3);
								playerData.set("money", money - 1500);
								playerData.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}
							player.sendMessage(Prefix.server + "You " + ChatColor.YELLOW + "paid 1500 Coins"
									+ ChatColor.GRAY + " to bought " + ChatColor.LIGHT_PURPLE + "3x LuckyClick Quota");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + Prefix.nom);
							Function.no(player);
						}
					} else if (args[0].equalsIgnoreCase("home")) {
						if (money > 5000) {
							try {
								playerData.set("Quota.Home", homeq + 1);
								playerData.set("money", money - 5000);
								playerData.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}
							player.sendMessage(
									Prefix.server + "You " + ChatColor.YELLOW + "paid 5000 Coins" + ChatColor.GRAY
											+ " to bought " + ChatColor.LIGHT_PURPLE + "1x Extend Sethome Limit");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + Prefix.nom);
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/buyquota [tpr|luckyclick|home]");
						Function.egg(player);
					}
				} else {
					player.sendMessage(
							Prefix.server + "Welcome to " + ChatColor.YELLOW + ChatColor.BOLD + "Quota's Shop");
					player.sendMessage(ChatColor.GREEN + "Pricing List " + ChatColor.WHITE + ":");
					player.sendMessage("- " + ChatColor.GREEN + "15x TPR Quota" + ChatColor.YELLOW + " 3000 Coins");
					player.sendMessage(
							"- " + ChatColor.LIGHT_PURPLE + "3x Lucky Click Quota" + ChatColor.YELLOW + " 1500 Coins");
					player.sendMessage(
							"- " + ChatColor.AQUA + "1x Extend Sethome Limit" + ChatColor.YELLOW + " 5000 Coins");
					player.sendMessage(Prefix.type + "/buyquota [tpr|luckyclick|home]");
					Function.egg(player);
				}

			}
			if (CommandLabel.equalsIgnoreCase("rank")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.rank")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 2) {
						if (Bukkit.getServer().getPlayer(args[1]) != null) {
							Player targetPlayer = player.getServer().getPlayer(args[1]);

							if (!Rank.setRank(targetPlayer, args[0])) { // Task unsuccess
								player.sendMessage(Prefix.server + Prefix.type
										+ "/rank [default|vip|helper|staff|builder|admin|owner] "
										+ targetPlayer.getName());
								Function.no(player);
							}

						} else {
							player.sendMessage(Prefix.server + ChatColor.RED + "Player " + ChatColor.YELLOW + args[1]
									+ ChatColor.GRAY + " not found.");
							Function.no(player);
						}
					} else {
						player.sendMessage(ChatColor.BLUE + "Rank> " + Prefix.type
								+ "/rank [default|vip|helper|staff|builder|admin|owner] [player]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("data") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":data")) {
				if (args.length == 0) {
					openDataGUI(player, playerName);
				} else {
					if (player.isOp())
						openDataGUI(player, args[0]);
					else
						openDataGUI(player, playerName);
				}
			}

			if (CommandLabel.equalsIgnoreCase("wikiadmin")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":wikiadmin")) {
				player.sendMessage("Temporary disable.");
			}
			if (CommandLabel.equalsIgnoreCase("wiki") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":wiki")) {
				player.sendMessage("Temporary disable.");
			}

			if (CommandLabel.equalsIgnoreCase("invisible")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.invisible")
						|| Rank.getPriority(player) >= 2) {
					Boolean invi = playerData.getBoolean("Invisible");
					if (invi == false) {
						try {
							playerData.set("Invisible", true);
							playerData.save(f);
						} catch (IOException e) {
							e.printStackTrace();
						}
						player.sendMessage(Prefix.server + "You're now " + ChatColor.AQUA + "invisible.");
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission("main.seeinvisible") || p.isOp() || p.hasPermission("main.*")
									|| Rank.getPriority(player) >= 2)
								p.showPlayer(this, player);
							else
								p.hidePlayer(this, player);

						}
					} else {
						try {
							playerData.set("Invisible", false);
							playerData.save(f);
						} catch (IOException e) {
							e.printStackTrace();
						}
						player.sendMessage(Prefix.server + "You're now " + ChatColor.GREEN + "visible.");
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.showPlayer(this, player);
						}
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("givequota")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.givequota")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 3) {
						Player targetPlayer = Bukkit.getPlayer(args[0]);
						if (targetPlayer != null) {
							File userdata1 = new File(getDataFolder(),
									File.separator + "PlayerDatabase/" + targetPlayer.getName());
							File f1 = new File(userdata1, File.separator + "config.yml");
							FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
							String targetQuota = "";
							if (args[1].equalsIgnoreCase("tpr") || args[1].equalsIgnoreCase("home")
									|| args[1].equalsIgnoreCase("luckyclick")) {
								if (isNumeric(args[2])) {
									if (args[1].equalsIgnoreCase("tpr")) {
										targetQuota = "Quota.TPR";
									} else if (args[1].equalsIgnoreCase("luckyclick")) {
										targetQuota = "Quota.LuckyClick";
									} else if (args[1].equalsIgnoreCase("home")) {
										targetQuota = "Quota.Home";
									}
									int quotabefore = playerData1.getInt(targetQuota);
									int quotatotal = quotabefore + Integer.parseInt(args[2]);
									try {
										playerData1.set(targetQuota, quotatotal);
										playerData1.save(f1);
									} catch (IOException e) {
										e.printStackTrace();
										Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
									}
									player.sendMessage(Prefix.database + "You gave " + ChatColor.AQUA + args[2] + "x "
											+ args[1].toUpperCase() + " Quota " + ChatColor.GRAY + "to "
											+ ChatColor.YELLOW + targetPlayer.getName());
								} else {
									player.sendMessage(Prefix.database + ChatColor.YELLOW + args[2] + Prefix.non);
									Function.no(player);
								}
							} else {
								player.sendMessage(Prefix.database + Prefix.type + "/givequota "
										+ targetPlayer.getName() + " [tpr|luckyclick|home] [amount]");
								Function.no(player);
							}
						} else {
							player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					} else {
						player.sendMessage(
								Prefix.database + Prefix.type + "/givequota [player] [tpr|luckyclick|home] [amount]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("adminchat") || CommandLabel.equalsIgnoreCase("ac")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":ac")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":adminchat")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.adminchat")
						|| (Rank.getPriority(player) >= 2)) {
					if (args.length != 0) {
						for (String part : args) {
							if (message != "")
								message += " ";
							message += part;
						}
						message = message.replaceAll("&", Prefix.Ampersand);
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.isOp() || p.hasPermission("main.*") || p.hasPermission("main.adminchat")
									|| Rank.getPriority(p) >= 2) {
								p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[AdminChat] " + ChatColor.WHITE
										+ player.getName() + " " + ChatColor.YELLOW + message);
								Function.anvil(p);
							}
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/adminchat [message]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("freeze")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":freeze")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.freeze")
						|| Rank.getPriority(player) >= 3) {
					if (args.length == 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Player targetPlayer = player.getServer().getPlayer(args[0]);
							String targetPlayerName = targetPlayer.getName();
							File userdata1 = new File(getDataFolder(),
									File.separator + "PlayerDatabase/" + targetPlayerName);
							File f1 = new File(userdata1, File.separator + "config.yml");
							FileConfiguration playerData1 = YamlConfiguration.loadConfiguration(f1);
							Boolean freeze = playerData1.getBoolean("freeze");
							if (freeze == true) {
								targetPlayer.setAllowFlight(false);
								try {
									playerData1.set("freeze", false);
									playerData1.save(f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
								Function.orb(player);
								Function.orb(targetPlayer);
								player.sendMessage(
										Prefix.server + "You " + ChatColor.GREEN + "grant " + ChatColor.YELLOW
												+ targetPlayerName + "'s ability " + ChatColor.GRAY + "to move.");
							} else {
								player.setAllowFlight(true);
								try {
									playerData1.set("freeze", true);
									playerData1.save(f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
								Function.orb(player, 0);
								Function.no(targetPlayer);
								player.sendMessage(Prefix.server + "You " + ChatColor.RED + "revoke " + ChatColor.YELLOW
										+ targetPlayerName + "'s ability " + ChatColor.GRAY + "to move.");
							}
						}

					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/freeze [player]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("setredeem")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":setredeem")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.redeem")
						|| Rank.getPriority(player) >= 5) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("code")) {
							if (args.length == 2) {
								getConfig().set("redeem.code", args[1]);
								getConfig().set("redeem.player", null);
								saveConfig();
								player.sendMessage(Prefix.database + "Redeem code has been updated to "
										+ ChatColor.GREEN + args[1] + ChatColor.GRAY + ".");
								Function.yes(player);
							} else {
								player.sendMessage(Prefix.database + Prefix.type + "/setredeem code [code]");
							}
						} else if (args[0].equalsIgnoreCase("reward")) {
							if (args.length >= 3) {
								if (args[1].equalsIgnoreCase("add") && args.length >= 4) {
									String item = args[2];
									int data = 0;
									if (args.length == 5) {
										data = Integer.parseInt(args[4]);
									}
									int amount = Integer.parseInt(args[3]);
									String m = item + "," + amount + ",0";
									if (item.equalsIgnoreCase("luckyclick") || item.equalsIgnoreCase("money")
											|| item.equalsIgnoreCase("home") || item.equalsIgnoreCase("tpr")) {
										addList("redeem.reward", m);
										saveConfig();
										player.sendMessage(Prefix.database + "Add " + ChatColor.YELLOW + amount + "x "
												+ item + " Quota " + ChatColor.GRAY + " to redeem reward complete.");
										Function.yes(player);
									} else {
										Material l = Material.getMaterial(item.toUpperCase());
										if (l != null) {
											addList("redeem.reward", item + "," + amount + "," + data);
											saveConfig();
											player.sendMessage(Prefix.database + "Add " + ChatColor.YELLOW + amount
													+ "x " + item + ":" + data + ChatColor.GRAY
													+ " to redeem reward complete.");
											Function.yes(player);
										} else {
											player.sendMessage(Prefix.database + "Item " + item + " not found");
										}
									}
								} else {
									player.sendMessage(Prefix.database + Prefix.type
											+ "/setreward reward [add|reset|list] [item] [amount] [data]");
									Function.no(player);
								}
							} else if (args[1].equalsIgnoreCase("reset")) {
								getConfig().set("redeem.reward", null);
								saveConfig();
								player.sendMessage(Prefix.database + ChatColor.GRAY + "Reset redeem reward complete.");
								Function.yes(player);
							} else if (args[1].equalsIgnoreCase("list")) {
								String reward = getConfig().getString("redeem.reward");
								String[] item = reward.split(" ");
								player.sendMessage(Prefix.database + "List redeem reward");
								for (int i = 0; i < item.length; i++) {
									String[] split = item[i].split(",");
									player.sendMessage("- " + ChatColor.YELLOW + split[1] + "x " + ChatColor.WHITE
											+ split[0].toUpperCase() + ":" + split[2]);
								}
							} else {
								player.sendMessage(Prefix.database + Prefix.type
										+ "/setreward reward [add|reset|list] [item] [amount] [data]");
								Function.no(player);
							}
						}
					} else {
						player.sendMessage(Prefix.database + Prefix.type + "/setredeem [code|reward] [arg]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("resetredeem")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":resetredeem")) {
				if (player.isOp() || player.hasPermission("main.*") || player.hasPermission("main.redeem")
						|| Rank.getPriority(player) >= 5) {
					getConfig().set("redeem.player", null);
					saveConfig();
					player.sendMessage(Prefix.database + ChatColor.GREEN + "Reset redeem complete.");
					Function.yes(player);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("redeem")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":redeem")) {
				if (args.length == 1) {
					String code = getConfig().getString("redeem.code");
					String reward = getConfig().getString("redeem.reward");
					if (args[0].equalsIgnoreCase(code) && !code.equalsIgnoreCase("none") && reward != null) {
						if (getConfig().getString("redeem.player." + playerName) == null
								|| getConfig().getBoolean("redeem.player." + playerName) == false) {

							String[] item = reward.split(" ");
							player.sendMessage(Prefix.database + "Here is your reward!");
							for (int i = 0; i < item.length; i++) {
								String[] split = item[i].split(",");
								player.sendMessage("- " + ChatColor.YELLOW + split[1] + "x " + ChatColor.WHITE
										+ split[0].toUpperCase() + ":" + split[2]);
								if (item[i].equalsIgnoreCase("tpr") || item[i].equalsIgnoreCase("home")
										|| item[i].equalsIgnoreCase("luckyclick")) {

								}
							}

							int tprq = playerData.getInt("Quota.TPR");
							int lcq = playerData.getInt("Quota.LuckyClick");
							try {
								playerData.set("Quota.TPR", tprq + 999);
								playerData.set("Quota.LuckyClick", lcq + 999);
								playerData.save(f);
							} catch (IOException e) {
								e.printStackTrace();
							}

							Function.yes(player);
							getConfig().set("redeem.player." + playerName, true);
							saveConfig();
						} else {
							player.sendMessage(Prefix.database + "You already earn reward from this code. "
									+ ChatColor.YELLOW + "(" + args[0].toUpperCase() + ")");
							Function.no(player);
						}
					} else if (code.equalsIgnoreCase("none")) {
						player.sendMessage(Prefix.database + "There's no redeem code avalible right now!");
						Function.no(player);
					} else if (reward == null) {
						player.sendMessage(Prefix.database + "Reward for this redeem didn't set yet!");
						Function.no(player);
					} else {
						player.sendMessage(Prefix.database + "Your redeem code is incorrect!");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.database + Prefix.type + "/redeem [code]");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("money")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":money")) {
				long money = playerData.getLong("money");
				String coin = " Coins";
				if (money <= 1) {
					coin = " Coin";
				}
				player.sendMessage(
						Prefix.server + "Your balance is " + ChatColor.YELLOW + money + coin + ChatColor.GRAY + ".");
			}
			if (CommandLabel.equalsIgnoreCase("givemoney")) {
				if (player.hasPermission("main.money") || player.isOp() || player.hasPermission("main.*")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 2) {
						Player receiver = Bukkit.getPlayer(args[0]);
						if (receiver != null) {
							if (isNumeric(args[1]) == true) {
								long amount = Long.parseLong(args[1]);
								Money.give(receiver, amount);
							} else {
								player.sendMessage(Prefix.server + ChatColor.YELLOW + args[1] + Prefix.non);
								Function.no(player);
							}
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/givemoney [player] [amount]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}

			}
			if (CommandLabel.equalsIgnoreCase("takemoney")) {
				if (player.hasPermission("main.money") || player.isOp() || player.hasPermission("main.*")
						|| Rank.getPriority(player) >= 5) {
					if (args.length == 2) {
						Player payer = Bukkit.getPlayer(args[0]);
						if (payer != null) {
							if (isNumeric(args[1]) == true) {
								long amount = Long.parseLong(args[1]);
								Money.take(payer, amount);
							} else {
								player.sendMessage(Prefix.server + ChatColor.YELLOW + args[1] + Prefix.non);
								Function.no(player);
							}
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/takemoney [player] [amount]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}

			}
			if (CommandLabel.equalsIgnoreCase("paymoney")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":paymoney")) {
				if (args.length == 2 && (Bukkit.getPlayer(args[0]) != null) && isNumeric(args[1])
						&& Long.parseLong(args[1]) > 0) {
					if (Money.tranfer(player, Bukkit.getPlayer(args[0]), Long.parseLong(args[1]))) {
						String coin = " Coins ";
						if (Long.parseLong(args[1]) <= 1)
							coin = " Coin ";
						Player rlayer = Bukkit.getPlayer(args[0]);
						player.sendMessage(Prefix.server + ChatColor.GRAY + "You paid " + ChatColor.GREEN + args[1]
								+ coin + ChatColor.GRAY + "to " + ChatColor.YELLOW + rlayer.getDisplayName());
						rlayer.sendMessage(Prefix.server + ChatColor.GRAY + "You received " + ChatColor.GREEN + args[1]
								+ coin + ChatColor.GRAY + "from " + ChatColor.YELLOW + player.getDisplayName());
						Function.yes(player);
						Function.yes(rlayer);
					} else {
						player.sendMessage(Prefix.server + Prefix.nom);
						Function.no(player);
					}
				} else {
					if ((Bukkit.getPlayer(args[0]) == null)) {
						player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found.");
						Function.no(player);
					} else if (!isNumeric(args[1])) {
						player.sendMessage(Prefix.server + Prefix.non);
						Function.no(player);
					} else if (Long.parseLong(args[1]) <= 0) {
						player.sendMessage(Prefix.server + Prefix.pm0);
						Function.no(player);
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/paymoney [player] [amount]");
						Function.no(player);
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("register")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":register")
					|| CommandLabel.equalsIgnoreCase("reg")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":reg")) {
				String p = playerData.getString("Security.password");
				String e = playerData.getString("Security.email");
				if (p.equalsIgnoreCase("none") && e.equalsIgnoreCase("none")) {
					if (!StockInt.blockLogin.contains(playerName)) {
						player.sendMessage(Prefix.server + "You're already sign-in!");
						Function.no(player);
					} else {
						if (args.length == 2) {
							if (args[0].length() <= 6) {
								player.sendMessage(
										Prefix.server + ChatColor.YELLOW + "Password need to more than 6 digits!");
								Function.no(player);
							} else if (!args[1].contains("@") || !args[1].contains(".")) {
								player.sendMessage(Prefix.server + ChatColor.YELLOW + "Invalid Email!");
								Function.no(player);
							} else {
								try {
									playerData.set("Security.password", args[0]);
									playerData.set("Security.email", args[1]);
									StockInt.blockLogin.remove(playerName);
									playerData.save(f);
									saveConfig();
									player.setGameMode(GameMode.SURVIVAL);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								player.sendMessage(Prefix.server + "Your password is " + ChatColor.YELLOW + args[0]);
								player.sendMessage(Prefix.server + "If you forgot password, Please " + ChatColor.YELLOW
										+ "contact to fanpage.");
								Function.yes(player);
							}
						} else {
							player.sendMessage(Prefix.server + Prefix.type + "/register [password] [email]");
							Function.no(player);
						}
					}
				} else if (!StockInt.blockLogin.contains(playerName)) {
					player.sendMessage(Prefix.server + "You're already sign-in!");
					Function.no(player);
				} else {
					player.sendMessage(
							Prefix.server + "You're already register! Use /login [password] to login instead!");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("login") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":login")
					|| CommandLabel.equalsIgnoreCase("l")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":l")) {
				String p = playerData.getString("Security.password");
				String e = playerData.getString("Security.email");
				if (!p.equalsIgnoreCase("none") && !e.equalsIgnoreCase("none")) {
					if (!StockInt.blockLogin.contains(playerName)) {
						player.sendMessage(Prefix.server + "You're already sign-in!");
						Function.no(player);
					} else {
						if (args[0].equalsIgnoreCase(p)) {
							StockInt.blockLogin.remove(playerName);
							player.sendMessage(Prefix.server + ChatColor.GREEN + "Sign-in Complete!");
							int g = getConfig().getInt("gamemode." + playerName);
							if (g == 0) {
								player.setGameMode(GameMode.SURVIVAL);
							} else if (g == 1) {
								player.setGameMode(GameMode.CREATIVE);
							} else if (g == 2) {
								player.setGameMode(GameMode.ADVENTURE);
							} else if (g == 3) {
								player.setGameMode(GameMode.SPECTATOR);
							} else {
								player.setGameMode(GameMode.SURVIVAL);
							}
							saveConfig();
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + ChatColor.RED + "Incorrect Password! " + ChatColor.GRAY
									+ "(Forget password? Contact at Fanpage.)");
							Function.no(player);
						}
					}
				} else {
					player.sendMessage(Prefix.server + "You're not register yet! Type /register [password] [email]");
				}
			}
			if (CommandLabel.equalsIgnoreCase("changepassword")) {
				String e = playerData.getString("Security.password");
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase(e)) {
						try {
							playerData.set("Security.password", args[1]);
							playerData.save(f);
						} catch (IOException ea) {
							ea.printStackTrace();
						}
						player.sendMessage(
								Prefix.server + "Your password has been updated to " + ChatColor.GREEN + args[1]);
						Function.yes(player);
					} else {
						player.sendMessage(Prefix.server + ChatColor.RED + "Old password not match to database");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.server + Prefix.type + "/changepassword [oldPass] [newPass]");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("report")) {
				long a = getConfig().getLong("report_count");
				a += 1;
				File report = new File(getDataFolder(), File.separator + "ReportDatabase/");
				File file = new File(report, File.separator + a + ".yml");
				FileConfiguration reportData = YamlConfiguration.loadConfiguration(file);

				if (args.length > 1) {
					if (Bukkit.getServer().getOfflinePlayer(args[0]) != null) {
						Player target = (Player) Bukkit.getServer().getOfflinePlayer(args[0]);
						String c = a + "";
						message = "";
						for (int i = 1; i != args.length; i++)
							message += args[i] + " ";
						message = message.replaceAll("&", Prefix.Ampersand);
						player.sendMessage(Prefix.database + "You " + ChatColor.RED + "report " + ChatColor.LIGHT_PURPLE
								+ args[0]);
						player.sendMessage("Report ID: " + ChatColor.LIGHT_PURPLE + a);
						player.sendMessage("Status: " + ChatColor.YELLOW + "Pending");
						player.sendMessage("Offender: " + ChatColor.AQUA + target.getName());
						player.sendMessage("Reporter: " + ChatColor.GREEN + playerName);
						player.sendMessage("Description: " + ChatColor.WHITE + message);
						getConfig().set("report_count", a);
						try {
							reportData.createSection("Report");
							reportData.set("Report.ID", a);
							reportData.set("Report.Reporter", playerName);
							reportData.set("Report.Offender", args[0]);
							reportData.set("Report.Status", "Pending");
							reportData.set("Report.Description", message);
							reportData.createSection("Inspector");
							reportData.set("Inspector", "none");
							reportData.save(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						addList("unread_report", c);
						saveConfig();
					} else {
						player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.database + Prefix.type + "/report [player] [reason]");
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("listreport")) {
				if (player.hasPermission("main.*") || player.hasPermission("main.report") || player.isOp()
						|| (Rank.getPriority(player) >= 2)) {
					player.sendMessage(Prefix.server + "Unread report ID: " + ChatColor.YELLOW
							+ getConfig().getStringList("unread_report"));
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("checkreport")) {
				if (player.hasPermission("main.*") || player.hasPermission("main.report") || player.isOp()
						|| (Rank.getPriority(player) >= 2)) {
					if (args.length == 1) {
						File report = new File(getDataFolder(), File.separator + "ReportDatabase/");
						File file = new File(report, File.separator + args[0] + ".yml");
						FileConfiguration reportData = YamlConfiguration.loadConfiguration(file);
						if (file.exists()) {
							long id = reportData.getLong("Report.ID");
							String reporter = reportData.getString("Report.Reporter");
							String offender = reportData.getString("Report.Offender");
							String status = reportData.getString("Report.Status");
							String description = reportData.getString("Report.Description");
							player.sendMessage(
									Prefix.database + "You're reading Report ID " + ChatColor.LIGHT_PURPLE + id);
							player.sendMessage("Reporter: " + ChatColor.GREEN + reporter);
							player.sendMessage("Offender: " + ChatColor.AQUA + offender);
							player.sendMessage("Status: " + ChatColor.YELLOW + status);
							player.sendMessage("Inspector: " + ChatColor.GOLD + playerName);
							player.sendMessage("Description: " + ChatColor.WHITE + description);
							try {
								reportData.set("Inspector", playerName);
								reportData.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							Bukkit.broadcastMessage(
									Prefix.database + "Report ID " + args[0] + " has received by " + playerName);
							Function.yesAll();
						} else {
							player.sendMessage(Prefix.database + "Report " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
						}
					} else {
						player.sendMessage(Prefix.database + Prefix.type + "/checkreport [id]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}

			}
			if (CommandLabel.equalsIgnoreCase("closereport")) {
				if (player.hasPermission("main.*") || player.hasPermission("main.report") || player.isOp()
						|| (Rank.getPriority(player) >= 2)) {
					if (args.length == 1) {
						File report = new File(getDataFolder(), File.separator + "ReportDatabase/");
						File file = new File(report, File.separator + args[0] + ".yml");
						FileConfiguration reportData = YamlConfiguration.loadConfiguration(file);
						if (file.exists()) {
							try {
								reportData.set("Report.Status", "Close");
								reportData.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							removeList("unread_report", args[0]);
							Bukkit.broadcastMessage(
									Prefix.server + "Report ID " + args[0] + " has closed by " + playerName);
							Function.yesAll();
						} else {
							player.sendMessage(Prefix.database + "Report " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
						}
					} else {
						player.sendMessage(Prefix.database + Prefix.type + "/closereport [id]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("tell") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":tell")
					|| CommandLabel.equalsIgnoreCase("whisper")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":whisper")
					|| CommandLabel.equalsIgnoreCase("w") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":w")
					|| CommandLabel.equalsIgnoreCase("t") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":t")
					|| CommandLabel.equalsIgnoreCase("pm") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":pm")
					|| CommandLabel.equalsIgnoreCase("privatemessage")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":pm")) {
				if (args.length > 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						message = "";
						for (int i = 1; i != args.length; i++)
							message += args[i] + " ";
						message = message.replaceAll("&", Prefix.Ampersand);
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if (p == player) {
							player.sendMessage(Prefix.server + "Are you kidding? You can't talking with yourself!");
							Function.no(player);
						} else {
							p.sendMessage(ChatColor.AQUA + playerName + ChatColor.WHITE + " to " + ChatColor.GREEN
									+ "You" + ChatColor.WHITE + ": " + message);
							player.sendMessage(ChatColor.AQUA + "You" + ChatColor.WHITE + " to " + ChatColor.GREEN
									+ p.getName() + ChatColor.WHITE + ": " + message);
							File tempFile = new File(getDataFolder() + File.separator + "temp.yml");
							FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
							try {
								tempData.set("chat_last_send." + playerName, p.getName());
								tempData.set("chat_last_send." + p.getName(), playerName);
								tempData.save(tempFile);
							} catch (IOException e) {
								e.printStackTrace();
								Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
							}
						}
					} else {
						player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
								+ " not found or offline.");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.server + Prefix.type + "/tell [player] [message]");
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("reply") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":reply")
					|| CommandLabel.equalsIgnoreCase("r")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":r")) {
				if (args.length > 0) {
					File tempFile = new File(getDataFolder() + File.separator + "temp.yml");
					FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
					if (tempData.getString("chat_last_send." + playerName) != null) {
						message = "";
						for (int i = 0; i != args.length; i++)
							message += args[i] + " ";
						message = message.replaceAll("&", Prefix.Ampersand);
						String lastSent = tempData.getString("chat_last_send." + playerName);
						Player p = Bukkit.getServer().getPlayer(lastSent);
						if (p == null) {
							player.sendMessage(Prefix.server + "Player " + ChatColor.YELLOW + lastSent + ChatColor.GRAY
									+ " not found or offline.");
							Function.no(player);
						} else {
							p.sendMessage(ChatColor.AQUA + playerName + ChatColor.WHITE + " to " + ChatColor.GREEN
									+ "You" + ChatColor.WHITE + ": " + message);
							player.sendMessage(ChatColor.AQUA + "You" + ChatColor.WHITE + " to " + ChatColor.GREEN
									+ p.getName() + ChatColor.WHITE + ": " + message);
						}
					} else {
						player.sendMessage(Prefix.server + "You didn't talk to anyone yet!");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.server + Prefix.type + "/reply [message]");
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("pondjaprivateserver")) {
				if (player.getUniqueId().toString().equalsIgnoreCase("36827ea4-37ac-4907-add3-01d9ba091ef9")) {
					player.sendMessage("now " + !StockInt.privateServerPondJa);
					if (!StockInt.privateServerPondJa) {
						StockInt.privateServerPondJa = true;
						getConfig().set("enable_private_server_model_for_developer_server_only", true);
						saveConfig();
					} else {
						StockInt.privateServerPondJa = false;
						getConfig().set("enable_private_server_model_for_developer_server_only", false);
						saveConfig();
					}
				}
			}

			if (CommandLabel.equalsIgnoreCase("testinvert")) {
				if (player.isOp()) {
					OnEntityLivingEvent.isInverting = true;
					final long start = System.nanoTime();
					double x = player.getLocation().getChunk().getX() * 16;
					double z = player.getLocation().getChunk().getZ() * 16;
					for (int i = 0; i < 128; i++) {
						for (double o = x; o < x + 16; o++) {
							for (double p = z; p < z + 16; p++) {

								Material bottom = Bukkit.getWorld(player.getWorld().getName())
										.getBlockAt(new Location(player.getWorld(), o, i, p)).getType();
								Material top = Bukkit.getWorld(player.getWorld().getName())
										.getBlockAt(new Location(player.getWorld(), o, 255 - i, p)).getType();

								Block bottomN = Bukkit.getWorld(player.getWorld().getName())
										.getBlockAt(new Location(player.getWorld(), o, i, p));
								Block topN = Bukkit.getWorld(player.getWorld().getName())
										.getBlockAt(new Location(player.getWorld(), o, 255 - i, p));

								if (!(bottom == Material.AIR && top == Material.AIR)) {
									topN.setType(bottom);
									bottomN.setType(top);
								}

								// ActionBarAPI.sendToAll(bottom.toString() + " " + o + "," + i + "," + p + " ||
								// " + o + "," + (255-i) + "," + p + " " + top.toString());
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					final long end = System.nanoTime();
					Bukkit.broadcastMessage("Took: " + (end - start) / 1000000000 + " seconds" + " ("
							+ ((end - start) / 1000000) + "ms" + ")");
					Bukkit.dispatchCommand(getServer().getConsoleSender(),
							"title @a title {\"text\":\"Task Completed\",\"color\":\"green\"}");
					Bukkit.dispatchCommand(getServer().getConsoleSender(),
							"title @a subtitle {\"text\":\"Took " + (end - start) / 1000000000 + " seconds" + " ("
									+ ((end - start) / 1000000) + "ms" + ")" + "\"};");
					OnEntityLivingEvent.isInverting = false;
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}

			}

			if (CommandLabel.equalsIgnoreCase("serverinfo")) {

				/* Total number of processors or cores available to the JVM */
				Bukkit.broadcastMessage("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

				/* Total amount of free memory available to the JVM */
				Bukkit.broadcastMessage("Free memory (bytes): " + Runtime.getRuntime().freeMemory());

				/* This will return Long.MAX_VALUE if there is no preset limit */
				long maxMemory = Runtime.getRuntime().maxMemory();
				/* Maximum amount of memory the JVM will attempt to use */
				Bukkit.broadcastMessage(
						"Maximum memory (bytes): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

				/* Total memory currently available to the JVM */
				Bukkit.broadcastMessage("Total memory available to JVM (bytes): " + Runtime.getRuntime().totalMemory());

				/* Get a list of all filesystem roots on this system */
				File[] roots = File.listRoots();

				/* For each filesystem root, print some info */
				for (File root : roots) {
					Bukkit.broadcastMessage("File system root: " + root.getAbsolutePath());
					Bukkit.broadcastMessage("Total space (bytes): " + root.getTotalSpace());
					Bukkit.broadcastMessage("Free space (bytes): " + root.getFreeSpace());
					Bukkit.broadcastMessage("Usable space (bytes): " + root.getUsableSpace());
				}

			}

			if (CommandLabel.equalsIgnoreCase("findbiome")) {
				if (args.length == 1) {

				} else {
					for (Biome b : Biome.values())
						player.sendMessage("- " + b.name());
					player.sendMessage("/findbiome [name]");

				}
			}

			if (CommandLabel.equalsIgnoreCase("test")) {
				if (player.isOp()) {
					ArrayList<Material> a = new ArrayList<Material>();
					a.add(Material.COAL_ORE);
					a.add(Material.DIAMOND_ORE);
					a.add(Material.EMERALD_ORE);
					a.add(Material.GOLD_ORE);
					a.add(Material.IRON_ORE);
					a.add(Material.LAPIS_ORE);
					a.add(Material.REDSTONE_ORE);
					a.add(Material.SPAWNER);
					a.add(Material.MOSSY_COBBLESTONE);
					a.add(Material.AIR);

					if (args[0].isEmpty())
						args[0] = "3";
					int count = Integer.parseInt(args[0]);
					if (count == -1)
						count = 1;

					final long start = System.nanoTime();
					final World w = player.getWorld();
					double x = player.getLocation().getChunk().getX() * 16;
					double z = player.getLocation().getChunk().getZ() * 16;
					for (double o = x; o < x + (16); o++) {
						for (double p = z; p < z + (16); p++) {
							for (int i = 0; i < 128; i++) {
								for (int r = 0; r < count; r++) {
									for (int l = 0; l < count; l++) {
										Block bottomN = w.getBlockAt(new Location(w, o + (16 * r), i, p + (16 * l)));
										Block topN = w.getBlockAt(new Location(w, o + (16 * r), 255 - i, p + (16 * l)));
										if (Integer.parseInt(args[0]) == -1) {
											bottomN.setType(Material.GLASS);
										} else {
											if (!a.contains(bottomN.getType()))
												bottomN.setType(Material.AIR);
											if (!a.contains(topN.getType()))
												topN.setType(Material.AIR);
										}
									}
								}
							}
						}
					}
					final long end = System.nanoTime();
					Bukkit.broadcastMessage("N*N Chunk: " + count + "*" + count);
					Bukkit.broadcastMessage("Took: " + (end - start) / 1000000000 + " seconds" + " ("
							+ ((end - start) / 1000000) + "ms" + ")");
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("god") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":god")) {
				if (player.isOp() || (player.hasPermission("main.god") || player.hasPermission("main.*"))
						|| Rank.getPriority(player) >= 5) {
					boolean g = playerData.getBoolean("god");
					if (g) {
						try {
							playerData.set("god", false);
							playerData.save(f);
						} catch (IOException e) {
							e.printStackTrace();
							Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
						}
						player.sendMessage(Prefix.server + "God mode " + ChatColor.GOLD + "disabled!");
						Function.yes(player);
					} else {
						try {
							playerData.set("god", true);
							playerData.save(f);
						} catch (IOException e) {
							e.printStackTrace();
							Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
						}
						player.sendMessage(Prefix.server + "God mode " + ChatColor.GREEN + "enabled!");
						Function.yes(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("unloadchunk")) {
				for (World w : Bukkit.getWorlds()) {
					Chunk[] a = w.getLoadedChunks();
					for (Chunk c : w.getLoadedChunks()) {
						c.unload();
						if (c.isLoaded() == false) {
							Bukkit.broadcastMessage(
									"unloaded chunk at " + c.getX() + "," + c.getZ() + "," + c.getWorld().getName());
						}
					}
					Chunk[] b = w.getLoadedChunks();
					Bukkit.broadcastMessage(w.getName() + ": " + a.length + " -> " + b.length);
				}
			}

			if (CommandLabel.equalsIgnoreCase("top") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":top")) {
				if (player.isOp() || (player.hasPermission("main.teleport") || player.hasPermission("main.*"))
						|| Rank.getPriority(player) >= 5) {
					double a = player.getWorld().getHighestBlockYAt(player.getLocation());
					player.teleport(new Location(player.getWorld(), player.getLocation().getX(), a + 1,
							player.getLocation().getZ()));
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("donate")) {
				Donate.displayMessage(player);
			}

			if (CommandLabel.equalsIgnoreCase("donateadmin")) {
				if (player.isOp() || player.hasPermission("main.admin") || player.hasPermission("main.*")
						|| Rank.getPriority(player) >= 5) {
					if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("add")) {
							if (args[1] != null && isNumeric(args[1])) {
								StockInt.moneyDonated += Long.parseLong(args[1]);
								player.sendMessage(" += " + args[1] + " to donated =" + StockInt.moneyDonated);
							} else {
								player.sendMessage("invalid_input");
							}
						} else if (args[0].equalsIgnoreCase("set")) {
							if (args[1] != null && isNumeric(args[1])) {
								StockInt.moneyDonated = Long.parseLong(args[1]);
								player.sendMessage(" = " + args[1] + " to donated =" + StockInt.moneyDonated);
							} else {
								player.sendMessage("invalid_input");
							}
						} else if (args[0].equalsIgnoreCase("settarget")) {
							if (args[1] != null && isNumeric(args[1])) {
								StockInt.moneyTargeted = Long.parseLong(args[1]);
								player.sendMessage(" = " + args[1] + " to targetDonated =" + StockInt.moneyTargeted);
							} else {
								player.sendMessage("invalid_input");
							}
						} else {
							player.sendMessage("/donateadmin [add|set|settarget] [args]");
						}
					} else {
						player.sendMessage("/donateadmin [add|set|settarget] [args]");
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}

			if (CommandLabel.equalsIgnoreCase("votetorestart")) {
				if (!StockInt.voteToRestart.contains(playerName)) {
					StockInt.voteToRestart.add(playerName);
					Bukkit.broadcastMessage(Prefix.server + ChatColor.AQUA + playerName + ChatColor.GRAY
							+ " has voted to restart server." + ChatColor.YELLOW + " (" + StockInt.voteToRestart.size()
							+ "/" + Bukkit.getOnlinePlayers().size() + ")");
					if (StockInt.voteToRestart.size() < Bukkit.getOnlinePlayers().size()) {
						Bukkit.broadcastMessage(Prefix.server + "We need more " + ChatColor.GREEN
								+ ((Bukkit.getOnlinePlayers().size() / 2) - StockInt.voteToRestart.size())
								+ ChatColor.GRAY + " for restarting server.");
					} else {
						Bukkit.broadcastMessage(Prefix.server
								+ "More than 50% of Online Player vote to restart server, so " + ChatColor.GOLD
								+ "60 seconds " + ChatColor.GRAY + "before server restarting.");
					}
					Function.yes(player);
				} else {
					StockInt.voteToRestart.remove(playerName);
					Function.yes(player);
					player.sendMessage(Prefix.server + "Unvote success.");
				}

				if (StockInt.voteToRestart.size() >= (Bukkit.getOnlinePlayers().size() / 2)) {
					StockInt.CountdownLength = 30;
					StockInt.CountdownStartLength = 30;
					StockInt.CountdownMessage = "Restart Server (From player voting)";
					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							getServer().dispatchCommand(getServer().getConsoleSender(), "restart");
						}
					}, 600L);
				}
			}

			if (CommandLabel.equalsIgnoreCase("qwerty")) {
				player.sendMessage("" + player.getLocation().getChunk().isSlimeChunk());
			}

			if (CommandLabel.equalsIgnoreCase("afk")) {
				if (AFK.AFKListName.contains(playerName)) {
					AFK.noLongerAFK(player);
				} else {
					AFK.setAFK(player);
					AFK.AFKCount.put(playerName, (long) 600);
				}
			}

			if (CommandLabel.equalsIgnoreCase("enderchest") || CommandLabel.equalsIgnoreCase("ec")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":enderchest")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":ec")) {
				if (player.isOp() || (player.hasPermission("main.ui") || player.hasPermission("main.*"))
						|| Rank.getPriority(player) >= 1) {
					player.openInventory(player.getEnderChest());
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("workbench") || CommandLabel.equalsIgnoreCase("wb")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":workbench")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":wb")) {
				if (player.isOp() || (player.hasPermission("main.ui") || player.hasPermission("main.*"))
						|| Rank.getPriority(player) >= 1) {
					Inventory wb = Bukkit.createInventory(null, InventoryType.WORKBENCH);
					player.openInventory(wb);
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("p") || CommandLabel.equalsIgnoreCase("publish")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":p")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":publish")) {
				if (player.isOp() || (player.hasPermission("main.broadcast") || player.hasPermission("main.*"))) {
					if (args.length != 0) {
						message = "";
						for (int i = 0; i != args.length; i++)
							message += args[i] + " ";
						message = message.replaceAll("&", Prefix.Ampersand);
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(ChatColor.GOLD + "b{" + ChatColor.YELLOW + ChatColor.BOLD + playerName
								+ ChatColor.GOLD + "} " + ChatColor.WHITE + message);
						Bukkit.broadcastMessage("");
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/publish [message]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
			if (CommandLabel.equalsIgnoreCase("free") || CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":free")) {
				Boolean v = getConfig().getBoolean("free_item." + playerName);
				if (v == false)
					FreeItem.openFreeGUI(player);
				else
					player.sendMessage(Prefix.server + "You're already redeem free item.");
			}
			if (CommandLabel.equalsIgnoreCase("changeplayerdatabase")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":changeplayerdatabase")) {
				if (!player.isOp() && Rank.getPriority(player) < 5) {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				} else {
					if (args.length == 2) {
						File oldFolder = new File(getDataFolder(), File.separator + "PlayerDatabase/" + args[0]);
						File newFolder = new File(getDataFolder(), File.separator + "PlayerDatabase/" + args[1]);
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						if (!oldFolder.exists()) {
							player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[0] + "'s "
									+ ChatColor.GRAY + "folder not found.");
							Function.no(player);
						} else if (!newFolder.exists()) {
							player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[1] + "'s "
									+ ChatColor.GRAY + "folder not found.");
							Function.no(player);
						} else if (targetPlayer == null) {
							player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[1] + ChatColor.GRAY
									+ " need to be online or login once time!");
							Function.no(player);
						} else {
							targetPlayer.kickPlayer(ChatColor.BOLD + "Your database has been updated" + ChatColor.WHITE
									+ "\nName:" + args[0] + " -> " + args[1] + ChatColor.GREEN
									+ "\nYou need to re-login to see change.");

							for (File file : newFolder.listFiles()) {
								try {
									FileDeleteStrategy.FORCE.delete(file);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							try {
								Thread.sleep(50);
								FileUtils.forceDelete(newFolder);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							try {
								FileUtils.moveDirectory(oldFolder, newFolder);
							} catch (IOException e) {
								e.printStackTrace();
							}

							for (File file : oldFolder.listFiles()) {
								try {
									FileDeleteStrategy.FORCE.delete(file);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							try {
								Thread.sleep(50);
								FileUtils.forceDelete(oldFolder);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} else {
						player.sendMessage(Prefix.server + Prefix.type + "/changeplayerdatabase [oldName] [newName]");
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("resetplayerdatabase")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":resetplayerdatabase")) {
				if (!player.isOp() && Rank.getPriority(player) < 5) {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				} else {
					if (args.length == 1) {
						File targetFolder = new File(getDataFolder(), File.separator + "PlayerDatabase/" + args[0]);
						if (!targetFolder.exists()) {
							player.sendMessage(Prefix.database + "Database called " + ChatColor.YELLOW + args[0]
									+ ChatColor.GRAY + " not found.");
							Function.no(player);
						} else {
							if (Bukkit.getPlayer(args[0]) != null) {
								Player target = Bukkit.getPlayer(args[0]);
								target.kickPlayer(Prefix.database + "Your database has been updated\n"
										+ StockInt.pluginName + "'s Data: " + ChatColor.GOLD + "RESET" + ChatColor.GREEN
										+ "\nYou need to re-login to see change.");
							}
							for (File file : targetFolder.listFiles()) {
								try {
									FileDeleteStrategy.FORCE.delete(file);
								} catch (IOException e) {
									e.printStackTrace();
									Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
								}
							}
							targetFolder.delete();
							player.sendMessage(Prefix.database + "Delete " + ChatColor.RED + args[0] + "'s database"
									+ ChatColor.GRAY + " complete.");
							Function.yes(player);
						}
					} else {
						player.sendMessage(Prefix.database + Prefix.type + "/resetplayerdatabase [playerName]");
						Function.no(player);
					}
				}
			}
			if (CommandLabel.equalsIgnoreCase("resetfree")
					|| CommandLabel.equalsIgnoreCase(StockInt.pluginName + ":resetfree")) {
				if (player.isOp() || Rank.getPriority(player) >= 5) {
					if (args.length == 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player targetPlayer = Bukkit.getPlayer(args[0]);
							getConfig().set("free_item." + targetPlayer.getName(), false);
							saveConfig();
							player.sendMessage(Prefix.database + "Reset Free Item redeeming for player "
									+ ChatColor.GREEN + targetPlayer.getName() + ChatColor.GRAY + ".");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.database + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GRAY
									+ " not found.");
							Function.no(player);
						}
					} else {
						player.sendMessage(Prefix.database + Prefix.type + "/resetfree [player]");
						Function.no(player);
					}
				} else {
					player.sendMessage(Prefix.permission);
					Function.no(player);
				}
			}
		}
		return true;

	}

	public static int itemsInInventory(Inventory inventory) {
		int i = 0;
		for (ItemStack is : inventory.getContents()) {
			if (is != null && is.getType() == Material.GOLD_BLOCK) {
				i += is.getAmount();
			}
		}
		return i;
	}

	public void openGUI(Player P) {

		Inventory a = Bukkit.createInventory(null, 54);

		ItemStack item_1 = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta itemm = item_1.getItemMeta();
		ArrayList<String> item_lore = new ArrayList<String>();
		item_lore.add("You have " + itemsInInventory(P.getInventory()));
		itemm.setLore(item_lore);

		P.openInventory(a);
	}

	public void onDisable() {
		AFK.AFKListName.clear();

		for (Player p : Bukkit.getOnlinePlayers()) {
			AFK.noLongerAFK(p);
		}

		Bukkit.broadcastMessage(
				Prefix.server + StockInt.pluginName + " System: " + ChatColor.RED + ChatColor.BOLD + "Disable");
		for (Player player1 : Bukkit.getOnlinePlayers())
			Function.pling(player1);

		if (StockInt.BarAPIHook == true)
			BarAPI_api.removeBarAll();

		getConfig().set("countdown", StockInt.CountdownLength);
		getConfig().set("countdown_startLength", StockInt.CountdownStartLength);
		getConfig().set("countdown_message", StockInt.CountdownMessage);
		getConfig().set("donate.get", StockInt.moneyDonated);
		getConfig().set("donate.target", StockInt.moneyTargeted);
		saveConfig();

	}

	public void onEnable() {

		ActionBarAPI.run();

		File warpFolder = new File(getDataFolder() + File.separator + "/WarpDatabase/");
		File privateWarpFolder = new File(getDataFolder() + File.separator + "/PrivateWarpDatabase/");
		File reportFolder = new File(getDataFolder() + File.separator + "/ReportDatabase/");
		try {
			if (!warpFolder.exists())
				warpFolder.mkdirs();
			if (!reportFolder.exists())
				reportFolder.mkdirs();
			if (!privateWarpFolder.exists())
				privateWarpFolder.mkdirs();

		} catch (SecurityException e) {
			return;
		}

		if (getServer().getPluginManager().isPluginEnabled("BarAPI") == true)
			StockInt.BarAPIHook = true;

		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, this);

		getConfig().options().copyDefaults(true);
		getConfig().set("warp", null);
		getConfig().set("event.warpstatus", false);
		getConfig().set("event.name", "none");
		getConfig().set("event.join", false);
		getConfig().set("event.queuelist", null);
		saveConfig();

		if (getConfig().getString("redeem.code") == null)
			getConfig().set("redeem.code", "none");

		StockInt.privateServerPondJa = getConfig().getBoolean("enable_private_server_model_for_developer_server_only");
		StockInt.spawnOnJoin = getConfig().getBoolean("spawn_on_join");
		StockInt.LoginFeature = getConfig().getBoolean("login_feature");
		StockInt.moneyDonated = getConfig().getLong("donate.get");
		StockInt.moneyTargeted = getConfig().getLong("donate.target");
		StockInt.CountdownStartLength = getConfig().getLong("countdown_startLength");
		StockInt.CountdownLength = getConfig().getLong("countdown");
		if (getConfig().getString("countdown_message") != null)
			StockInt.CountdownMessage = getConfig().getString("countdown_message").replaceAll("&", Prefix.Ampersand);

		for (Player player1 : Bukkit.getOnlinePlayers()) {
			Function.orb(player1);
			Rank.setDisplay(player1);
		}

		regEvents();
		saveConfig();

		BukkitScheduler s = getServer().getScheduler();
		s.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				Countdown.run();
				isStandOnPlate();

				if (StockInt.privateServerPondJa) {
					if (Bukkit.getWorld("world").getTime() < 20) { // First second of the day
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1));
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0));
							p.sendMessage(ChatColor.YELLOW + "Fresh Morning~! " + ChatColor.LIGHT_PURPLE
									+ "+Regeneration I Effect for 30 seconds.");
							p.sendMessage(ChatColor.YELLOW + "                     " + ChatColor.LIGHT_PURPLE
									+ "+Resistant II Effect for 60 seconds.");
						}
					}
				}
				AFK.afkLoop();
			}
		}, 0L, 20L);

		s.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				Login.loginTask();

				for (Player p : Bukkit.getOnlinePlayers()) {
					String afk = "";
					if (AFK.AFKListName.contains(p.getName()) && !AFK.calculateAFK(p).isEmpty())
						afk = "[AFK - " + AFK.calculateAFK(p) + "]";
					p.setPlayerListName(afk + p.getDisplayName() + ChatColor.WHITE + " [" + Ping.pingWithColor(p)
							+ ChatColor.WHITE + "]");
				}
			}
		}, 0L, 100L);
		/*
		 * s.scheduleSyncRepeatingTask(this, new Runnable() {
		 * 
		 * @Override public void run() { AutoSaveWorld.save(); } }, 0L, 6000L);
		 */
		String version = getDescription().getVersion();

		Bukkit.broadcastMessage(
				Prefix.server + StockInt.pluginName + " System: " + ChatColor.GREEN + ChatColor.BOLD + "Enable");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(StockInt.pluginName + "'s patch version: " + ChatColor.GREEN + version);
		Bukkit.broadcastMessage("  -" + ChatColor.GOLD + " ActionBarAPI " + ChatColor.WHITE + "library version: "
				+ ChatColor.AQUA + "1.5.4 " + ChatColor.WHITE + "by " + ChatColor.YELLOW + "ConnorLinfoot");
		Bukkit.broadcastMessage("Developer: " + ChatColor.GOLD + ChatColor.BOLD + "PondJaTH");
		Bukkit.broadcastMessage("");

		for (Player P : Bukkit.getOnlinePlayers())
			if (!AFK.AFKListName.contains(P.getName()))
				AFK.AFKCount.put(P.getName(), (long) 0);

		VersionJa.main();

		if (StockInt.ServerVersion == 0) {
			System.out.println("\n\n================[" + StockInt.pluginName
					+ "]=======================\nThis server isn't match with minimum requirement\nRequire: Minecraft Server 1.8 and newer.\n================================================\n\n");
			pm.disablePlugin(this);
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Action act;
		act = e.getAction();
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Blockto113.SOIL.bukkitblock())
			e.setCancelled(true);
		if ((act == Action.RIGHT_CLICK_BLOCK) == false) {
			return;
		}
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		String playerName = player.getName();
		File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		long money = playerData.getLong("money");
		if (block.getType() == Blockto113.WALL_SIGN.bukkitblock()
				|| block.getType() == Blockto113.SIGN_POST.bukkitblock()) {
			Sign s = (Sign) block.getState();
			if ((s.getLine(0).contains("[sell]") || s.getLine(0).equalsIgnoreCase("[buy]")) && isNumeric(s.getLine(3))
					&& isNumeric(s.getLine(1)) && !isNumeric(s.getLine(2))) {

				// Format
				// [sell] / [buy]
				// amount
				// material (in name)
				// price

				String s0 = s.getLine(0).toLowerCase();
				int s1_amount = Integer.parseInt(ChatColor.stripColor(s.getLine(1)));
				String s2_item = s.getLine(2).toLowerCase();
				short s2_item_data = 0;
				long s3_price = Integer.parseInt(s.getLine(3));

				if (s2_item.contains("x") || s2_item.contains("*")) {
					// printf("Yes it has");
				}

				if (!s2_item.contains(":"))
					s2_item = s.getLine(2);
				else {
					String[] line2 = s.getLine(2).split(":");
					s2_item = line2[0];
					s2_item_data = (short) Integer.parseInt(line2[1]);
				}

				if (!s0.isEmpty() && !s.getLine(1).isEmpty() && !s.getLine(2).isEmpty() && !s.getLine(3).isEmpty())
					if (ChatColor.stripColor(s.getLine(0)).contains("[sell]"))
						Shop.sell(player, s2_item, s1_amount, s3_price, s2_item_data);
					else if (ChatColor.stripColor(s.getLine(0)).contains("[buy]"))
						Shop.buy(player, s2_item, s1_amount, s3_price, s2_item_data);
			}

			int lcq = playerData.getInt("Quota.LuckyClick");
			if (s.getLine(0).equalsIgnoreCase("[luckyclick]")) {
				if (lcq < 1) {
					player.sendMessage(Prefix.server + "You don't have enough quota!");
					Function.no(player);
					player.sendMessage(Prefix.server + "Use " + ChatColor.AQUA + "/buyquota LuckyClick" + ChatColor.GRAY
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
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.DIAMOND, r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " DIAMOND");
					}
					if (r == 1) {
						int r1 = new Random().nextInt(16);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.IRON_INGOT, r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " IRON_INGOT");
					}
					if (r == 2) {
						int r1 = new Random().nextInt(501);
						String coin = " Coins";
						if (r1 == 0 || r1 == 1) {
							r1 = 1;
							coin = " Coin";
						}
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + coin + ChatColor.GRAY + ".");

						try {
							playerData.set("money", money + r1);
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (r == 3) {
						int r1 = new Random().nextInt(21);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Blockto113.EXP_BOTTLE.bukkitblock(), r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " EXP_BOTTLE");
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
						String coin = " Coins";
						if (r1 == 0 || r1 == 1) {
							r1 = 1;
							coin = " Coin";
						}

						try {
							playerData.set("money", money - r1);
							playerData.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + "-" + r1 + coin + ChatColor.GRAY + ".");
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
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.DIRT, r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You got "
								+ ChatColor.RED + r1 + " Dirt");
					}
					if (r == 9) {
						player.sendMessage(Prefix.lc + ChatColor.RED + "BAD LUCK! " + ChatColor.WHITE + "You don't get "
								+ ChatColor.RED + "ANYTHING");
					}
					if (r == 10) {
						int r1 = new Random().nextInt(16);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.IRON_INGOT, r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " IRON_INGOT");

					}
					if (r == 11) {
						int r1 = new Random().nextInt(4);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.EMERALD, r1);
						player.getInventory().addItem(item);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " EMERALD");
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
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.REDSTONE, r1);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + r1 + " REDSTONE");
						player.getInventory().addItem(item);
					}
					if (r == 17) {
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! " + ChatColor.WHITE + "You got "
								+ ChatColor.YELLOW + "AIR " + ChatColor.GRAY + ChatColor.ITALIC + "(Seriously?)");
					}
					if (r == 18) {
						int r1 = new Random().nextInt(21);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.COAL, r1);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
								+ "You got " + ChatColor.YELLOW + r1 + " COAL");
						player.getInventory().addItem(item);
					}
					if (r == 19) {
						int r1 = new Random().nextInt(31);
						if (r1 == 0)
							r1++;
						ItemStack item = new ItemStack(Material.COBBLESTONE, r1);
						player.sendMessage(Prefix.lc + ChatColor.GREEN + "Good Luck! (or not) " + ChatColor.WHITE
								+ "You got " + ChatColor.YELLOW + r1 + " COBBLESTONE");
						player.getInventory().addItem(item);
					}
					int lcq2 = playerData.getInt("Quota.LuckyClick");
					Function.egg(player);
					player.sendMessage(
							Prefix.server + "You have " + ChatColor.LIGHT_PURPLE + lcq2 + " Lucky Click Quota left!");
				}
			}
			if (s.getLine(0).equalsIgnoreCase("[freequota]")) {

			}
			if (s.getLine(0).equalsIgnoreCase("[buyquota]")) {
				int tprq = playerData.getInt("Quota.TPR");
				int homeq = playerData.getInt("Quota.Home");
				int luckyq = playerData.getInt("Quota.LuckyClick");
				if (s.getLine(2).equalsIgnoreCase("home")) {
					if (isNumeric(s.getLine(1))) {
						if (money > (Integer.parseInt(s.getLine(1)) * 5000)) {
							try {
								playerData.set("Quota.Home", homeq + Integer.parseInt(s.getLine(1)));
								playerData.set("money", money - (Integer.parseInt(s.getLine(1)) * 5000));
								playerData.save(f);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							player.sendMessage(Prefix.server + "You " + ChatColor.YELLOW + "paid "
									+ (Integer.parseInt(s.getLine(1)) * 5000) + " Coins" + ChatColor.GRAY
									+ " to bought " + ChatColor.GREEN + s.getLine(1) + "x Home Quota");
							Function.yes(player);
						} else {
							player.sendMessage(Prefix.server + Prefix.nom);
							Function.no(player);
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
						player.sendMessage(Prefix.server + "You " + ChatColor.YELLOW + "paid "
								+ (Integer.parseInt(s.getLine(1)) * 300) + " Coins" + ChatColor.GRAY + " to bought "
								+ ChatColor.GREEN + s.getLine(1) + "x TPR Quota");
						Function.yes(player);
					} else {
						player.sendMessage(Prefix.server + Prefix.nom);
						Function.no(player);
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
						player.sendMessage(Prefix.server + "You " + ChatColor.YELLOW + "paid "
								+ (Integer.parseInt(s.getLine(1)) * 500) + " Coins" + ChatColor.GRAY + " to bought "
								+ ChatColor.GREEN + s.getLine(1) + "x LuckyClick Quota");
						Function.yes(player);
					} else {
						player.sendMessage(Prefix.server + Prefix.nom);
						Function.no(player);
					}
				}
			}

		} else {
			return;
		}
	}

	@SuppressWarnings("deprecation")
	public void openDataGUI(Player p, String a) {
		String name = "";
		if (Bukkit.getServer().getPlayer(a) != null) {
			name = Bukkit.getServer().getPlayer(a).getName();
		} else {
			name = p.getName();
		}
		File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + name);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		Inventory inv;
		inv = Bukkit.createInventory(null, 18, name + "'s data");
		ItemStack f1 = new ItemStack(Blockto113.SKULL_ITEM.bukkitblock(), 1, (short) 3);
		SkullMeta fs = (SkullMeta) f1.getItemMeta();
		fs.setOwner(name);
		fs.setDisplayName(ChatColor.WHITE + "Name: " + ChatColor.AQUA + ChatColor.BOLD + name);
		f1.setItemMeta(fs);
		inv.setItem(0, f1);
		String rank = playerData.getString("rank");
		if (rank.equalsIgnoreCase("default")) {
			ItemStack f2 = new ItemStack(Blockto113.SAPLING.bukkitblock(), 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.GRAY + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("vip")) {
			ItemStack f2 = new ItemStack(Blockto113.YELLOW_FLOWER.bukkitblock(), 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.GREEN + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("staff")) {
			ItemStack f2 = new ItemStack(Material.BOOK, 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("builder")) {
			ItemStack f2 = new ItemStack(Blockto113.WORKBENCH.bukkitblock(), 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.GREEN + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("helper")) {
			ItemStack f2 = new ItemStack(Material.PAPER, 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("admin")) {
			ItemStack f2 = new ItemStack(Material.IRON_SWORD, 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.RED + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else if (rank.equalsIgnoreCase("owner")) {
			ItemStack f2 = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.GOLD + ChatColor.BOLD + rank);
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		} else {
			ItemStack f2 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta f2m = f2.getItemMeta();
			f2m.setDisplayName(ChatColor.WHITE + "Rank: " + ChatColor.GRAY + ChatColor.BOLD + "???");
			f2.setItemMeta(f2m);
			inv.setItem(2, f2);
		}

		Boolean muteis = playerData.getBoolean("mute.is");
		String mutere = playerData.getString("mute.reason");
		Boolean freeze = playerData.getBoolean("freeze");
		int countwarn = playerData.getInt("warn");
		int tprq = playerData.getInt("Quota.TPR");
		int lcq = playerData.getInt("Quota.LuckyClick");
		int homeq = playerData.getInt("Quota.Home");
		if (muteis == true) {
			ItemStack f3 = new ItemStack(Material.PAINTING, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Mute: " + ChatColor.RED + "Yes. " + ChatColor.RED + ChatColor.ITALIC
					+ "Reason: " + mutere);
			f3.setItemMeta(f3m);
			inv.setItem(4, f3);
		} else {
			ItemStack f3 = new ItemStack(Material.LEGACY_SIGN, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Mute: " + ChatColor.GREEN + "No");
			f3.setItemMeta(f3m);
			inv.setItem(4, f3);
		}

		if (countwarn == 1) {
			ItemStack f3 = new ItemStack(Material.STONE, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Warn: " + ChatColor.YELLOW + "1 time, Don't break rules!");
			f3.setItemMeta(f3m);
			inv.setItem(6, f3);
		} else if (countwarn == 2) {
			ItemStack f3 = new ItemStack(Material.COBBLESTONE, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Warn: " + ChatColor.GOLD + "2 times, WTF are you doing?");
			f3.setItemMeta(f3m);
			inv.setItem(6, f3);
		} else if (countwarn == 3) {
			ItemStack f3 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Warn: " + ChatColor.RED + "3 times, You will be banned.");
			f3.setItemMeta(f3m);
			inv.setItem(6, f3);
		} else {
			ItemStack f3 = new ItemStack(Material.GLASS, 1);
			ItemMeta f3m = f3.getItemMeta();
			f3m.setDisplayName(ChatColor.WHITE + "Warn: " + ChatColor.AQUA + "No-Warn :)" + ChatColor.WHITE + " [0]");
			f3.setItemMeta(f3m);
			inv.setItem(6, f3);
		}

		if (freeze == true) {
			ItemStack f4 = new ItemStack(Material.ICE, 1);
			ItemMeta f4m = f4.getItemMeta();
			f4m.setDisplayName(ChatColor.WHITE + "Freeze: " + ChatColor.RED + ChatColor.BOLD + "Yes");
			f4.setItemMeta(f4m);
			inv.setItem(8, f4);
		}
		if (freeze == false) {
			ItemStack f4 = new ItemStack(Material.ICE, 1);
			ItemMeta f4m = f4.getItemMeta();
			f4m.setDisplayName(ChatColor.WHITE + "Freeze: " + ChatColor.GREEN + ChatColor.BOLD + "No");
			f4.setItemMeta(f4m);
			inv.setItem(8, f4);
		}
		long money = playerData.getLong("money");
		ItemStack f5 = new ItemStack(Material.EMERALD, 1);
		ItemMeta f5m = f5.getItemMeta();
		String coin = " Coins";
		if (money <= 1) {
			coin = " Coin";
		}
		f5m.setDisplayName(ChatColor.WHITE + "Money: " + ChatColor.YELLOW + ChatColor.BOLD + money + coin);
		f5.setItemMeta(f5m);
		inv.setItem(10, f5);

		ItemStack f6 = new ItemStack(Material.ENDER_PEARL, 1);
		ItemMeta f6m = f6.getItemMeta();
		String tprqq = " quotas";
		if (tprq <= 1) {
			tprqq = " quota";
		}
		f6m.setDisplayName(ChatColor.WHITE + "TP Quota: " + ChatColor.GREEN + ChatColor.BOLD + tprq + tprqq);
		f6.setItemMeta(f6m);
		inv.setItem(12, f6);

		ItemStack f7 = new ItemStack(Blockto113.BED.bukkitblock(), 1);
		ItemMeta f7m = f7.getItemMeta();
		String place = " places";
		if (homeq <= 1) {
			place = " place";
		}
		f7m.setDisplayName(ChatColor.WHITE + "Maximum Sethome: " + ChatColor.GREEN + ChatColor.BOLD + homeq + place);
		f7.setItemMeta(f7m);
		inv.setItem(14, f7);

		ItemStack f8 = new ItemStack(Material.CHEST, 1);
		ItemMeta f8m = f8.getItemMeta();
		String lcqq = " quotas";
		if (lcq <= 1) {
			lcqq = " quota";
		}
		f8m.setDisplayName(ChatColor.WHITE + "LuckyClick Quota: " + ChatColor.GREEN + ChatColor.BOLD + lcq + lcqq);
		f8.setItemMeta(f8m);
		inv.setItem(16, f8);

		p.openInventory(inv);
	}

	public void playCircularEffect(Location location, Effect effect, boolean v) {
		for (int i = 0; i <= 8; i += ((!v && (i == 3)) ? 2 : 1))
			location.getWorld().playEffect(location, effect, i);
	}

	@EventHandler
	public void PlayerChangeSign(SignChangeEvent event) {
		Player player = event.getPlayer();
		String l0 = event.getLine(0).toLowerCase();
		String line0 = event.getLine(0);
		if (l0.endsWith("[tp]") || l0.endsWith("[sell]") || l0.endsWith("[buy]") || l0.endsWith("[luckyclick]")
				|| l0.endsWith("[cmd]") || l0.endsWith("[buyquota]")) {
			if (!player.isOp() && !player.hasPermission("main.sign")) {
				event.setLine(0, "Sorry, but you");
				event.setLine(1, "need permission");
				event.setLine(2, "or op for sign");
				event.setLine(3, "'" + line0 + "'");
				player.sendMessage(Prefix.permission);
				Bukkit.broadcastMessage(Prefix.server + "Player " + ChatColor.YELLOW + player.getName() + ChatColor.GRAY
						+ " try to create sign " + ChatColor.RED + ChatColor.BOLD + line0);
			}
		}
	}

	@EventHandler
	public void PlayerUsePlate(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		int w = playerData.getInt("WarpState");
		Location loc = player.getLocation();
		Location loc2 = player.getLocation();
		Location loc3 = player.getLocation();
		loc.setY(loc.getY());
		loc2.setY(loc.getY() - 2);
		loc3.setY(loc.getY() - 3);
		Block block = loc.getBlock();
		Block block2 = loc2.getBlock();
		Block block3 = loc3.getBlock();
		if (event.isSneaking() == true) {
			if ((block.getType() == Blockto113.GOLD_PLATE.bukkitblock()
					|| block.getType() == Blockto113.IRON_PLATE.bukkitblock())
					&& (block2.getType() == Blockto113.SIGN_POST.bukkitblock()
							|| block2.getType() == Blockto113.WALL_SIGN.bukkitblock())) {
				Sign s1 = (Sign) block2.getState();
				if (s1.getLine(0).equalsIgnoreCase("[tp]")) {
					if (w == 0) {
						try {
							playerData.set("WarpState", 1);
							playerData.save(f);
						} catch (IOException e) {
							Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
						}
						teleportPlate(player);
					}
				} else if (s1.getLine(0).equalsIgnoreCase("[cmd]")) {
					if (!s1.getLine(1).isEmpty()) {
						String l1 = s1.getLine(1);
						String l2 = s1.getLine(2);
						String l3 = s1.getLine(3);
						if (l1.startsWith("*")) {
							getServer().dispatchCommand(getServer().getConsoleSender(),
									l1.replaceAll("*", "") + l2 + l3);
						} else {
							player.performCommand(l1.replaceAll("$", playerName) + l2.replaceAll("$", playerName)
									+ l3.replaceAll("$", playerName));
						}
						if (block3.getType() == Blockto113.SIGN_POST.bukkitblock()
								|| block3.getType() == Blockto113.WALL_SIGN.bukkitblock()) {
							Sign s2 = (Sign) block3.getState();
							if (s2 != null) {
								if (s2.getLine(0).equalsIgnoreCase("[pay]")) {
									long targetPlayerMoney = playerData.getLong("money");
									if (isNumeric(s2.getLine(1)) && Integer.parseInt(s2.getLine(1)) > 0) {
										long n = (long) (targetPlayerMoney - Long.parseLong(s2.getLine(1)));
										if (n < 0) {
											n = 0;
										}
										try {
											playerData.set("money", n);
											playerData.save(f);
										} catch (IOException e) {
											e.printStackTrace();
										}
										Money.take(player, Long.parseLong(s2.getLine(1)));
										Function.yes(player);
									}
								}
							}
						}
					}
				} else {
					ActionBarAPI.send(player, "This plate isn't " + ChatColor.RED + "ready");
				}
			}
		}
	}

	public void removeList(String key, String... element) {
		List<String> list = getConfig().getStringList(key);
		list.removeAll(Arrays.asList(element));
		getConfig().set(key, list);
		saveConfig();
	}

	public void isStandOnPlate() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String playerName = p.getName();
			File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
			File f = new File(userdata, File.separator + "config.yml");
			FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
			Location loc = p.getLocation();
			Location loc2 = p.getLocation();
			loc2.setY(loc.getY() - 2);
			int w = playerData.getInt("WarpState");
			Block block = loc.getBlock();
			Block block2 = loc2.getBlock();
			if ((block.getType() == Blockto113.GOLD_PLATE.bukkitblock()
					|| block.getType() == Blockto113.IRON_PLATE.bukkitblock())
					&& ((block2.getType() == Blockto113.SIGN_POST.bukkitblock())
							|| (block2.getType() == Blockto113.WALL_SIGN.bukkitblock()))) {
				Sign sign = (Sign) block2.getState();
				if (sign.getLine(0).equalsIgnoreCase("[tp]")) {
					if (w == 0) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 10));
						ActionBarAPI.send(p,
								ChatColor.RESET + ">>>>>>>>" + ChatColor.YELLOW + "" + ChatColor.BOLD + " Press "
										+ ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "Shift"
										+ ChatColor.AQUA + " to teleport " + ChatColor.RESET + "<<<<<<<<");
					}
				}
				if (sign.getLine(0).equalsIgnoreCase("[cmd]")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 10));
					ActionBarAPI.send(p,
							ChatColor.RESET + ">>>>>>>>" + ChatColor.YELLOW + "" + ChatColor.BOLD + " Press "
									+ ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "Shift" + ChatColor.AQUA
									+ " to perform command " + ChatColor.RESET + "<<<<<<<<");
				}

			}
		}
	}

	@SuppressWarnings("deprecation")
	public void plateParticle(Player player) {
		player.playEffect(player.getLocation(), Effect18to113.FIREWORKS_SPARK.bukkiteffect(), 10);
		player.playEffect(player.getLocation(), Effect18to113.FIREWORKS_SPARK.bukkiteffect(), 10);
		player.playEffect(player.getLocation(), Effect18to113.FIREWORKS_SPARK.bukkiteffect(), 10);
		player.playEffect(player.getLocation(), Effect18to113.FIREWORKS_SPARK.bukkiteffect(), 10);
		player.playEffect(player.getLocation(), Effect18to113.FIREWORKS_SPARK.bukkiteffect(), 10);
	}

	public void teleportPlate(Player p) {
		String playerName = p.getName();
		File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		int w = playerData.getInt("WarpState");
		Location loc = p.getLocation();
		Location loc_plate = p.getLocation();
		Location loc_sign1 = p.getLocation();
		Location loc_sign2 = p.getLocation();
		loc_plate.setY(loc.getY());
		loc_sign1.setY(loc.getY() - 2);
		loc_sign2.setY(loc.getY() - 3);
		Block block_plate = loc_plate.getBlock();
		Block block_sign = loc_sign1.getBlock();
		Block block_sign2 = loc_sign2.getBlock();
		if ((block_plate.getType() == Blockto113.GOLD_PLATE.bukkitblock()
				|| block_plate.getType() == Blockto113.IRON_PLATE.bukkitblock())
				&& (block_sign.getType() == Blockto113.SIGN_POST.bukkitblock()
						|| block_sign.getType() == Blockto113.WALL_SIGN.bukkitblock())) {
			Sign sign = (Sign) block_sign.getState();
			if (sign.getLine(0).equalsIgnoreCase("[tp]")) {
				plateParticle(p);
				if (w == 1) {
					ActionBarAPI.send(p, ChatColor.YELLOW + ">>>>>>>>" + Prefix.tc + ChatColor.YELLOW + "<<<<<<<<");
					Function.egg(p, (float) 0.3);
				} else if (w == 2) {
					ActionBarAPI.send(p, ChatColor.YELLOW + ">>>>>>" + ChatColor.GOLD + ">>" + Prefix.tc
							+ ChatColor.GOLD + "<<" + ChatColor.YELLOW + "<<<<<<");
					Function.egg(p, (float) 0.5);
				} else if (w == 3) {
					ActionBarAPI.send(p, ChatColor.YELLOW + ">>>>" + ChatColor.GOLD + ">>>>" + Prefix.tc
							+ ChatColor.GOLD + "<<<<" + ChatColor.YELLOW + "<<<<");
					Function.egg(p, (float) 0.7);
				} else if (w == 4) {
					ActionBarAPI.send(p, ChatColor.YELLOW + ">>" + ChatColor.GOLD + ">>>>>>" + Prefix.tc
							+ ChatColor.GOLD + "<<<<<<" + ChatColor.YELLOW + "<<");
					Function.egg(p, (float) 0.9);
				} else if (w == 5) {
					ActionBarAPI.send(p, ChatColor.GOLD + ">>>>>>>>" + Prefix.tc + ChatColor.GOLD + "<<<<<<<<");
					Function.egg(p, (float) 1.2);
				} else if (w == 7) {
					Sign location_sign = (Sign) block_sign.getState();
					World world = p.getWorld();
					Location pl = p.getLocation();
					if (block_sign2.getType() == Blockto113.SIGN_POST.bukkitblock()
							|| block_sign2.getType() == Blockto113.WALL_SIGN.bukkitblock()) {
						Sign world_sign = (Sign) block_sign2.getState();
						if (world_sign.getLine(0).equalsIgnoreCase("[world]")) {
							World world_check = Bukkit
									.getWorld(world_sign.getLine(1) + world_sign.getLine(2) + world_sign.getLine(3));
							if (world_check != null) {
								world = world_check;
							}
						}
					}
					double xh = Double.parseDouble(location_sign.getLine(1));
					double yh = Double.parseDouble(location_sign.getLine(2));
					double zh = Double.parseDouble(location_sign.getLine(3));
					double x = xh + 0.5;
					double y = yh;
					double z = zh + 0.5;
					double yaw = pl.getYaw();
					double pitch = pl.getPitch();
					Location loca = new Location(world, x, y, z);
					loca.setPitch((float) pitch);
					loca.setYaw((float) yaw);
					p.teleport(loca);
					ActionBarAPI.send(p, ChatColor.GREEN + ">>>>>>>>" + ChatColor.BOLD + " Teleport! " + ChatColor.RESET
							+ ChatColor.GREEN + "<<<<<<<<");
					Function.yes(p);
				} else {
					// NOTHING
				}
				w += 1;
				if (w <= 7) {
					try {
						playerData.set("WarpState", w);
						playerData.save(f);
					} catch (IOException e) {
						Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
					}
					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							teleportPlate(p);
						}
					}, 10);
				} else {
					try {
						playerData.set("WarpState", 0);
						playerData.save(f);
					} catch (IOException e) {
						Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
					}
				}
			}
		} else {
			ActionBarAPI.send(p, ChatColor.RED + ">>>>>>>>" + Prefix.tcc + ChatColor.RED + "<<<<<<<<");
			Function.no(p);
			try {
				playerData.set("WarpState", 0);
				playerData.save(f);
			} catch (IOException e) {
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		}
	}
}