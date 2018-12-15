package me.palapon2545.SMDMain.Function;

import org.bukkit.Effect;

public enum Effect18to113 {
    FIREWORK_SPARK("FIREWORK_SPARK", "FIREWORK_SHOOT");

    private String pre19effect;
    private String to113effect;
    private Effect resolvedeffect = null;

    Effect18to113(String pre19effect, String to113effect) {
        this.pre19effect = pre19effect;
        this.to113effect = to113effect;
    }

    public Effect bukkiteffect() {
        if (resolvedeffect != null) return resolvedeffect;
        
        try {
            return resolvedeffect = Effect.valueOf(to113effect);
        } catch (IllegalArgumentException e) {
        	return resolvedeffect = Effect.valueOf(pre19effect);
        }
    }
}
