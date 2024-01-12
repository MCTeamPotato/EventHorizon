package de.dafuqs.revelationary.networking;

import de.dafuqs.revelationary.ClientRevelationHolder;
import de.dafuqs.revelationary.RevelationRegistry;
import de.dafuqs.revelationary.Revelationary;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.event.EventNetworkChannel;

public class RevelationaryS2CPacketReceivers {
	private static final String VERSION = "1.0";

	private static final EventNetworkChannel REVELATION_SYNC_CHANNEL = NetworkRegistry.newEventChannel(RevelationaryPackets.REVELATION_SYNC, () -> VERSION, c -> true, c -> true);

	public static void register() {
		REVELATION_SYNC_CHANNEL.addListener(networkEvent -> {
			PacketByteBuf buf = networkEvent.getPayload();
			try {
				RevelationRegistry.fromPacket(buf);
			} catch (Exception e) {
				Revelationary.logError("Error fetching results from sync packet");
				Revelationary.logError(e.toString());
			}
			ClientRevelationHolder.cloakAll();
		});
	}
}