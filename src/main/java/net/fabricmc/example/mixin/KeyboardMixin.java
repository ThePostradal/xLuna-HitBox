package net.fabricmc.example.mixin;

import net.fabricmc.example.conf.config;
import net.fabricmc.example.gui.HitboxGuiScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKey(long windowPointer, int key, int code, int action, int mods, CallbackInfo callbackInfo) {
        // k = 75, i = 73, p = 80, right alt = 346

        if (action == 1) {
            if (key == 85) { // U key
                double newSize = config.getSize() + 0.05;
                config.setSize(newSize);
            } 
            if (key == 73) { // I key
                double newSize = config.getSize() - 0.05;
                config.setSize(newSize);
            } 
            if (key == 80) { // P key
                config.setPanic(true);
                clearChat();
                sendMessage("§c[Luna] §fPanic mode activated!");
            } 
            if (key == 346) { // Right Alt key (corrected)
                if (MinecraftClient.getInstance().currentScreen == null) {
                    MinecraftClient.getInstance().openScreen(new HitboxGuiScreen());
                }
            }
        }
    }
    
    private void sendMessage(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText(message), false);
        }
    }
    
    private void clearChat() {
        if (MinecraftClient.getInstance().player != null) {
            // Clear chat by sending empty messages
            for (int i = 0; i < 50; i++) {
                MinecraftClient.getInstance().player.sendMessage(new LiteralText(""), false);
            }
        }
    }
}
