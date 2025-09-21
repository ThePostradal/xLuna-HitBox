package net.fabricmc.example.mixin;

import net.fabricmc.example.conf.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class EspMixin {
    
    @Inject(at = @At("HEAD"), method = "renderEntity", cancellable = true)
    private void renderEntity(Entity entity, double tf, double tf1, double tf2, float tf3, MatrixStack matrixStack,
                              VertexConsumerProvider vertexConsumerProvider, CallbackInfo ci) {
        if (config.isEspEnabled() && entity instanceof PlayerEntity) {
            if (entity != MinecraftClient.getInstance().player) {
                // Add glowing effect to other players
                entity.setGlowing(true);
            }
        } else if (entity instanceof PlayerEntity && entity != MinecraftClient.getInstance().player) {
            // Remove glowing effect when ESP is disabled
            entity.setGlowing(false);
        }
    }
}
