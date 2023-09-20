package de.dafuqs.revelationary.mixin;

import de.dafuqs.revelationary.Revelationary;
import de.dafuqs.revelationary.config.RevelationaryConfig;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class Plugin implements IMixinConfigPlugin {
	public Plugin() {
		Revelationary.isRubidiumLoaded = FMLLoader.getLoadingModList().getModFileById("rubidium") != null;
	}

	@Override
	public void onLoad(String mixinPackage) {
	}
	
	@Contract(pure = true)
	@Override
	public @Nullable String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, @NotNull String mixinClassName) {
		if(mixinClassName.contains("BlockUnbreakingMixin")) {
			return RevelationaryConfig.get().PreventMiningOfUnrevealedBlocks;
		}
		return true;
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}
	
	@Contract(pure = true)
	@Override
	public @Unmodifiable List<String> getMixins() {
		return List.of();
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
	
}