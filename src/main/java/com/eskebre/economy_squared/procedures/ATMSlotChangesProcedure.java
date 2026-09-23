package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import com.eskebre.economy_squared.network.Eco2ModVariables;
import com.eskebre.economy_squared.init.Eco2ModMenus;
import com.eskebre.economy_squared.init.Eco2ModItems;

public class ATMSlotChangesProcedure {
	public static InteractionResult execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		ItemStack item = ItemStack.EMPTY;
		double multiplier = 0;
		double amount = 0;
		item = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).copy();
		if (item.getItem() == Eco2ModItems.CURRENCY_1.get()) {
			multiplier = 1;
		} else if (item.getItem() == Eco2ModItems.CURRENCY_4.get()) {
			multiplier = 4;
		} else if (item.getItem() == Eco2ModItems.CURRENCY_16.get()) {
			multiplier = 16;
		} else if (item.getItem() == Eco2ModItems.CURRENCY_64.get()) {
			multiplier = 64;
		} else {
			return InteractionResult.PASS;
		}
		amount = getAmountInGUISlot(entity, 0) * multiplier;
		if (entity instanceof Player _player && _player.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu) {
			ItemStack _setstack7 = new ItemStack(Blocks.AIR).copy();
			_setstack7.setCount(1);
			_menu.getSlots().get(0).set(_setstack7);
			_player.containerMenu.broadcastChanges();
		}
		if (!world.isClientSide()) {
			{
				Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
				_vars.currency = entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency + amount;
				_vars.markSyncDirty();
			}
		}
		return InteractionResult.SUCCESS;
	}

	private static int getAmountInGUISlot(Entity entity, int sltid) {
		if (entity instanceof Player player && player.containerMenu instanceof Eco2ModMenus.MenuAccessor menuAccessor) {
			ItemStack stack = menuAccessor.getSlots().get(sltid).getItem();
			if (stack != null)
				return stack.getCount();
		}
		return 0;
	}
}