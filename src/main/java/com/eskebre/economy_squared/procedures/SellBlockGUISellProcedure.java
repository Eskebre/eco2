package com.eskebre.economy_squared.procedures;

import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
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
import com.eskebre.economy_squared.init.Eco2ModMenus;

public class SellBlockGUISellProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double item = 0;
		double max_sold = 0;
		ItemStack player_item = ItemStack.EMPTY;
		ItemStack shop_item = ItemStack.EMPTY;
		boolean nbt_match = false;
		shop_item = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).copy();
		if (shop_item.getItem() == Blocks.AIR.asItem()) {
			return InteractionResult.PASS;
		}
		item = 0;
		nbt_match = getBlockNBTLogic(world, BlockPos.containing(x, y, z), "nbtMatch");
		max_sold = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "maxSold");
		if (0 != max_sold) {
			if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "totalSold") >= max_sold) {
				if (entity instanceof ServerPlayer _player)
					_player.sendSystemMessage(Component.literal("Sold Out"), false);
				return InteractionResult.PASS;
			}
		}
		if (entity.getCapability(Capabilities.Item.ENTITY, null) instanceof ResourceHandler<ItemResource> _resourceHandlerIter) {
			for (int _idx = 0; _idx < _resourceHandlerIter.size(); _idx++) {
				ItemStack itemstackiterator = ItemUtil.getStack(_resourceHandlerIter, _idx);
				player_item = itemstackiterator.copy();
				if (shop_item.getItem() == player_item.getItem()) {
					item = item + itemstackiterator.getCount();
				}
			}
		}
		if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu13 ? _menu13.getSlots().get(0).getItem() : ItemStack.EMPTY).getCount() <= item) {
			{
				Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
				_vars.currency = entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "cost");
				_vars.markSyncDirty();
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu16 ? _menu16.getSlots().get(0).getItem() : ItemStack.EMPTY);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(),
						(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Eco2ModMenus.MenuAccessor _menu17 ? _menu17.getSlots().get(0).getItem() : ItemStack.EMPTY).getCount(), _player.inventoryMenu.getCraftSlots());
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

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}