package de.dafuqs.revelationary.networking;

import de.dafuqs.revelationary.RevelationRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class RevelationaryS2CPacketSenders {

	public static void sendRevelations(ServerPlayerEntity player) {
		PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
		RevelationRegistry.write(packetByteBuf);
		RevelationaryS2CPacketReceivers.sendToClient(player, packetByteBuf, RevelationaryPackets.REVELATION_SYNC);
		//player.networkHandler.sendPacket(new CustomPayloadS2CPacket(RevelationaryPackets.REVELATION_SYNC, packetByteBuf));
	}
}
