package dev.sbs.minecraftapi.skyblock.data;

import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.data.Model;
import dev.sbs.api.stream.pair.Pair;
import dev.sbs.minecraftapi.MinecraftApi;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Slayer extends Model {

    @NotNull String getId();

    @NotNull String getName();

    @NotNull String getDescription();

    default @NotNull ConcurrentMap<String, Double> getEffects() {
        return this.getLevels()
            .stream()
            .flatMap(level -> level.getEffects().stream())
            .collect(Concurrent.toUnmodifiableMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                Double::sum
            ));
    }

    default @NotNull ConcurrentList<Integer> getExperienceTiers() {
        return this.getLevels()
            .stream()
            .map(Level::getTotalRequiredXP)
            .collect(Concurrent.toUnmodifiableList());
    }

    int getMaxLevel();

    int getMaxTier();

    double getWeightModifier();

    int getWeightDivider();

    @NotNull String getMobTypeId();

    default @NotNull MobType getMobType() {
        return MinecraftApi.getRepositoryOf(MobType.class)
            .findFirstOrNull(MobType::getId, this.getMobTypeId());
    }

    @NotNull ConcurrentList<Level> getLevels();

    interface Level {

        int getLevel();

        int getTotalRequiredXP();

        @NotNull String getTitle();

        @NotNull ConcurrentList<String> getUnlocks();

        default @NotNull ConcurrentMap<String, Double> getEffects() {
            return MinecraftApi.getRepositoryOf(Stat.class)
                .stream()
                .map(stat -> Pair.of(
                    stat.getId(),
                    this.getUnlocks()
                        .indexedStream()
                        .map((line, index, size) -> {
                            String value = "0.0";

                            if (line.startsWith("+") && line.endsWith(stat.getName())) // Flat
                                value = line.split("\\s+")[0];
                            else if (line.contains("Grants +") && line.contains(stat.getName()) && index == size - 1) // Tiered
                                value = line.split("\\s+")[2];

                            value = value.replace("+", "");
                            value = value.replace("%", "");

                            if (value.contains("➜")) // Tiered
                                value = value.split("➜")[1];

                            return value;
                        })
                        .mapToDouble(Double::parseDouble)
                        .sum()
                ))
                .filter(entry -> entry.getValue() > 0.0)
                .collect(Concurrent.toUnmodifiableMap());
        }

    }

}
