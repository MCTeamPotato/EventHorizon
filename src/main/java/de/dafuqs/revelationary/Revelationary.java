package de.dafuqs.revelationary;

import de.dafuqs.revelationary.api.advancements.AdvancementCriteria;
import de.dafuqs.revelationary.networking.RevelationaryS2CPacketReceivers;
import de.dafuqs.revelationary.networking.RevelationaryS2CPacketSenders;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Revelationary.MOD_ID)
public class Revelationary {

    public static final String MOD_ID = "eventhorizon";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean isRubidiumLoaded;


    public Revelationary() {
        AdvancementCriteria.register();
        MinecraftForge.EVENT_BUS.register(this);
        RevelationaryS2CPacketReceivers.register();
    }

    public static void logInfo(String message) {
        LOGGER.info("[EventHorizon] " + message);
    }

    public static void logWarning(String message) {
        LOGGER.warn("[EventHorizon] " + message);
    }

    public static void logError(String message) {
        LOGGER.error("[EventHorizon] " + message);
    }

    @SubscribeEvent
    public void onInitialize(FMLCommonSetupEvent event) {
        logInfo("Starting Common Startup");
        if (FMLLoader.getLoadingModList().getModFileById("rubidium") != null) {
            logWarning("Rubidium detected. Chunk rebuilding will be done in cursed mode.");
        }

        logInfo("Common startup completed!");
    }

    @SubscribeEvent
    public void onAddReloadListener(@NotNull AddReloadListenerEvent event) {
        event.addListener(RevelationDataLoader.INSTANCE);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        RevelationRegistry.addRevelationAwares();
    }

    @SubscribeEvent
    public void onCmdRegister(@NotNull RegisterCommandsEvent event) {
        Commands.register(event.getDispatcher());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerLogIn(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity serverPlayerEntity) RevelationaryS2CPacketSenders.sendRevelations(serverPlayerEntity);
    }
}
