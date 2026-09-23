/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

import com.eskebre.economy_squared.block.TicketBlockBlock;
import com.eskebre.economy_squared.block.ShopBlock;
import com.eskebre.economy_squared.block.SellBlockBlock;
import com.eskebre.economy_squared.block.PlayerShopBlock;
import com.eskebre.economy_squared.block.ATMBlock;
import com.eskebre.economy_squared.Eco2Mod;

public class Eco2ModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Eco2Mod.MODID);
	public static final DeferredBlock<Block> ATM;
	public static final DeferredBlock<Block> SHOP;
	public static final DeferredBlock<Block> SELL_BLOCK;
	public static final DeferredBlock<Block> PLAYER_SHOP;
	public static final DeferredBlock<Block> TICKET_BLOCK;
	static {
		ATM = register("atm", ATMBlock::new);
		SHOP = register("shop", ShopBlock::new);
		SELL_BLOCK = register("sell_block", SellBlockBlock::new);
		PLAYER_SHOP = register("player_shop", PlayerShopBlock::new);
		TICKET_BLOCK = register("ticket_block", TicketBlockBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}