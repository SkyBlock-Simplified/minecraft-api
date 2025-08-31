package dev.sbs.minecraftapi.skyblock.island;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.linked.ConcurrentLinkedMap;
import dev.sbs.minecraftapi.skyblock.island.account.Banking;
import dev.sbs.minecraftapi.skyblock.island.account.CommunityUpgrades;
import dev.sbs.minecraftapi.skyblock.profile_stats.ProfileStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SkyBlockIsland {

    private static final DecimalFormat smallDecimalFormat = new DecimalFormat("#0.#");

    @SerializedName("profile_id")
    private @NotNull UUID islandId;
    @SerializedName("community_upgrades")
    private @NotNull Optional<CommunityUpgrades> communityUpgrades = Optional.empty();
    private @NotNull Optional<Banking> banking = Optional.empty();
    @SerializedName("game_mode")
    private @NotNull Optional<GameMode> gameMode = Optional.of(GameMode.NORMAL);
    @SerializedName("cute_name")
    private @NotNull Optional<Profile> profile = Optional.empty();
    private boolean selected;
    private @NotNull ConcurrentLinkedMap<UUID, Member> members = Concurrent.newLinkedMap();

    public boolean hasMember(@NotNull UUID uniqueId) {
        return this.getMembers().containsKey(uniqueId);
    }

    public @NotNull ConcurrentLinkedMap<String, Long> getCollection() {
        return this.getMembers()
            .stream()
            .flatMap((uniqueId, member) -> member.getCollection().stream())
            .collect(Concurrent.toLinkedMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                Long::sum
            ));
    }

    public @NotNull ConcurrentLinkedMap<String, Integer> getCollectionUnlocked() {
        return this.getMembers()
            .stream()
            .flatMap((uniqueId, member) -> member.getCollectionUnlocked().stream())
            .collect(Concurrent.toLinkedMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                Integer::sum
            ));
    }

    public @NotNull ConcurrentList<Integer> getCraftedMinions(@NotNull String itemId) {
        return this.getMembers()
            .stream()
            .values()
            .flatMap(member -> member.getCraftedMinions(itemId).stream())
            .distinct()
            .collect(Concurrent.toUnmodifiableList())
            .sorted(Comparator.naturalOrder());
    }

    public @NotNull ProfileStats getProfileStats(@NotNull Member member) {
        return this.getProfileStats(member, true);
    }

    public @NotNull ProfileStats getProfileStats(@NotNull Member member, boolean calculateBonus) {
        return new ProfileStats(this, member, calculateBonus);
    }

    public int getUniqueMinions() {
        return this.getMembers()
            .stream()
            .values()
            .mapToInt(member -> member.getPlayerData().getCraftedMinions().size())
            .sum() + this.getCommunityUpgrades()
            .map(communityUpgrades -> communityUpgrades.getUpgraded()
                .stream()
                .filter(upgraded -> upgraded.getUpgrade() == CommunityUpgrades.Type.MINION_SLOTS)
                .count()
            )
            .map(Long::intValue)
            .orElse(0);
    }

}
