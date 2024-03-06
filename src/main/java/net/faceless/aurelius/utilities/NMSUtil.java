package net.faceless.aurelius.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftMagicNumbers;
import org.jetbrains.annotations.Nullable;

public class NMSUtil {

    @Nullable
    public static Block getNMSBlock(Material material) {
        if (!material.isBlock()) return null;
        return CraftMagicNumbers.getBlock(material);
    }

    public static BlockPos toBlockPos(Location location) {
        return new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location toLocation(BlockPos pos, World world) {
        return new Location(world, pos.getX(), pos.getY(), pos.getZ());
    }

}
