package xyz.illuminat3;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoNFClient implements ClientModInitializer {

	private static final Logger LOGGER = LogManager.getLogger();

	private static long lastMessageTime = 0;
	private static final long RATE_LIMIT_INTERVAL = 15_000;

	@Override
	public void onInitializeClient() {
		LOGGER.info("AutoNFClient initialized.");
	}

	public static void handleChatMessage(String message) {
		LOGGER.info("Received message: {}", message);
		if (message.contains("FISHING ▶")) {
			String username = getUsername();

			if (username == null) {
				LOGGER.warn("Player username is not available. Skipping username check.");
			} else {
				if (message.contains(username)) {
					LOGGER.info("Message contains username. Exiting handleChatMessage.");
					return;
				}
			}

			LOGGER.info("Fishing Message Detected!");

			String response = null;
			if (message.contains("Mythical")) {
				response = "gg";
			} else if (message.contains("Platinum")) {
				response = "nf";
			}

			if (response != null) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastMessageTime >= RATE_LIMIT_INTERVAL) {
					sendChatMessage(response);
					lastMessageTime = currentTime;
					LOGGER.info("Sent message: {}", response);
				} else {
					long timeLeft = RATE_LIMIT_INTERVAL - (currentTime - lastMessageTime);
					LOGGER.info("Rate limit active. Message not sent. Time left: {} ms", timeLeft);
				}
			}
		}
	}

	private static void sendChatMessage(String message) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player != null) {
			client.player.networkHandler.sendChatMessage(message);
		} else {
			LOGGER.warn("Cannot send message. Player instance is null.");
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
