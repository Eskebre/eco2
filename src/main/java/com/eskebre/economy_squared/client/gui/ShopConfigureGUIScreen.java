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

import com.eskebre.economy_squared.world.inventory.ShopConfigureGUIMenu;
import com.eskebre.economy_squared.procedures.STRGetTotalSoldProcedure;
import com.eskebre.economy_squared.network.ShopConfigureGUIButtonMessage;
import com.eskebre.economy_squared.init.Eco2ModScreens;

public class ShopConfigureGUIScreen extends AbstractContainerScreen<ShopConfigureGUIMenu> implements Eco2ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox cost;
	private EditBox max_sold;
	private Button button_apply;
	private Button button_reset_total;
	private static final Identifier BACKGROUND = Identifier.parse("eco2:textures/screens/shop_configure_gui.png");

	public ShopConfigureGUIScreen(ShopConfigureGUIMenu container, Inventory inventory, Component text) {
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
			else if (name.equals("max_sold"))
				max_sold.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		cost.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		max_sold.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (max_sold.isFocused())
			return max_sold.keyPressed(event);
		return super.keyPressed(event);
	}

	@Override
	public void resize(int width, int height) {
		String costValue = cost.getValue();
		String max_soldValue = max_sold.getValue();
		super.resize(width, height);
		cost.setValue(costValue);
		max_sold.setValue(max_soldValue);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.eco2.shop_configure_gui.label_cost"), 6, 16, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.shop_configure_gui.label_output_item"), 6, 43, -12829636, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.shop_configure_gui.label_total_sold"), 6, 7, -12829636, false);
		guiGraphics.text(this.font, STRGetTotalSoldProcedure.execute(world, x, y, z), 69, 7, -65536, false);
		guiGraphics.text(this.font, Component.translatable("gui.eco2.shop_configure_gui.label_max_sold"), 84, 16, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		cost = new EditBox(this.font, this.leftPos + 33, this.topPos + 16, 36, 20, Component.translatable("gui.eco2.shop_configure_gui.cost"));
		cost.setMaxLength(8192);
		cost.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "cost", content, false);
		});
		this.addWidget(this.cost);
		max_sold = new EditBox(this.font, this.leftPos + 132, this.topPos + 16, 36, 20, Component.translatable("gui.eco2.shop_configure_gui.max_sold"));
		max_sold.setMaxLength(8192);
		max_sold.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "max_sold", content, false);
		});
		this.addWidget(this.max_sold);
		button_apply = Button.builder(Component.translatable("gui.eco2.shop_configure_gui.button_apply"), e -> {
			int x = ShopConfigureGUIScreen.this.x;
			int y = ShopConfigureGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ShopConfigureGUIButtonMessage(0, x, y, z));
				ShopConfigureGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 114, this.topPos + 61, 50, 20).build();
		this.addRenderableWidget(button_apply);
		button_reset_total = Button.builder(Component.translatable("gui.eco2.shop_configure_gui.button_reset_total"), e -> {
			int x = ShopConfigureGUIScreen.this.x;
			int y = ShopConfigureGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new ShopConfigureGUIButtonMessage(1, x, y, z));
				ShopConfigureGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 61, 80, 20).build();
		this.addRenderableWidget(button_reset_total);
	}
}