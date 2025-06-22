package dev.sbs.minecraftapi;

import com.google.gson.Gson;
import dev.sbs.api.SimplifiedApi;
import dev.sbs.api.io.gson.adapter.NbtContentTypeAdapter;
import dev.sbs.api.io.gson.adapter.SkyBlockDateTypeAdapter;
import dev.sbs.minecraftapi.client.hypixel.HypixelClient;
import dev.sbs.minecraftapi.client.hypixel.request.HypixelRequest;
import dev.sbs.minecraftapi.util.SkyBlockDate;
import dev.sbs.minecraftapi.client.hypixel.response.skyblock.implementation.island.util.NbtContent;
import dev.sbs.minecraftapi.client.mojang.MojangProxy;
import dev.sbs.minecraftapi.client.mojang.client.MojangApiClient;
import dev.sbs.minecraftapi.client.mojang.client.MojangSessionClient;
import dev.sbs.minecraftapi.client.mojang.request.MinecraftServerRequest;
import dev.sbs.minecraftapi.client.mojang.request.MinecraftServicesRequest;
import dev.sbs.minecraftapi.client.mojang.request.MojangApiRequest;
import dev.sbs.minecraftapi.client.mojang.request.MojangSessionRequest;
import dev.sbs.minecraftapi.client.mojang.response.MojangMultiUsernameResponse;
import dev.sbs.minecraftapi.client.sbs.SbsClient;
import dev.sbs.minecraftapi.client.sbs.request.SbsRequest;
import dev.sbs.minecraftapi.client.sbs.response.SkyBlockEmojisResponse;
import dev.sbs.minecraftapi.client.sbs.response.SkyBlockImagesResponse;
import dev.sbs.minecraftapi.client.sbs.response.SkyBlockItemsResponse;
import dev.sbs.minecraftapi.nbt.NbtFactory;
import dev.sbs.minecraftapi.text.segment.ColorSegment;
import dev.sbs.minecraftapi.text.segment.LineSegment;
import dev.sbs.minecraftapi.text.segment.TextSegment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code MinecraftApi} is non-instantiable utility class that serves as
 * an extension container for {@link SimplifiedApi} adding support for various
 * managers, services, builders, and API clients used across the application.
 * <p>
 * This class centralizes the initialization and retrieval of dependent resources to ensure
 * a simplified and consistent interface for interacting with API components.
 * <ul>
 *     <li>Adds Minecraft and Hypixel related {@link Gson} configurations.</li>
 *     <li>Adds service support for {@link NbtFactory}.</li>
 *     <li>Adds JSON text support through {@link TextSegment}.</li>
 *     <li>Adds Mojang, Hypixel and Sbs client configurations.</li>
 *     <li>and more...</li>
 * </ul>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftApi extends SimplifiedApi {

    static {
        // Add Minecraft Adapters
        registerGsonTypeAdapter(NbtContent.class, new NbtContentTypeAdapter());
        registerGsonTypeAdapter(MojangMultiUsernameResponse.class, new MojangMultiUsernameResponse.Deserializer());
        registerGsonTypeAdapter(SkyBlockDate.RealTime.class, new SkyBlockDateTypeAdapter.RealTime());
        registerGsonTypeAdapter(SkyBlockDate.SkyBlockTime.class, new SkyBlockDateTypeAdapter.SkyBlockTime());
        registerGsonTypeAdapter(SkyBlockEmojisResponse.class, new SkyBlockEmojisResponse.Deserializer());
        registerGsonTypeAdapter(SkyBlockImagesResponse.class, new SkyBlockImagesResponse.Deserializer());
        registerGsonTypeAdapter(SkyBlockItemsResponse.class, new SkyBlockItemsResponse.Deserializer());

        serviceManager.add(NbtFactory.class, new NbtFactory());

        // Provide Class Builders
        compilerManager.add(MojangApiRequest.class, MojangApiClient.class);
        compilerManager.add(MojangSessionRequest.class, MojangSessionClient.class);
        compilerManager.add(SbsRequest.class, SbsClient.class);
        compilerManager.add(HypixelRequest.class, HypixelClient.class);

        // Provide Builders
        builderManager.add(LineSegment.class, LineSegment.Builder.class);
        builderManager.add(ColorSegment.class, ColorSegment.Builder.class);
        builderManager.add(TextSegment.class, TextSegment.Builder.class);

        // Create Api Handlers & Feign Proxies
        MojangProxy mojangProxy = new MojangProxy();
        serviceManager.add(MojangProxy.class, mojangProxy);
        serviceManager.add(MojangApiRequest.class, mojangProxy.getApiRequest());
        serviceManager.add(MojangSessionRequest.class, mojangProxy.getSessionRequest());
        serviceManager.add(MinecraftServicesRequest.class, mojangProxy.getServicesRequest());
        serviceManager.add(MinecraftServerRequest.class, new MinecraftServerRequest());

        SbsClient sbsApiClient = new SbsClient();
        serviceManager.add(SbsClient.class, sbsApiClient);
        serviceManager.add(SbsRequest.class, sbsApiClient.build(SbsRequest.class));

        HypixelClient hypixelApiClient = new HypixelClient();
        serviceManager.add(HypixelClient.class, hypixelApiClient);
        serviceManager.add(HypixelRequest.class, hypixelApiClient.build(HypixelRequest.class));
    }

    public static @NotNull NbtFactory getNbtFactory() {
        return serviceManager.get(NbtFactory.class);
    }

    /**
     * Gets the {@link MojangProxy} used to interact with the Mojang API.
     */
    public static @NotNull MojangProxy getMojangProxy() {
        return serviceManager.get(MojangProxy.class);
    }

}
