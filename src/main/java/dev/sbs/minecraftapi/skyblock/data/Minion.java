package dev.sbs.minecraftapi.skyblock.data;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.collection.concurrent.ConcurrentList;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.data.Model;
import dev.sbs.minecraftapi.MinecraftApi;
import org.jetbrains.annotations.NotNull;

public interface Minion extends Model {

    @NotNull String getId();

    @NotNull String getName();

    @NotNull String getCollectionId();

    @NotNull ConcurrentList<Tier> getTiers();

    default @NotNull Collection getCollection() {
        return MinecraftApi.getRepositoryOf(Collection.class)
            .findFirstOrNull(Collection::getId, this.getCollectionId());
    }

    interface Tier {

        int getTier();

        double getSpeed();

        @NotNull String getItemId();

        @NotNull UpgradeCost getUpgradeCost();

        default @NotNull Item getItem() {
            return MinecraftApi.getRepositoryOf(Item.class)
                .findFirstOrNull(Item::getId, this.getItemId());
        }

    }

    interface UpgradeCost {

        @NotNull ConcurrentMap<Currency, Double> getCurrencies();

        @NotNull ConcurrentMap<String, Double> getItems();

        enum Currency {

            @SerializedName("coins")
            COINS,
            @SerializedName("pelts")
            PELTS,
            @SerializedName("northStars")
            NORTH_STARS

        }

    }

}
