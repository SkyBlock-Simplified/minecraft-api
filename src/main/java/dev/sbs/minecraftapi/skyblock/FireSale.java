package dev.sbs.minecraftapi.skyblock;

import com.google.gson.annotations.SerializedName;
import dev.sbs.minecraftapi.skyblock.date.SkyBlockDate;
import lombok.Getter;

@Getter
public class FireSale {

    @SerializedName("item_id")
    private String itemId;
    private SkyBlockDate.RealTime start;
    private SkyBlockDate.RealTime end;
    private int amount;
    private int price;

}
