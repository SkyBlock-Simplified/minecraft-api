package dev.sbs.minecraftapi.client.sbs.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import dev.sbs.api.collection.concurrent.Concurrent;
import dev.sbs.api.collection.concurrent.ConcurrentMap;
import dev.sbs.api.stream.pair.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SkyBlockImagesResponse {

    private final ConcurrentMap<String, Image> items;

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Image {

        private final String normal;
        private final String enchanted;

    }

    public static class Deserializer implements JsonDeserializer<SkyBlockImagesResponse> {

        @Override
        public SkyBlockImagesResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            return new SkyBlockImagesResponse(
                jsonElement.getAsJsonObject()
                    .entrySet()
                    .stream()
                    .map(entry -> Pair.of(
                        entry.getKey(),
                        new Image(
                            entry.getValue().getAsJsonObject().get("normal").getAsString(),
                            entry.getValue().getAsJsonObject().get("enchanted").getAsString()
                        )
                    ))
                    .collect(Concurrent.toUnmodifiableMap())
            );
        }

    }

}
