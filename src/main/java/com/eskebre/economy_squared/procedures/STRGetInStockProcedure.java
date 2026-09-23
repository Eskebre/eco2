package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class STRGetInStockProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		double AmountLeft = 0;
		AmountLeft = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "maxSold") - getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold");
		if (AmountLeft <= 0) {
			return "Sold Out";
		}
		return new java.text.DecimalFormat("##.##").format(AmountLeft);
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}