package com.eskebre.economy_squared.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import com.eskebre.economy_squared.network.Eco2ModVariables;
import com.eskebre.economy_squared.init.Eco2ModMenus;
import com.eskebre.economy_squared.init.Eco2ModItems;

public class ATMWithdraw1Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double amount = 0;
		amount = Math.min(entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency, (entity instanceof Player _entity0 && _entity0.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(2, "amount", 0.0) : 0.0);
		if (amount > 0) {
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(Eco2ModItems.CURRENCY_1.get()).copy();
				_setstack.setCount((int) amount);
				_player.getInventory().placeItemBackInInventory(_setstack);
			}
			{
				Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
				_vars.currency = entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency - amount;
				_vars.markSyncDirty();
			}
		}
	}
}