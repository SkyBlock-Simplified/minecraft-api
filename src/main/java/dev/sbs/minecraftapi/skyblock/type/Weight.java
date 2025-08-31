package dev.sbs.minecraftapi.skyblock.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public interface Weight {

    double getValue();

    double getOverflow();

    default double getTotal() {
        return this.getValue() + this.getOverflow();
    }

    static @NotNull Weight of(double value, double overflow) {
        return new WeightImpl(value, overflow);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PUBLIC)
    class WeightImpl implements Weight {

        private final double value;
        private final double overflow;

    }

}