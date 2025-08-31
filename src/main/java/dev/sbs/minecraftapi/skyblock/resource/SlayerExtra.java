package dev.sbs.minecraftapi.skyblock.resource;

import dev.sbs.api.data.Model;
import org.jetbrains.annotations.NotNull;

public interface SlayerExtra extends Model {

    @NotNull String getId();

    double getWeightModifier();

    int getWeightDivider();

}
