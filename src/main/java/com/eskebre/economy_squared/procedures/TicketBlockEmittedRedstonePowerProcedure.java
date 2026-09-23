package com.eskebre.economy_squared.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;

public class TicketBlockEmittedRedstonePowerProcedure {
	public static double execute(BlockState blockstate) {
		if (getPropertyByName(blockstate, "active") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			return 15;
		}
		return 0;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}