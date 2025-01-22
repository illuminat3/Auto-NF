package xyz.illuminat3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class AutoNFClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
			String messageContent = message.getString();

			if (messageContent.contains("FISHING ▶")) {
				if (messageContent.contains("Mythical")) {
					sendChatMessage("gg");
				} else if (messageContent.contains("Platinum")) {
					sendChatMessage("nf");
				}
			}
			return true;
		});
	}

	// FISHING ▶ Mythical
	// FISHING ▶ Platinum

	private void sendChatMessage(String message) {
		var client = net.minecraft.client.MinecraftClient.getInstance();
		if (client.player != null) {
			client.player.networkHandler.sendChatMessage(message);
		}
	}
}
