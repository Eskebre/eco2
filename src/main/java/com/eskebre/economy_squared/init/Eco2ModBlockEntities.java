/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import com.eskebre.economy_squared.block.entity.TicketBlockBlockEntity;
import com.eskebre.economy_squared.block.entity.ShopBlockEntity;
import com.eskebre.economy_squared.block.entity.SellBlockBlockEntity;
import com.eskebre.economy_squared.block.entity.PlayerShopBlockEntity;
import com.eskebre.economy_squared.Eco2Mod;

@EventBusSubscriber
public class Eco2ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Eco2Mod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShopBlockEntity>> SHOP = register("shop", Eco2ModBlocks.SHOP, ShopBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SellBlockBlockEntity>> SELL_BLOCK = register("sell_block", Eco2ModBlocks.SELL_BLOCK, SellBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerShopBlockEntity>> PLAYER_SHOP = register("player_shop", Eco2ModBlocks.PLAYER_SHOP, PlayerShopBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TicketBlockBlockEntity>> TICKET_BLOCK = register("ticket_block", Eco2ModBlocks.TICKET_BLOCK, TicketBlockBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.Item.BLOCK, SHOP.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SELL_BLOCK.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, PLAYER_SHOP.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TICKET_BLOCK.get(), WorldlyContainerWrapper::new);
	}
}