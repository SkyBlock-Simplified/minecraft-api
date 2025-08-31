package dev.sbs.minecraftapi.skyblock.island;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum Profile {

    APPLE("🍎", true),
    BANANA("🍌", true),
    BLUEBERRY("🫐", true),
    COCONUT("🥥", true),
    CUCUMBER("🥒", true),
    GRAPES("🍇", true),
    KIWI("🥝", true),
    LEMON("🍋", true),
    LIME("\uD83C\uDF4B\u200D\uD83D\uDFE9", true),
    MANGO("🥭", true),
    ORANGE("🍊", true),
    PAPAYA("🍈", true),
    PEACH("🍑", true),
    PEAR("🍐", true),
    PINEAPPLE("🍍", true),
    POMEGRANATE("🌰", true),
    RASPBERRY("🍒", true),
    STRAWBERRY("🍓", true),
    TOMATO("🍅", true),
    WATERMELON("🍉", true),
    ZUCCHINI("🍆", true),

    // Heatran
    @SerializedName("Complain Everyday")
    COMPLAIN_EVERYDAY("💢", false),

    // Linfoot
    @SerializedName("TEST")
    TEST("💢", false),

    // Restored Profiles
    @SerializedName("Restored")
    RESTORED("🗑", false),

    // Akinsoft
    @SerializedName("Not Allowed To Quit Skyblock Ever Again")
    NOT_ALLOWED_TO_QUIT_SKYBLOCK_EVER_AGAIN("💢", false);

    private final @NotNull String symbol;
    private final boolean usedForFruitBowl;

    public @NotNull String getName() {
        return StringUtil.capitalizeFully(this.name().replace("_", " "));
    }

}
