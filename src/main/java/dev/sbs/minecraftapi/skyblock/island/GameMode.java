package dev.sbs.minecraftapi.skyblock.island;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public enum GameMode {

    NORMAL,
    @SerializedName("ironman")
    IRONMAN,
    @SerializedName("island")
    STRANDED,
    @SerializedName("bingo")
    BINGO;

    public @NotNull String getName() {
        return StringUtil.capitalizeFully(this.name());
    }

}
