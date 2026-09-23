package com.eskebre.economy_squared.procedures;

import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import com.eskebre.economy_squared.network.Eco2ModVariables;

public class ShopGUIBuyProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double cost = 0;
		double max_sold = 0;
		ItemStack item = ItemStack.EMPTY;
		cost = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "cost");
		item = (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).copy();
		max_sold = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "maxSold");
		if (item.getItem() == Blocks.AIR.asItem()) {
			return InteractionResult.PASS;
		}
		if (0 != max_sold) {
			if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold") >= max_sold) {
				if (entity instanceof ServerPlayer _player)
					_player.sendSystemMessage(Component.literal("Sold Out"), false);
				return InteractionResult.PASS;
			}
		}
		if (entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency >= cost) {
			{
				Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
				_vars.currency = entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency - cost;
				_vars.markSyncDirty();
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = item.copy();
				_setstack.setCount(item.getCount());
				_player.getInventory().placeItemBackInInventory(_setstack);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("totalSold", (1 + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold")));
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			world.getBlockEntity(new net.minecraft.core.BlockPos((int) x, (int) y, (int) z)).setChanged();
		}
		return InteractionResult.SUCCESS;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			ResourceHandler<ItemResource> itemHandler = ext.getCapability(Capabilities.Item.BLOCK, pos, null);
			if (itemHandler != null)
				return ItemUtil.getStack(itemHandler, slot);
		}
		return ItemStack.EMPTY;
	}
}