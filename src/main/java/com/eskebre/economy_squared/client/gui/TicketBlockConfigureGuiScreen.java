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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import com.mojang.blaze3d.platform.InputConstants;

import com.eskebre.economy_squared.world.inventory.TicketBlockConfigureGuiMenu;
import com.eskebre.economy_squared.procedures.StrGetMoneyProcedure;
import com.eskebre.economy_squared.network.TicketBlockConfigureGuiButtonMessage;
import com.eskebre.economy_squared.init.Eco2ModScreens;

public class TicketBlockConfigureGuiScreen extends AbstractContainerScreen<TicketBlockConfigureGuiMenu> implements Eco2ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox cost;
	private Button button_claim;
	private Button button_test;
	private Button button_pickup_block;
	private static final Identifier BACKGROUND = Identifier.parse("eco2:textures/screens/ticket_block_configure_gui.png");

	public TicketBlockConfigureGuiScreen(TicketBlockConfigureGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 166);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("cost"))
				cost.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		cost.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (cost.isFocused())
			return cost.keyPressed(event);
		return super.keyPressed(event);
	}

	@Override
	public void resize(int width, int height) {
		String costValue = cost.getValue();
		super.resize(width, height);
		cost.setValue(costValue);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_configure_gui.label_ticket_block_configure"), 6, 7, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_configure_gui.label_cost"), 6, 34, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.ticket_block_configure_gui.label_money"), 96, 34, -12829636, false);
		guiGraphics.text(this.font, StrGetMoneyProcedure.execute(world, x, y, z), 132, 34, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		cost = new EditBox(this.font, this.leftPos + 33, this.topPos + 34, 54, 20, Component.translatable("gui.eco2.ticket_block_configure_gui.cost"));
		cost.setMaxLength(8192);
		cost.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "cost", content, false);
		});
		cost.setHint(Component.translatable("gui.eco2.ticket_block_configure_gui.cost"));
		this.addWidget(this.cost);
		button_claim = Button.builder(Component.translatable("gui.eco2.ticket_block_configure_gui.button_claim"), e -> {
			int x = TicketBlockConfigureGuiScreen.this.x;
			int y = TicketBlockConfigureGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new TicketBlockConfigureGuiButtonMessage(0, x, y, z));
				TicketBlockConfigureGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 114, this.topPos + 61, 50, 20).build();
		this.addRenderableWidget(button_claim);
		button_test = Button.builder(Component.translatable("gui.eco2.ticket_block_configure_gui.button_test"), e -> {
			int x = TicketBlockConfigureGuiScreen.this.x;
			int y = TicketBlockConfigureGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new TicketBlockConfigureGuiButtonMessage(1, x, y, z));
				TicketBlockConfigureGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 33, this.topPos + 61, 45, 20).build();
		this.addRenderableWidget(button_test);
		button_pickup_block = Button.builder(Component.translatable("gui.eco2.ticket_block_configure_gui.button_pickup_block"), e -> {
			int x = TicketBlockConfigureGuiScreen.this.x;
			int y = TicketBlockConfigureGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new TicketBlockConfigureGuiButtonMessage(2, x, y, z));
				TicketBlockConfigureGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 42, this.topPos + 88, 85, 20).build();
		this.addRenderableWidget(button_pickup_block);
	}
}