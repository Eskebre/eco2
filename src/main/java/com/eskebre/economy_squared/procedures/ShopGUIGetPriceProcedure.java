package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class ShopGUIGetPriceProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		return "$" + new java.text.DecimalFormat("##").format(Math.floor(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "cost")));
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}