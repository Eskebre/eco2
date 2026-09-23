package com.eskebre.economy_squared.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class Currency64Item extends Item {
	public Currency64Item(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).stacksTo(99));
	}
}