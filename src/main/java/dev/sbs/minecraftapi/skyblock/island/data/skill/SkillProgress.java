package dev.sbs.minecraftapi.skyblock.island.data.skill;

import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.util.NumberUtil;
import dev.sbs.minecraftapi.MinecraftApi;
import dev.sbs.minecraftapi.skyblock.island.Member;
import dev.sbs.minecraftapi.skyblock.resource.Skill;
import dev.sbs.minecraftapi.skyblock.type.Experience;
import dev.sbs.minecraftapi.skyblock.type.Weight;
import dev.sbs.minecraftapi.skyblock.type.Weighted;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class SkillProgress implements Experience, Weighted {

    private final @NotNull String id;
    private final double experience;
    private final int levelSubtractor;

    public SkillProgress(@NotNull String id, double experience, @NotNull Member member) {
        this.id = id;
        this.experience = Math.max(experience, 0);
        this.levelSubtractor = this.calcLevelSubtractor(member);
    }

    private int calcLevelSubtractor(@NotNull Member member) {
        return switch (this.getId()) {
            case "FARMING" -> 10 - member.getJacobsContest().getFarmingLevelCap();
            case "FORAGING" -> {
                int subtractor = 0;
                subtractor += member.getCollectionUnlocked().getOrDefault("FIG_LOG", 0) < 9 ? 1 : 0;
                subtractor += member.getCollectionUnlocked().getOrDefault("MANGROVE_LOG", 0) < 9 ? 1 : 0;
                // TODO: Agatha Shop
                // TODO: Hopefully collection unlocks are in the api
                yield subtractor;
            }
            default -> 0;
        };
    }

    public @NotNull Skill getSkill() {
        return MinecraftApi.getRepositoryOf(Skill.class).findFirstOrNull(Skill::getId, this.getId());
    }

    @Override
    public @NotNull ConcurrentList<Integer> getExperienceTiers() {
        return this.getSkill().getExperienceTiers();
    }

    @Override
    public int getMaxLevel() {
        return this.getSkill().getMaxLevel();
    }

    @Override
    public @NotNull Weight getWeight() {
        if (this.getSkill().getWeightDivider() == 0.0)
            return Weight.of(0, 0);

        double rawLevel = this.getRawLevel();
        ConcurrentList<Integer> experienceTiers = this.getExperienceTiers();
        double maxSkillExperienceRequired = experienceTiers.get(experienceTiers.size() - 1);

        if (rawLevel < this.getMaxLevel())
            rawLevel += (this.getProgressPercentage() / 100); // Add Percentage Progress to Next Level

        double base = Math.pow(rawLevel * 10, 0.5 + this.getSkill().getWeightExponent() + (rawLevel / 100.0)) / 1250;
        double weightValue = NumberUtil.round(base, 2);
        double weightOverflow = 0;

        if (this.getExperience() > maxSkillExperienceRequired) {
            double overflow = Math.pow((this.getExperience() - maxSkillExperienceRequired) / this.getSkill().getWeightDivider(), 0.968);
            weightOverflow = NumberUtil.round(overflow, 2);
        }

        return Weight.of(weightValue, weightOverflow);
    }

}