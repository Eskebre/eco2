package com.eskebre.economy_squared.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class Currency4Item extends Item {
	public Currency4Item(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON));
	}
}