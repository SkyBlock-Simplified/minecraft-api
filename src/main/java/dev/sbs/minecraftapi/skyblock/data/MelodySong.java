package dev.sbs.minecraftapi.skyblock.data;

import dev.sbs.api.data.Model;
import org.jetbrains.annotations.NotNull;

public interface MelodySong extends Model {

    @NotNull String getId();

    @NotNull String getName();

    @NotNull Difficulty getDifficulty();

    int getIntelligenceReward();

    enum Difficulty {

        EASY,
        HARD,
        EXPERT,
        VIRTUOSO

    }

}
