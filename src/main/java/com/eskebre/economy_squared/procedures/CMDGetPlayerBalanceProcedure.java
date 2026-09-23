package com.eskebre.economy_squared.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

import com.eskebre.economy_squared.network.Eco2ModVariables;

public class CMDGetPlayerBalanceProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		Entity player = null;
		player = commandParameterEntity(arguments, "name");
		if (player == null) {
			player = entity;
		}
		if (player == null) {
			if (entity instanceof ServerPlayer _player)
				_player.sendSystemMessage(Component.literal("Player is NULL"), false);
		} else {
			if (entity instanceof ServerPlayer _player)
				_player.sendSystemMessage(Component.literal((player.getDisplayName().getString() + " has a balance of $" + new java.text.DecimalFormat("##.##").format(player.getData(Eco2ModVariables.PLAYER_VARIABLES).currency))), false);
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}