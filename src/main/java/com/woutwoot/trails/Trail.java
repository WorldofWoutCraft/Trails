package com.woutwoot.trails;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 9/4/2014
 *
 * @author minecreatr
 */
public class Trail {

    public static final Trail star = rt(new Trail(ParticleEffect.ParticleType.CRIT_MAGIC, "star"));
    public static final Trail spark = rt(new Trail(ParticleEffect.ParticleType.FIREWORKS_SPARK, "spark"));
    public static final Trail music = rt(new Trail(ParticleEffect.ParticleType.NOTE, "music"));
    public static final Trail happy = rt(new Trail(ParticleEffect.ParticleType.VILLAGER_HAPPY, "happy"));
    public static final Trail lava = rt(new Trail(ParticleEffect.ParticleType.DRIP_LAVA, "lava"));
    public static final Trail ash = rt(new Trail(ParticleEffect.ParticleType.LAVA, "ash"));
    public static final Trail water = rt(new Trail(ParticleEffect.ParticleType.DRIP_WATER, "water"));
    public static final Trail magic = rt(new Trail(ParticleEffect.ParticleType.SPELL_WITCH, "magic"));
    public static final Trail love = rt(new Trail(ParticleEffect.ParticleType.HEART, "love"));
    public static final Trail dust = rt(new Trail(ParticleEffect.ParticleType.REDSTONE, "dust"));
    public static final Trail slime = rt(new Trail(ParticleEffect.ParticleType.SLIME, "slime"));
    public static final Trail ender = rt(new Trail(ParticleEffect.ParticleType.PORTAL, "ender"));
    public static final Trail knowledge = rt(new Trail(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, "knowledge"));
    public static final Trail step = rt(new Trail(ParticleEffect.ParticleType.FOOTSTEP, "step"));
    public static final Trail voidTrail = rt(new Trail(ParticleEffect.ParticleType.TOWN_AURA, "void"));
    public static final Trail potion = rt(new Trail(ParticleEffect.ParticleType.SPELL_MOB, "potion"));
    public static final Trail pop = rt(new Trail(ParticleEffect.ParticleType.EXPLOSION_NORMAL, "pop"));
    public static final Trail splash = rt(new Trail(ParticleEffect.ParticleType.WATER_SPLASH, "splash"));
    public static final Trail smoke = rt(new Trail(ParticleEffect.ParticleType.SMOKE_LARGE, "smoke"));
    public static final Trail snow = rt(new Trail(ParticleEffect.ParticleType.SNOWBALL, "snow"));
    public static final Trail flame = rt(new Trail(ParticleEffect.ParticleType.FLAME, "flame"));
    public static final Trail angry = rt(new Trail(ParticleEffect.ParticleType.VILLAGER_ANGRY, "angry"));
    public static final Trail crack = rt(new Trail(ParticleEffect.ParticleType.BLOCK_CRACK, "crack"));


    //public static final Trail guardian = rt(new Trail(ParticleEffects.GUARDIAN, "guardian"));
    private static ArrayList<Trail> toRegister;
    private ParticleEffect.ParticleType effect;
    private String name;

    public Trail(ParticleEffect.ParticleType e, String n) {
        this.effect = e;
        name = n;
    }

    public static Trail rt(Trail trail) {
        if (toRegister == null) {
            toRegister = new ArrayList<Trail>();
        }
        toRegister.add(trail);
        return trail;
    }

    public static void registerTrails(HashMap<String, Trail> register) {
        for (int i = 0; i < toRegister.size(); i++) {
            register.put(toRegister.get(i).getName(), toRegister.get(i));
        }
    }

    public ParticleEffect.ParticleType getEffect() {
        return this.effect;
    }

    public String getName() {
        return this.name;
    }
}
