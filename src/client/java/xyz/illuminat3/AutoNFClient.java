package xyz.illuminat3;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoNFClient implements ClientModInitializer {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitializeClient() {
		LOGGER.info("AutoNFClient initialized.");
	}

	public static void handleChatMessage(String message) {
		String username = getUsername();

		if (username == null) {
			LOGGER.warn("Player username is not available. Skipping username check.");
		} else {
			if (message.contains(username)) {
				LOGGER.info("Message contains username. Exiting handleChatMessage.");
				return;
			}
		}

        LOGGER.info("Received message: {}", message);
		if (message.contains("FISHING ▶")) {
			LOGGER.info("Fishing Message Detected!");
			if (message.contains("Mythical")) {
				sendChatMessage("gg");
			} else if (message.contains("Platinum")) {
				sendChatMessage("nf");
			}
		}
	}

	private static void sendChatMessage(String message) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player != null) {
			client.player.networkHandler.sendChatMessage(message);
		}
	}

	private static String getUsername() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player != null) {
			return client.player.getName().getString();
		}
		return null;
	}
}
