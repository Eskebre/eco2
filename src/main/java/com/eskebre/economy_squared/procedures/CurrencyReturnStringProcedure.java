package com.eskebre.economy_squared.procedures;

import net.minecraft.world.entity.Entity;

import com.eskebre.economy_squared.network.Eco2ModVariables;

public class CurrencyReturnStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "$" + new java.text.DecimalFormat("##.##").format(entity.getData(Eco2ModVariables.PLAYER_VARIABLES).currency);
	}
}