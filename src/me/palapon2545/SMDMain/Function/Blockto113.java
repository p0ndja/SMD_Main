package me.palapon2545.SMDMain.Function;

import org.bukkit.Material;

public enum Blockto113 {
	EXP_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
    SIGN_POST("SIGN_POST","SIGN"),
    SOIL("SOIL","FARMLAND"),
    SAPLING("SAPLING", "OAK_SAPLING"),
    YELLOW_FLOWER("YELLOW_FLOWER", "DANDELION"),
    WORKBENCH("WORKBENCH","CRAFTING_TABLE"),
	SKULL_ITEM("SKULL_ITEM", "LEGACY_SKULL_ITEM"),
	WOOL("WOOL", "WHITE_WOOL"),
	GOLD_PLATE("GOLD_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE"),
	IRON_PLATE("IRON_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE"),
	BED("BED", "RED_BED"),
	DOUBLE_PLANT("DOUBLE_PLANT","SUNFLOWER"),
	WOOD_SWORD("WOOD_SWORD", "WOODEN_SWORD"),
	WOOD_AXE("WOOD_AXE", "WOODEN_AXE"),
	WOOD_HOE("WOOD_HOE", "WOODEN_HOE"),
	WOOD_PICKAXE("WOOD_PICKAXE", "WOODEN_PICKAXE"),
	WOOD_SPADE("WOOD_SPADE", "WOODEN_SHOVEL"),
	LAPIS_LAZURI_EXCEPTION("INK_SAC", "LAPIS_LAZULI"),
	STAINED_GLASS_PANE("STAINED_GLASS_PANE", "WHITE_STAINED_GLASS_PANE");

    private String pre113block;
    private String post113block;
    private Material resolvedblock = null;

    Blockto113(String pre113block, String post113block) {
        this.pre113block = pre113block;
        this.post113block = post113block;
    }

    public Material bukkitblock() {
        if (resolvedblock != null) return resolvedblock;
        
        try {
        	//Try 1.13 item
            return resolvedblock = Material.valueOf(post113block);
        } catch (IllegalArgumentException e) {
            //Try 1.8 item
            return resolvedblock = Material.valueOf(pre113block);
        }
    }
}
