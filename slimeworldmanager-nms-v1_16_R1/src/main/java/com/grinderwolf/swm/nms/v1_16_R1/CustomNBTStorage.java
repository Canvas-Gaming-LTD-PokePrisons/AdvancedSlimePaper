package com.grinderwolf.swm.nms.v1_16_R1;

import com.flowpowered.nbt.CompoundTag;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.nms.CraftSlimeWorld;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.server.v1_16_R1.*;

import java.io.File;
import java.util.UUID;

@Getter
public class CustomNBTStorage extends WorldNBTStorage {

    private static final GameRules EMPTY_GAMERULES = new GameRules();

    @Getter(value = AccessLevel.NONE)
    private final UUID uuid = UUID.randomUUID();
    private final SlimeWorld world;
    private WorldData worldData;

    public CustomNBTStorage(SlimeWorld world) {
        super(new File("temp_" + world.getName()), null);

        this.world = world;
    }

    public WorldData getWorldData() {
        if (worldData == null) {
            worldData = new CustomWorldData((CraftSlimeWorld) world);
        }

        return worldData;
    }

    public void checkSession() { }

    public void saveWorldData(WorldData worldData, NBTTagCompound nbtTagCompound) {
        CompoundTag gameRules = (CompoundTag) Converter.convertTag("gamerules", worldData.v().a()).getAsCompoundTag().get();
        CompoundTag extraData = this.world.getExtraData();

        extraData.getValue().remove("gamerules");

        if (!gameRules.getValue().isEmpty()) {
            extraData.getValue().put("gamerules", gameRules);
        }
    }

    public void saveWorldData(WorldData worldData) {
        this.saveWorldData(worldData, null);
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void save(EntityHuman entityHuman) {

    }

    @Override
    public NBTTagCompound load(EntityHuman entityHuman) {
        return null;
    }

    @Override public String[] getSeenPlayers() {
        return new String[0];
    }
}