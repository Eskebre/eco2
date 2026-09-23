package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

public class TicketBlockBlockDestroyedByPlayerProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if (("").equals(getBlockNBTString(world, BlockPos.containing(x, y, z), "owner")) || (entity.getStringUUID()).equals(getBlockNBTString(world, BlockPos.containing(x, y, z), "owner"))) {
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getStringOr(tag, "");
		return "";
	}
}