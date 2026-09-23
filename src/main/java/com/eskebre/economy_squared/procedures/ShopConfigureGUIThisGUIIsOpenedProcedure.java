package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import com.eskebre.economy_squared.init.Eco2ModMenus;

public class ShopConfigureGUIThisGUIIsOpenedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && _player.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu)
			_menu.sendMenuStateUpdate(_player, 0, "cost", (new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "cost"))), true);
		if (entity instanceof Player _player && _player.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu)
			_menu.sendMenuStateUpdate(_player, 0, "max_sold", (new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "maxSold"))), true);
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}