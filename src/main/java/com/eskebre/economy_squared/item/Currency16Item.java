package com.eskebre.economy_squared.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class Currency16Item extends Item {
	public Currency16Item(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}
}