package dev.sbs.minecraftapi.skyblock.island.data.slayer;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.io.gson.PostInit;
import dev.sbs.api.stream.pair.Pair;
import dev.sbs.minecraftapi.skyblock.type.Weight;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
public class SlayerData implements PostInit {

    private final static @NotNull Slayer UNKNOWN = new Slayer(Slayer.Type.UNKNOWN, new BossData());
    @SerializedName("slayer_quest")
    private final @NotNull Optional<Slayer.Quest> activeQuest = Optional.empty();
    @Getter(AccessLevel.NONE)
    @SerializedName("slayer_bosses")
    private @NotNull ConcurrentMap<Slayer.Type, BossData> slayerBosses = Concurrent.newMap();
    private ConcurrentList<Slayer> slayers;

    @Override
    public void postInit() {
        this.slayers = this.slayerBosses.stream()
            .map(Slayer::new)
            .collect(Concurrent.toUnmodifiableList());
    }

    public @NotNull Slayer getSlayer(@NotNull Slayer.Type type) {
        return this.getSlayer(type.name());
    }

    public @NotNull Slayer getSlayer(@NotNull String type) {
        return this.getSlayers().matchFirst(skill -> skill.getType().name().equalsIgnoreCase(type)).orElse(UNKNOWN);
    }

    public double getAverage() {
        return this.getSlayers()
            .stream()
            .mapToDouble(Slayer::getLevel)
            .sum() / this.getSlayers().size();
    }

    public double getExperience() {
        return this.getSlayers()
            .stream()
            .mapToDouble(Slayer::getExperience)
            .sum();
    }

    public double getProgressPercentage() {
        return this.getSlayers()
            .stream()
            .mapToDouble(Slayer::getTotalProgressPercentage)
            .sum() / this.getSlayers().size();
    }

    public @NotNull ConcurrentMap<Slayer, Weight> getWeight() {
        return this.getSlayers()
            .stream()
            .map(slayer -> Pair.of(slayer, slayer.getWeight()))
            .collect(Concurrent.toMap());
    }

}
