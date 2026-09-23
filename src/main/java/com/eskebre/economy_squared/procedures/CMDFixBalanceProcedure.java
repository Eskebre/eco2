package com.eskebre.economy_squared.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import com.eskebre.economy_squared.network.Eco2ModVariables;

public class CMDFixBalanceProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Eco2ModVariables.PlayerVariables _vars = entity.getData(Eco2ModVariables.PLAYER_VARIABLES);
			_vars.currency = Math.floor(entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency);
			_vars.markSyncDirty();
		}
		if (entity instanceof ServerPlayer _player)
			_player.sendSystemMessage(Component.literal("Fixed Balance"), false);
	}
}