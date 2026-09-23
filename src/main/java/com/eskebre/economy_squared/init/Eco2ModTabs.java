/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.eskebre.economy_squared.Eco2Mod;

public class Eco2ModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Eco2Mod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ECO_2_TAB = REGISTRY.register("eco_2_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.eco2.eco_2_tab")).icon(() -> new ItemStack(Eco2ModBlocks.ATM.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Eco2ModItems.CURRENCY_1.get());
				tabData.accept(Eco2ModItems.CURRENCY_4.get());
				tabData.accept(Eco2ModItems.CURRENCY_16.get());
				tabData.accept(Eco2ModItems.CURRENCY_64.get());
				tabData.accept(Eco2ModBlocks.ATM.get().asItem());
				tabData.accept(Eco2ModBlocks.SHOP.get().asItem());
				tabData.accept(Eco2ModItems.SHOP_CONFIGURATOR.get());
				tabData.accept(Eco2ModBlocks.SELL_BLOCK.get().asItem());
				tabData.accept(Eco2ModItems.WALLET.get());
				tabData.accept(Eco2ModBlocks.TICKET_BLOCK.get().asItem());
			}).build());
}