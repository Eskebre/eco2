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

import com.eskebre.economy_squared.procedures.ATMWithdraw64Procedure;
import com.eskebre.economy_squared.procedures.ATMWithdraw4Procedure;
import com.eskebre.economy_squared.procedures.ATMWithdraw1Procedure;
import com.eskebre.economy_squared.procedures.ATMWithdraw16Procedure;
import com.eskebre.economy_squared.Eco2Mod;

@EventBusSubscriber
public record ATMGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<ATMGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Eco2Mod.MODID, "atmgui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ATMGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ATMGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new ATMGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<ATMGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final ATMGUIButtonMessage message, final IPayloadContext context) {
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

			ATMWithdraw1Procedure.execute(entity);
		}
		if (buttonID == 1) {

			ATMWithdraw4Procedure.execute(entity);
		}
		if (buttonID == 2) {

			ATMWithdraw16Procedure.execute(entity);
		}
		if (buttonID == 3) {

			ATMWithdraw64Procedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Eco2Mod.addNetworkMessage(ATMGUIButtonMessage.TYPE, ATMGUIButtonMessage.STREAM_CODEC, ATMGUIButtonMessage::handleData);
	}
}