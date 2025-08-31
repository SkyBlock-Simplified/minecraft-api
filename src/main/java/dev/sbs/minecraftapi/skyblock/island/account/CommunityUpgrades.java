package dev.sbs.minecraftapi.skyblock.island.account;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.util.StringUtil;
import dev.sbs.minecraftapi.skyblock.date.SkyBlockDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUpgrades {

    @SerializedName("currently_upgrading")
    private @NotNull Optional<Upgrading> currentlyUpgrading = Optional.empty();
    @SerializedName("upgrade_states")
    private @NotNull ConcurrentList<Upgraded> upgraded = Concurrent.newList();

    public int getHighestTier(@NotNull Type type) {
        return this.getUpgraded()
            .stream()
            .filter(upgraded -> upgraded.getUpgrade().name().equalsIgnoreCase(type.name()))
            .sorted((o1, o2) -> Comparator.comparing(Upgraded::getTier).compare(o2, o1))
            .map(Upgraded::getTier)
            .findFirst()
            .orElse(0);
    }

    public @NotNull ConcurrentList<Upgraded> getUpgrades(@NotNull Type type) {
        return this.getUpgraded()
            .stream()
            .filter(upgraded -> upgraded.getUpgrade().name().equalsIgnoreCase(type.name()))
            .sorted((o1, o2) -> Comparator.comparing(Upgraded::getTier).compare(o1, o2))
            .collect(Concurrent.toList());
    }

    @Getter
    public static class Upgraded {

        @SerializedName("upgrade")
        private Type upgrade;
        private int tier;
        @SerializedName("started_ms")
        private SkyBlockDate.RealTime started;
        @SerializedName("started_by")
        private String startedBy;
        @SerializedName("claimed_ms")
        private SkyBlockDate.RealTime claimed;
        @SerializedName("claimed_by")
        private String claimedBy;
        @SerializedName("fasttracked")
        private boolean fastTracked;

    }

    @Getter
    public static class Upgrading {

        @SerializedName("upgrade")
        private Type upgrade;
        @SerializedName("new_tier")
        private int newTier;
        @SerializedName("start_ms")
        private SkyBlockDate.RealTime started;
        @SerializedName("who_started")
        private String startedBy;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {

        @SerializedName("minion_slots")
        MINION_SLOTS(5),
        @SerializedName("coins_allowance")
        COINS_ALLOWANCE(5),
        @SerializedName("guests_count")
        GUESTS_COUNT(5),
        @SerializedName("island_size")
        ISLAND_SIZE(10),
        @SerializedName("coop_slots")
        COOP_SLOTS(3);

        private final int maxLevel;

        public @NotNull String getName() {
            return StringUtil.capitalizeFully(this.name().replace("_", " "));
        }

    }

}
