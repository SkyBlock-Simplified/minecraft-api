package dev.sbs.minecraftapi.exception;

import org.intellij.lang.annotations.PrintFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinecraftException extends RuntimeException {

    public MinecraftException(@NotNull Throwable cause) {
        super(cause);
    }

    public MinecraftException(@NotNull String message) {
        super(message);
    }

    public MinecraftException(@NotNull Throwable cause, @NotNull String message) {
        super(message, cause);
    }

    public MinecraftException(@NotNull @PrintFormat String message, @Nullable Object... args) {
        super(String.format(message, args));
    }

    public MinecraftException(@NotNull Throwable cause, @NotNull @PrintFormat String message, @Nullable Object... args) {
        super(String.format(message, args), cause);
    }

}
