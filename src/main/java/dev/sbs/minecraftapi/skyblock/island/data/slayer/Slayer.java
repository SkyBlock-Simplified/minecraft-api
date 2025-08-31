package dev.sbs.minecraftapi.skyblock.island.data.slayer;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.util.NumberUtil;
import dev.sbs.api.util.StringUtil;
import dev.sbs.minecraftapi.skyblock.type.Experience;
import dev.sbs.minecraftapi.skyblock.type.Weight;
import dev.sbs.minecraftapi.skyblock.type.Weighted;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Arrays;

@Getter
public class Slayer implements Experience, Weighted {

    private final @NotNull Type type;
    private final double experience;
    private final @NotNull ConcurrentMap<Integer, Boolean> claimed;
    private final @NotNull ConcurrentMap<Integer, Boolean> claimedSpecial;
    private final @NotNull ConcurrentMap<Integer, Integer> kills;
    private final @NotNull ConcurrentMap<Integer, Integer> attempts;

    Slayer(@NotNull Type type, @NotNull BossData bossData) {
        this.type = type;
        this.experience = Math.max(bossData.getExperience(), 0);
        this.claimed = bossData.getClaimed();
        this.claimedSpecial = bossData.getClaimedSpecial();
        this.kills = bossData.getKills();
        this.attempts = bossData.getAttempts();
    }

    @Override
    public @NotNull ConcurrentList<Integer> getExperienceTiers() {
        return this.getType().getExperienceTiers();
    }

    @Override
    public int getMaxLevel() {
        return this.getType().getMaxLevel();
    }

    @Override
    public @NotNull Weight getWeight() {
        if (this.getType().getWeightDivider() == 0.0)
            return Weight.of(0, 0);

        ConcurrentList<Integer> experienceTiers = this.getExperienceTiers();
        double maxSlayerExperienceRequired = experienceTiers.get(experienceTiers.size() - 1);
        double base = Math.min(this.getExperience(), maxSlayerExperienceRequired) / this.getType().getWeightDivider();
        double weightValue = NumberUtil.round(base, 2);
        double weightOverflow = 0;

        if (this.getExperience() > maxSlayerExperienceRequired) {
            double remaining = this.getExperience() - maxSlayerExperienceRequired;
            double overflow = 0;
            double modifier = this.getType().getWeightModifier();

            while (remaining > 0) {
                double left = Math.min(remaining, maxSlayerExperienceRequired);
                overflow += Math.pow(left / (this.getType().getWeightDivider() * (1.5 + modifier)), 0.942);
                remaining -= left;
                modifier += modifier;
            }

            weightOverflow = NumberUtil.round(overflow, 2);
        }

        return Weight.of(weightValue, weightOverflow);
    }

    public boolean isClaimed(int level) {
        return this.getClaimed().getOrDefault(level, false);
    }

    @Getter
    public static class Quest {

        private @NotNull Type type = Type.UNKNOWN;
        private int tier;
        @SerializedName("start_timestamp")
        private Instant start;
        @SerializedName("completion_state")
        private int completionState;
        @SerializedName("used_armor")
        private boolean usedArmor;
        private boolean solo;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {

        UNKNOWN(Concurrent.newUnmodifiableList(), 0.0, 0),
        @SerializedName("ZOMBIE")
        REVENANT_HORROR(DEFAULT_TIERS, 0.15, 2_208),
        @SerializedName("SPIDER")
        TARANTULA_BROODFATHER(DEFAULT_TIERS, 0.08, 2_118),
        @SerializedName("WOLF")
        SVEN_PACKMASTER(WOLF_TIERS, 0.015, 1_962),
        @SerializedName("ENDERMAN")
        VOIDGLOOM_SERAPH(DEFAULT_TIERS, 0.017, 1_430),
        @SerializedName("BLAZE")
        INFERNO_DEMONLORD(DEFAULT_TIERS, 0.0, 0),
        @SerializedName("VAMPIRE")
        RIFTSTALKER_BLOODFIEND(VAMPIRE_TIERS, 0.0, 0);

        private final @NotNull ConcurrentList<Integer> experienceTiers;
        private final double weightModifier;
        private final int weightDivider;

        public int getMaxLevel() {
            return this.getExperienceTiers().size();
        }

        public @NotNull String getName() {
            return StringUtil.capitalizeFully(this.name().replace("_", " "));
        }

        public boolean notUnknown() {
            return this != UNKNOWN;
        }

        public static @NotNull Type of(@NotNull String name) {
            return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
        }

    }

    private static final @NotNull ConcurrentList<Integer> DEFAULT_TIERS = Concurrent.newUnmodifiableList(
        5, 15, 200, 1_000, 5_000, 20_000, 100_000, 400_000, 1_000_000
    );

    private static final @NotNull ConcurrentList<Integer> WOLF_TIERS = Concurrent.newUnmodifiableList(
        5, 15, 200, 1_500, 5_000, 20_000, 100_000, 400_000, 1_000_000
    );

    private static final @NotNull ConcurrentList<Integer> VAMPIRE_TIERS = Concurrent.newUnmodifiableList(
        20, 75, 240, 840, 2_400
    );

}
