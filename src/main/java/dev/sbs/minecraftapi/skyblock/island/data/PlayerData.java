package dev.sbs.minecraftapi.skyblock.island.data;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.minecraftapi.skyblock.date.SkyBlockDate;
import dev.sbs.minecraftapi.skyblock.island.Potion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@Getter
public class PlayerData {

    @SerializedName("death_count")
    private int deathCount;
    @SerializedName("last_death")
    private SkyBlockDate.SkyBlockTime lastDeath;
    @SerializedName("fishing_treasure_caught")
    private int fishingTreasureCaught;
    @SerializedName("experience")
    private @NotNull ConcurrentMap<String, Double> skillExperience = Concurrent.newMap();

    // Unlockables
    @SerializedName("reaper_peppers_eaten")
    private int reaperPeppersEaten;
    @SerializedName("perks")
    private @NotNull ConcurrentMap<String, Integer> essencePerks = Concurrent.newMap();
    @SerializedName("unlocked_coll_tiers")
    private @NotNull ConcurrentList<String> unlockedCollectionTiers = Concurrent.newList();
    @SerializedName("crafted_generators")
    private @NotNull ConcurrentList<String> craftedMinions = Concurrent.newList();

    // Visited Locations
    @SerializedName("visited_modes")
    private @NotNull ConcurrentList<String> visitedModes = Concurrent.newList();
    @SerializedName("visited_zones")
    private @NotNull ConcurrentList<String> visitedZones = Concurrent.newList();
    @SerializedName("achievement_spawned_island_types")
    private @NotNull ConcurrentList<String> spawnedIslandTypes = Concurrent.newList();

    // Potions & Buffs
    @SerializedName("active_effects")
    private @NotNull ConcurrentList<Potion> activePotions = Concurrent.newList();
    @SerializedName("paused_effects")
    private @NotNull ConcurrentList<Potion> pausedPotions = Concurrent.newList();
    @SerializedName("disabled_potion_effects")
    private @NotNull ConcurrentList<String> disabledPotions = Concurrent.newList();
    @SerializedName("temp_stat_buffs")
    private @NotNull ConcurrentList<CenturyCake> centuryCakes = Concurrent.newList();

    public @NotNull ConcurrentList<Integer> getCraftedMinions(@NotNull String itemId) {
        return this.getCraftedMinions()
            .stream()
            .filter(item -> item.matches(String.format("^%s_[\\d]+$", itemId)))
            .map(item -> Integer.parseInt(item.replace(String.format("%s_", itemId), "")))
            .collect(Concurrent.toList())
            .sorted(Comparator.naturalOrder());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CenturyCake {

        private int stat; // This is in ordinal order in stat menu
        private String key;
        private int amount;
        @SerializedName("expire_at")
        private SkyBlockDate.RealTime expiresAt;

    }

}
