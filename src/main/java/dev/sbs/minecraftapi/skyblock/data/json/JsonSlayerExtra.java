package dev.sbs.minecraftapi.skyblock.data.json;

import dev.sbs.api.builder.EqualsBuilder;
import dev.sbs.api.builder.HashCodeBuilder;
import dev.sbs.api.data.json.JsonModel;
import dev.sbs.api.data.json.JsonResource;
import dev.sbs.minecraftapi.skyblock.data.SlayerExtra;
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
    name = "slayers_extra"
)
@NoArgsConstructor(access = AccessLevel.NONE)
public class JsonSlayerExtra implements SlayerExtra, JsonModel {

    private @Id @NotNull String id = "";
    private double weightModifier;
    private int weightDivider;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        JsonSlayerExtra that = (JsonSlayerExtra) o;

        return new EqualsBuilder()
            .append(this.getWeightModifier(), that.getWeightModifier())
            .append(this.getWeightDivider(), that.getWeightDivider())
            .append(this.getId(), that.getId())
            .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getId())
            .append(this.getWeightModifier())
            .append(this.getWeightDivider())
            .build();
    }

}
