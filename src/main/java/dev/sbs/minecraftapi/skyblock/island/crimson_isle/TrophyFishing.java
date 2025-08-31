package dev.sbs.minecraftapi.skyblock.island.crimson_isle;

import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.stream.pair.Pair;
import dev.sbs.minecraftapi.MinecraftApi;
import dev.sbs.minecraftapi.skyblock.data.TrophyFish;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
public class TrophyFishing {

    private final @NotNull ConcurrentMap<String, ConcurrentMap<TrophyFish.Tier, Integer>> fish;
    private final int totalCaught;
    private final @NotNull Optional<Pair<String, TrophyFish.Tier>> lastCaught;

    public TrophyFishing(@NotNull ConcurrentMap<String, Object> trophy_fish) {
        this.totalCaught = (int) trophy_fish.removeOrGet("total_caught", 0);

        this.lastCaught = trophy_fish.getOptional("last_caught")
            .map(String::valueOf)
            .map(value -> value.split("/"))
            .map(parts -> Pair.of(parts[0], TrophyFish.Tier.valueOf(parts[1])));

        this.fish = MinecraftApi.getRepositoryOf(TrophyFish.class)
            .stream()
            .map(type -> Pair.of(
                type.getId(),
                trophy_fish.stream()
                    .filter(entry -> entry.getKey().startsWith(type.getId().toLowerCase()))
                    .map(entry -> Pair.of(
                        TrophyFish.Tier.valueOf(entry.getKey().replace(type.getId(), "")),
                        (int) entry.getValue()
                    ))
                    .collect(Concurrent.toMap())
            ))
            .collect(Concurrent.toMap());
    }

}
