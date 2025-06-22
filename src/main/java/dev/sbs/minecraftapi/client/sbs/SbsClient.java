package dev.sbs.minecraftapi.client.sbs;

import dev.sbs.api.client.Client;
import dev.sbs.minecraftapi.client.sbs.exception.SbsApiException;
import dev.sbs.minecraftapi.client.sbs.request.SbsRequest;
import dev.sbs.api.client.response.CFCacheStatus;
import dev.sbs.api.collection.concurrent.Concurrent;
import feign.FeignException;

public final class SbsClient extends Client<SbsRequest> {

    public SbsClient() {
        super("api.sbs.dev");
        super.setCachedResponseHeaders(Concurrent.newUnmodifiableSet(
            CFCacheStatus.HEADER_KEY,
            "RateLimit-Limit",
            "RateLimit-Remaining",
            "RateLimit-Reset"
        ));
        super.setErrorDecoder((methodKey, response) -> {
            throw new SbsApiException(FeignException.errorStatus(methodKey, response));
        });
    }

}
