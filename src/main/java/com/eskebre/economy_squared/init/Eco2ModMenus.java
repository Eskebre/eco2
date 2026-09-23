/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.Map;

import com.eskebre.economy_squared.world.inventory.*;
import com.eskebre.economy_squared.network.MenuStateUpdateMessage;
import com.eskebre.economy_squared.Eco2Mod;

public class Eco2ModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, Eco2Mod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<ATMGUIMenu>> ATMGUI = REGISTRY.register("atmgui", () -> IMenuTypeExtension.create(ATMGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ShopGUIMenu>> SHOP_GUI = REGISTRY.register("shop_gui", () -> IMenuTypeExtension.create(ShopGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ShopConfigureGUIMenu>> SHOP_CONFIGURE_GUI = REGISTRY.register("shop_configure_gui", () -> IMenuTypeExtension.create(ShopConfigureGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SellBlockGUIMenu>> SELL_BLOCK_GUI = REGISTRY.register("sell_block_gui", () -> IMenuTypeExtension.create(SellBlockGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TicketBlockGuiMenu>> TICKET_BLOCK_GUI = REGISTRY.register("ticket_block_gui", () -> IMenuTypeExtension.create(TicketBlockGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TicketBlockConfigureGuiMenu>> TICKET_BLOCK_CONFIGURE_GUI = REGISTRY.register("ticket_block_configure_gui", () -> IMenuTypeExtension.create(TicketBlockConfigureGuiMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide()) {
				if (Minecraft.getInstance().screen instanceof Eco2ModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}