package dev.sbs.minecraftapi.skyblock.data.json;

import dev.sbs.api.data.json.JsonModel;
import dev.sbs.api.data.json.Resource;
import dev.sbs.minecraftapi.skyblock.data.MelodySong;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@Resource(
    path = "skyblock",
    name = "melody_songs"
)
@NoArgsConstructor(access = AccessLevel.NONE)
public class JsonMelodySong implements MelodySong, JsonModel {

    private @Id @NotNull String id = "";
    private @NotNull String name = "";
    private @NotNull Difficulty difficulty = Difficulty.EASY;
    private int intelligenceReward;

}
