package dev.sbs.minecraftapi.skyblock.data;

import dev.sbs.api.data.Model;
import org.jetbrains.annotations.NotNull;

public interface SkillExtra extends Model {

    @NotNull String getId();

    double getWeightExponent();

    int getWeightDivider();

    boolean isCosmetic();

    default boolean notCosmetic() {
        return !this.isCosmetic();
    }

}
