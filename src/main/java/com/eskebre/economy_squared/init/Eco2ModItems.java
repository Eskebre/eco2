/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import java.util.function.Function;

import com.eskebre.economy_squared.item.*;
import com.eskebre.economy_squared.Eco2Mod;

public class Eco2ModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Eco2Mod.MODID);
	public static final DeferredItem<Item> CURRENCY_1;
	public static final DeferredItem<Item> CURRENCY_4;
	public static final DeferredItem<Item> CURRENCY_16;
	public static final DeferredItem<Item> CURRENCY_64;
	public static final DeferredItem<Item> ATM;
	public static final DeferredItem<Item> SHOP;
	public static final DeferredItem<Item> SHOP_CONFIGURATOR;
	public static final DeferredItem<Item> SELL_BLOCK;
	public static final DeferredItem<Item> PLAYER_SHOP;
	public static final DeferredItem<Item> WALLET;
	public static final DeferredItem<Item> TICKET_BLOCK;
	static {
		CURRENCY_1 = register("currency_1", Currency1Item::new);
		CURRENCY_4 = register("currency_4", Currency4Item::new);
		CURRENCY_16 = register("currency_16", Currency16Item::new);
		CURRENCY_64 = register("currency_64", Currency64Item::new);
		ATM = block(Eco2ModBlocks.ATM);
		SHOP = block(Eco2ModBlocks.SHOP);
		SHOP_CONFIGURATOR = register("shop_configurator", ShopConfiguratorItem::new);
		SELL_BLOCK = block(Eco2ModBlocks.SELL_BLOCK);
		PLAYER_SHOP = block(Eco2ModBlocks.PLAYER_SHOP);
		WALLET = register("wallet", WalletItem::new);
		TICKET_BLOCK = block(Eco2ModBlocks.TICKET_BLOCK);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, Item.Properties::new);
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), () -> properties);
	}
}