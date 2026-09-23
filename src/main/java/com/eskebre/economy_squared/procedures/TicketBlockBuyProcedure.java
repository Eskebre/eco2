package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import com.eskebre.economy_squared.network.Eco2ModVariables;

public class TicketBlockBuyProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double cost = 0;
		double max_sold = 0;
		cost = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "cost");
		max_sold = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "maxSold");
		if (0 != max_sold) {
			if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold") >= max_sold) {
				if (entity instanceof ServerPlayer _player)
					_player.sendSystemMessage(Component.literal("Sold Out"), false);
				return InteractionResult.PASS;
			}
		}
		if (cost < 0) {
			return InteractionResult.PASS;
		}
		if (entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency >= cost) {
			TicketBlockPoweredProcedure.execute(world, x, y, z);
			{
				Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
				_vars.currency = entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency - cost;
				_vars.markSyncDirty();
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("totalSold", (1 + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold")));
					_blockEntity.getPersistentData().putDouble("money", (cost + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "money")));
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			world.getBlockEntity(new net.minecraft.core.BlockPos((int) x, (int) y, (int) z)).setChanged();
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
		return InteractionResult.SUCCESS;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}