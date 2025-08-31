package dev.sbs.minecraftapi.skyblock.island.data.dungeon;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.minecraftapi.skyblock.date.SkyBlockDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class FloorData {

    protected double experience;
    @SerializedName("highest_tier_completed")
    private int highestCompletedTier;
    @SerializedName("best_runs")
    private @NotNull ConcurrentMap<Integer, ConcurrentList<BestRun>> bestRuns = Concurrent.newMap();

    @SerializedName("times_played")
    private @NotNull ConcurrentMap<Integer, Integer> timesPlayed = Concurrent.newMap();
    @SerializedName("tier_completions")
    private @NotNull ConcurrentMap<Integer, Integer> completions = Concurrent.newMap();
    @SerializedName("milestone_completions")
    private @NotNull ConcurrentMap<Integer, Integer> milestoneCompletions = Concurrent.newMap();

    @SerializedName("best_score")
    private @NotNull ConcurrentMap<Integer, Integer> bestScore = Concurrent.newMap();
    @SerializedName("watcher_kills")
    private @NotNull ConcurrentMap<Integer, Integer> watcherKills = Concurrent.newMap();
    @SerializedName("mobs_killed")
    private @NotNull ConcurrentMap<Integer, Integer> mobsKilled = Concurrent.newMap();
    @SerializedName("most_mobs_killed")
    private @NotNull ConcurrentMap<Integer, Integer> mostMobsKilled = Concurrent.newMap();
    @SerializedName("most_healing")
    private @NotNull ConcurrentMap<Integer, Double> mostHealing = Concurrent.newMap();

    // Class Damage
    @Getter(AccessLevel.NONE)
    @SerializedName("most_damage_healer")
    private @NotNull ConcurrentMap<Integer, Double> mostDamageHealer = Concurrent.newMap();
    @Getter(AccessLevel.NONE)
    @SerializedName("most_damage_mage")
    private @NotNull ConcurrentMap<Integer, Double> mostDamageMage = Concurrent.newMap();
    @Getter(AccessLevel.NONE)
    @SerializedName("most_damage_berserk")
    private @NotNull ConcurrentMap<Integer, Double> mostDamageBerserk = Concurrent.newMap();
    @Getter(AccessLevel.NONE)
    @SerializedName("most_damage_archer")
    private @NotNull ConcurrentMap<Integer, Double> mostDamageArcher = Concurrent.newMap();
    @Getter(AccessLevel.NONE)
    @SerializedName("most_damage_tank")
    private @NotNull ConcurrentMap<Integer, Double> mostDamageTank = Concurrent.newMap();

    // Fastest Times
    @SerializedName("fastest_time")
    private @NotNull ConcurrentMap<Integer, Integer> fastestTime = Concurrent.newMap();
    @SerializedName("fastest_time_s")
    private @NotNull ConcurrentMap<Integer, Integer> fastestSTierTime = Concurrent.newMap();
    @SerializedName("fastest_time_s_plus")
    private @NotNull ConcurrentMap<Integer, Integer> fastestSPlusTierTime = Concurrent.newMap();

    public @NotNull ConcurrentMap<Integer, Double> getMostDamage(@NotNull DungeonClass.Type classType) {
        return switch (classType) {
            case HEALER -> this.mostDamageHealer.toUnmodifiableMap();
            case MAGE -> this.mostDamageMage.toUnmodifiableMap();
            case BERSERK -> this.mostDamageBerserk.toUnmodifiableMap();
            case ARCHER -> this.mostDamageArcher.toUnmodifiableMap();
            case TANK -> this.mostDamageTank.toUnmodifiableMap();
            default -> Concurrent.newUnmodifiableMap();
        };
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BestRun {

        // Time
        private SkyBlockDate.RealTime timestamp;
        @SerializedName("elapsed_time")
        private int elapsedTime;

        // Score
        @SerializedName("score_exploration")
        private int explorationScore;
        @SerializedName("score_speed")
        private int speedScore;
        @SerializedName("score_skill")
        private int skillScore;
        @SerializedName("score_bonus")
        private int bonusScore;

        // Damage
        @SerializedName("damage_dealt")
        private double damageDealt;
        @SerializedName("damage_mitigated")
        private double damageMitigated;
        @SerializedName("ally_healing")
        private double allyHealing;

        @SerializedName("dungeon_class")
        private @NotNull DungeonClass.Type dungeonClass = DungeonClass.Type.UNKNOWN;
        private ConcurrentList<UUID> teammates;
        @SerializedName("deaths")
        private int deaths;
        @SerializedName("mobs_killed")
        private int mobsKilled;
        @SerializedName("secrets_found")
        private int secretsFound;

    }

}
