/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.eskebre.economy_squared.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import com.eskebre.economy_squared.client.gui.*;

@EventBusSubscriber(Dist.CLIENT)
public class Eco2ModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(Eco2ModMenus.ATMGUI.get(), ATMGUIScreen::new);
		event.register(Eco2ModMenus.SHOP_GUI.get(), ShopGUIScreen::new);
		event.register(Eco2ModMenus.SHOP_CONFIGURE_GUI.get(), ShopConfigureGUIScreen::new);
		event.register(Eco2ModMenus.SELL_BLOCK_GUI.get(), SellBlockGUIScreen::new);
		event.register(Eco2ModMenus.TICKET_BLOCK_GUI.get(), TicketBlockGuiScreen::new);
		event.register(Eco2ModMenus.TICKET_BLOCK_CONFIGURE_GUI.get(), TicketBlockConfigureGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}