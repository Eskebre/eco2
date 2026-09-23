package com.eskebre.economy_squared.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

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

import com.eskebre.economy_squared.world.inventory.ATMGUIMenu;
import com.eskebre.economy_squared.procedures.CurrencyReturnStringProcedure;
import com.eskebre.economy_squared.network.ATMGUIButtonMessage;
import com.eskebre.economy_squared.init.Eco2ModScreens;

public class ATMGUIScreen extends AbstractContainerScreen<ATMGUIMenu> implements Eco2ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_1x;
	private Button button_4x;
	private Button button_16x;
	private Button button_64x;
	private ExtendedSlider amount;
	private static final Identifier BACKGROUND = Identifier.parse("eco2:textures/screens/atmgui.png");

	public ATMGUIScreen(ATMGUIMenu container, Inventory inventory, Component text) {
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
		if (elementType == 2 && elementState instanceof Number n) {
			if (name.equals("amount"))
				amount.setValue(n.doubleValue());
		}
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
		guiGraphics.text(this.font, Component.translatable("gui.eco2.atmgui.label_atm"), 6, 7, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.atmgui.label_amount"), 6, 16, -12829636, false);
		guiGraphics.text(this.font, CurrencyReturnStringProcedure.execute(entity), 42, 16, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.atmgui.label_withdraw"), 6, 34, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.atmgui.label_deposit"), 123, 34, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_1x = Button.builder(Component.translatable("gui.eco2.atmgui.button_1x"), e -> {
			int x = ATMGUIScreen.this.x;
			int y = ATMGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ATMGUIButtonMessage(0, x, y, z));
				ATMGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 43, 27, 20).build();
		this.addRenderableWidget(button_1x);
		button_4x = Button.builder(Component.translatable("gui.eco2.atmgui.button_4x"), e -> {
			int x = ATMGUIScreen.this.x;
			int y = ATMGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ATMGUIButtonMessage(1, x, y, z));
				ATMGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 33, this.topPos + 43, 27, 20).build();
		this.addRenderableWidget(button_4x);
		button_16x = Button.builder(Component.translatable("gui.eco2.atmgui.button_16x"), e -> {
			int x = ATMGUIScreen.this.x;
			int y = ATMGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ATMGUIButtonMessage(2, x, y, z));
				ATMGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 60, this.topPos + 43, 27, 20).build();
		this.addRenderableWidget(button_16x);
		button_64x = Button.builder(Component.translatable("gui.eco2.atmgui.button_64x"), e -> {
			int x = ATMGUIScreen.this.x;
			int y = ATMGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ATMGUIButtonMessage(3, x, y, z));
				ATMGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 87, this.topPos + 43, 27, 20).build();
		this.addRenderableWidget(button_64x);
		amount = new ExtendedSlider(this.leftPos + 6, this.topPos + 61, 108, 20, Component.translatable("gui.eco2.atmgui.amount_prefix"), Component.translatable("gui.eco2.atmgui.amount_suffix"), 1, 64, 1, 1, 0, true) {
			@Override
			protected void applyValue() {
				if (!menuStateUpdateActive)
					menu.sendMenuStateUpdate(entity, 2, "amount", this.getValue(), false);
			}
		};
		this.addRenderableWidget(amount);
		if (!menuStateUpdateActive)
			menu.sendMenuStateUpdate(entity, 2, "amount", amount.getValue(), false);
	}
}