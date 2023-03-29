package com.aliahx.mixtape.mixin;

import com.aliahx.mixtape.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    @Shadow @Final private GameOptions settings;
    private static final Identifier MIXTAPE_ICON_TEXTURE = new Identifier("mixtape:textures/gui/mixtape_button.png");

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initMixin(CallbackInfo ci) {
        this.addDrawableChild(new TexturedButtonWidget(this.width / 2 + 159, this.height / 6 + 42, 20, 20, 0, 0, 20, MIXTAPE_ICON_TEXTURE, 20, 40, (button) -> {
            MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(ModConfig.class, this).get());
        }));
    }
}
