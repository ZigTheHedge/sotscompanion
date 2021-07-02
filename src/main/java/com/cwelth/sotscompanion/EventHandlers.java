package com.cwelth.sotscompanion;

import com.cwelth.sotscompanion.SotsMod;
import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.blocks.PortalBlock;
import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.world.teleporter.TeleporterAtumStart;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SotsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlers {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlace(BlockEvent.EntityPlaceEvent event) {
        BlockState state = event.getPlacedBlock();
        if (event.getEntity() != null && event.getEntity().level.dimension() == Atum.ATUM) {
            if (state.getBlock() == Blocks.GRASS_BLOCK) {
                event.getWorld().setBlock(event.getPos(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
            }
            if (state.getBlock() == Blocks.FARMLAND) {
                event.getWorld().setBlock(event.getPos(), Blocks.FARMLAND.defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerFellOut(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        if(player.level.dimension() == World.OVERWORLD)
        {
            if (player instanceof ServerPlayerEntity && player.level instanceof ServerWorld) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
                ServerWorld world = (ServerWorld)player.level;
                PortalBlock.changeDimension(world, serverPlayer, new TeleporterAtumStart());
                serverPlayer.setRespawnPosition(Atum.ATUM, serverPlayer.blockPosition(), serverPlayer.getYHeadRot(), true, false);
            }
        }
    }

}
