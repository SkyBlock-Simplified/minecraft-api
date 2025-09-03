package dev.sbs.minecraftapi.skyblock.data.json;

import com.google.gson.annotations.SerializedName;
import dev.sbs.api.data.json.JsonModel;
import dev.sbs.api.data.json.JsonResource;
import dev.sbs.minecraftapi.skyblock.data.TrophyFish;
import dev.sbs.minecraftapi.text.ChatFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@JsonResource(
    path = "skyblock",
    name = "trophy_fishes"
)
@NoArgsConstructor(access = AccessLevel.NONE)
public class JsonTrophyFish implements TrophyFish, JsonModel {

    private @Id @NotNull String id = "";
    private @NotNull String name = "";
    private @NotNull ChatFormat format = ChatFormat.WHITE;
    @SerializedName("location")
    private @NotNull String locationId = "";

}
