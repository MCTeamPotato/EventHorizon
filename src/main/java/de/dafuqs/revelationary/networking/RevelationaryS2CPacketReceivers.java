package de.dafuqs.revelationary.networking;

import de.dafuqs.revelationary.ClientRevelationHolder;
import de.dafuqs.revelationary.RevelationRegistry;
import de.dafuqs.revelationary.Revelationary;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class RevelationaryS2CPacketReceivers {

	private static final String PROTOCOL_VERSION = "1.0";

	private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(RevelationaryPackets.REVELATION_SYNC, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void sendToClient(ServerPlayerEntity player, PacketByteBuf buf, Identifier identifier) {
		INSTANCE.sendTo(new Packet(identifier, buf), player.networkHandler.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void register() {
		INSTANCE.registerMessage(1, Packet.class,
				(msg, buf) -> {
					buf.writeIdentifier(msg.id);
					buf.writeBytes(msg.buf);
				}, buf -> new Packet(buf.readIdentifier(), buf),
				(msg, ctx) -> {
					NetworkEvent.Context context = ctx.get();
					PacketByteBuf buf = msg.buf;
					try {
						RevelationRegistry.fromPacket(buf);
					} catch (Exception e) {
						Revelationary.logError("Error fetching results from sync packet");
						Revelationary.logError(e.toString());
					}
					ClientRevelationHolder.cloakAll();
					context.setPacketHandled(true);
				}
		);
	}

	private static class Packet {
		public Identifier id;
		public PacketByteBuf buf;

		public Packet(Identifier id, PacketByteBuf buf) {
			this.id = id;
			this.buf = buf;
		}
	}
}