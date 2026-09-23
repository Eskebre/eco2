package com.eskebre.economy_squared.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import com.mojang.blaze3d.platform.InputConstants;

import com.eskebre.economy_squared.world.inventory.TicketBlockGuiMenu;
import com.eskebre.economy_squared.procedures.ShopGUIGetPriceProcedure;
import com.eskebre.economy_squared.procedures.CurrencyReturnStringProcedure;
import com.eskebre.economy_squared.network.TicketBlockGuiButtonMessage;
import com.eskebre.economy_squared.init.Eco2ModScreens;

public class TicketBlockGuiScreen extends AbstractContainerScreen<TicketBlockGuiMenu> implements Eco2ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_buy;
	private static final Identifier BACKGROUND = Identifier.parse("eco2:textures/screens/ticket_block_gui.png");

	public TicketBlockGuiScreen(TicketBlockGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 102);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = InputConstants.getKey(event).getValue();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_gui.label_ticket_block"), 6, 4, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_gui.label_cost"), 60, 38, -12829636, false);
		guiGraphics.text(this.font, ShopGUIGetPriceProcedure.execute(world, x, y, z), 87, 38, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_gui.label_amount1"), 6, 16, -12829636, false);
		guiGraphics.text(this.font, CurrencyReturnStringProcedure.execute(entity), 42, 16, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_buy = Button.builder(Component.translatable("gui.eco2.ticket_block_gui.button_buy"), e -> {
			int x = TicketBlockGuiScreen.this.x;
			int y = TicketBlockGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new TicketBlockGuiButtonMessage(0, x, y, z));
				TicketBlockGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 74, 162, 20).build();
		this.addRenderableWidget(button_buy);
	}
}