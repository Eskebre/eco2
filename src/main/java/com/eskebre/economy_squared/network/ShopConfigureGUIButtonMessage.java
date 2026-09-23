package com.eskebre.economy_squared.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import com.eskebre.economy_squared.procedures.ShopConfigureGuiApplyProcedure;
import com.eskebre.economy_squared.procedures.ResetTotalProcedure;
import com.eskebre.economy_squared.Eco2Mod;

@EventBusSubscriber
public record ShopConfigureGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<ShopConfigureGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Eco2Mod.MODID, "shop_configure_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ShopConfigureGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ShopConfigureGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new ShopConfigureGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<ShopConfigureGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final ShopConfigureGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (buttonID == 0) {

			ShopConfigureGuiApplyProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			ResetTotalProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Eco2Mod.addNetworkMessage(ShopConfigureGUIButtonMessage.TYPE, ShopConfigureGUIButtonMessage.STREAM_CODEC, ShopConfigureGUIButtonMessage::handleData);
	}
}