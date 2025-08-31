package dev.sbs.minecraftapi.skyblock;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;

@Getter
public class Article {

    @SerializedName("item.material")
    private String material;
    @Getter(AccessLevel.NONE)
    private String link;
    @SerializedName("text")
    private String date;
    private String title;

    public URL getUrl() {
        try {
            return new URL(this.link);
        } catch (MalformedURLException muex) {
            throw new IllegalArgumentException(String.format("Unable to create URL '%s'!", this.link));
        }
    }

}
