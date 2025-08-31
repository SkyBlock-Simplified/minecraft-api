package dev.sbs.minecraftapi.skyblock.data.json;

import dev.sbs.api.data.json.JsonModel;
import dev.sbs.api.data.json.Resource;
import dev.sbs.minecraftapi.skyblock.data.MobType;
import dev.sbs.minecraftapi.text.ChatFormat;
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
    name = "mob_types"
)
@NoArgsConstructor(access = AccessLevel.NONE)
public class JsonMobType implements MobType, JsonModel {

    private @Id @NotNull String id = "";
    private @NotNull String name = "";
    private @NotNull String symbol = "";
    private @NotNull ChatFormat format = ChatFormat.WHITE;

}
