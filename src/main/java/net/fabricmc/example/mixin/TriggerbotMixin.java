package net.fabricmc.example.mixin;

import net.fabricmc.example.conf.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class TriggerbotMixin {
    
    private long lastAttackTime = 0;
    
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        if (config.isTriggerbotEnabled()) {
            MinecraftClient client = MinecraftClient.getInstance();
            
            if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) client.crosshairTarget;
                Entity target = entityHit.getEntity();
                
                if (target instanceof PlayerEntity && target != client.player) {
                    long currentTime = System.currentTimeMillis();
                    
                    // Check if enough time has passed since last attack
                    if (currentTime - lastAttackTime >= config.getTriggerbotDelay()) {
                        
                        // Check if we should only attack on critical hits
                        if (config.isCriticalsOnly()) {
                            // Check if we can perform a critical hit (falling or on ground with proper timing)
                            if (client.player.fallDistance > 0 || canCriticalHit()) {
                                attack();
                                lastAttackTime = currentTime;
                            }
                        } else {
                            // Attack normally with delay
                            attack();
                            lastAttackTime = currentTime;
                        }
                    }
                }
            }
        }
    }
    
    private void attack() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.interactionManager != null && client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) client.crosshairTarget;
            client.interactionManager.attackEntity(client.player, entityHit.getEntity());
            client.player.swingHand(Hand.MAIN_HAND);
        }
    }
    
    private boolean canCriticalHit() {
        MinecraftClient client = MinecraftClient.getInstance();
        // Simple critical hit check - player must be falling or on ground with proper timing
        return client.player.fallDistance > 0 || 
               (client.player.isOnGround() && client.player.getVelocity().y < 0);
    }
}
