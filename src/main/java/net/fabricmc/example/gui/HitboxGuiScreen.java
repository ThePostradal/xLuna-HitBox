package net.fabricmc.example.gui;

import net.fabricmc.example.conf.config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class HitboxGuiScreen extends Screen {
    // Left side (Attack)
    private ButtonWidget hitboxToggleButton;
    private ButtonWidget hitboxExpandButton;
    private SliderWidget hitboxSizeSlider;
    private ButtonWidget panicButton;
    
    // Right side (Other)
    private ButtonWidget espToggleButton;
    private ButtonWidget espExpandButton;
    private ButtonWidget visibilityButton;
    private ButtonWidget handItemsButton;
    private ButtonWidget triggerbotButton;
    
    // General
    private ButtonWidget closeButton;
    
    // Expand states
    private boolean hitboxExpanded = false;
    private boolean espExpanded = false;

    public HitboxGuiScreen() {
        super(new LiteralText("Hitbox Settings"));
    }

    @Override
    protected void init() {
        super.init();
        
        int leftX = this.width / 4;
        int rightX = this.width * 3 / 4;
        int startY = this.height / 2 - 60;
        
        // LEFT SIDE - ATTACK FUNCTIONS
        
        // Hitbox toggle with expand button
        this.hitboxToggleButton = new ButtonWidget(leftX - 80, startY, 120, 20,
            new LiteralText("HitBox: " + (config.isHitboxesEnabled() ? "§aON" : "§cOFF")),
            button -> {
                config.setHitboxesEnabled(!config.isHitboxesEnabled());
                config.resetPanic(); // Reset panic when enabling hitboxes
                button.setMessage(new LiteralText("HitBox: " + (config.isHitboxesEnabled() ? "§aON" : "§cOFF")));
                sendMessage("§a[Luna] §fHitBox " + (config.isHitboxesEnabled() ? "§aENABLED" : "§cDISABLED"));
            });
        
        this.hitboxExpandButton = new ButtonWidget(leftX + 50, startY, 20, 20,
            new LiteralText(hitboxExpanded ? "▼" : "▶"),
            button -> {
                hitboxExpanded = !hitboxExpanded;
                button.setMessage(new LiteralText(hitboxExpanded ? "▼" : "▶"));
                this.init(); // Reinitialize to show/hide settings
            });
        
        this.addButton(this.hitboxToggleButton);
        this.addButton(this.hitboxExpandButton);
        
        // Hitbox settings (only visible when expanded)
        if (hitboxExpanded) {
            this.hitboxSizeSlider = new SliderWidget(leftX - 80, startY + 30, 150, 20, 
                new LiteralText("Size: " + String.format("%.2f", config.getSize())), 
                (config.getSize() - 0.1) / 1.9) {
                
                @Override
                protected void updateMessage() {
                    this.setMessage(new LiteralText("Size: " + String.format("%.2f", this.value * 1.9 + 0.1)));
                }
                
                @Override
                protected void applyValue() {
                    double newSize = this.value * 1.9 + 0.1;
                    config.setSize(newSize);
                }
            };
            
            this.panicButton = new ButtonWidget(leftX - 80, startY + 60, 150, 20,
                new LiteralText("§cPanic Mode"),
                button -> {
                    config.setPanic(true);
                    clearChat();
                    sendMessage("§c[Luna] §fPanic mode activated!");
                });
            
            this.addButton(this.hitboxSizeSlider);
            this.addButton(this.panicButton);
        }
        
        // RIGHT SIDE - OTHER FUNCTIONS
        
        // ESP toggle with expand button
        this.espToggleButton = new ButtonWidget(rightX - 80, startY, 120, 20,
            new LiteralText("ESP: " + (config.isEspEnabled() ? "§aON" : "§cOFF")),
            button -> {
                config.setEspEnabled(!config.isEspEnabled());
                button.setMessage(new LiteralText("ESP: " + (config.isEspEnabled() ? "§aON" : "§cOFF")));
                sendMessage("§a[Luna] §fESP " + (config.isEspEnabled() ? "§aENABLED" : "§cDISABLED"));
            });
        
        this.espExpandButton = new ButtonWidget(rightX + 50, startY, 20, 20,
            new LiteralText(espExpanded ? "▼" : "▶"),
            button -> {
                espExpanded = !espExpanded;
                button.setMessage(new LiteralText(espExpanded ? "▼" : "▶"));
                this.init(); // Reinitialize to show/hide settings
            });
        
        this.addButton(this.espToggleButton);
        this.addButton(this.espExpandButton);
        
        // ESP settings (only visible when expanded)
        if (espExpanded) {
            this.visibilityButton = new ButtonWidget(rightX - 80, startY + 30, 150, 20,
                new LiteralText("Visibility: " + (config.isVisibilityEnabled() ? "§aON" : "§cOFF")),
                button -> {
                    config.setVisibilityEnabled(!config.isVisibilityEnabled());
                    button.setMessage(new LiteralText("Visibility: " + (config.isVisibilityEnabled() ? "§aON" : "§cOFF")));
                    sendMessage("§a[Luna] §fVisibility " + (config.isVisibilityEnabled() ? "§aENABLED" : "§cDISABLED"));
                });
            
            this.handItemsButton = new ButtonWidget(rightX - 80, startY + 60, 150, 20,
                new LiteralText("Hand Items: " + (config.isHandItemsEnabled() ? "§aON" : "§cOFF")),
                button -> {
                    config.setHandItemsEnabled(!config.isHandItemsEnabled());
                    button.setMessage(new LiteralText("Hand Items: " + (config.isHandItemsEnabled() ? "§aON" : "§cOFF")));
                    sendMessage("§a[Luna] §fHand Items " + (config.isHandItemsEnabled() ? "§aENABLED" : "§cDISABLED"));
                });
            
            this.triggerbotButton = new ButtonWidget(rightX - 80, startY + 90, 150, 20,
                new LiteralText("TriggerBot: " + (config.isTriggerbotEnabled() ? "§aON" : "§cOFF")),
                button -> {
                    config.setTriggerbotEnabled(!config.isTriggerbotEnabled());
                    button.setMessage(new LiteralText("TriggerBot: " + (config.isTriggerbotEnabled() ? "§aON" : "§cOFF")));
                    sendMessage("§a[Luna] §fTriggerBot " + (config.isTriggerbotEnabled() ? "§aENABLED" : "§cDISABLED"));
                });
            
            this.addButton(this.visibilityButton);
            this.addButton(this.handItemsButton);
            this.addButton(this.triggerbotButton);
        }
        
        // Close button
        this.closeButton = new ButtonWidget(this.width / 2 - 50, this.height - 40, 100, 20,
            new LiteralText("Close"),
            button -> {
                sendMessage("§a[Luna] §fGUI closed");
                this.onClose();
            });
        
        this.addButton(this.closeButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        
        // Title
        drawCenteredText(matrices, this.textRenderer, new LiteralText("§6Luna Client"), this.width / 2, 20, 0xFFFFFF);
        
        // Left side title
        drawCenteredText(matrices, this.textRenderer, new LiteralText("§cAttack"), this.width / 4, 50, 0xFFFFFF);
        
        // Right side title
        drawCenteredText(matrices, this.textRenderer, new LiteralText("§bOther"), this.width * 3 / 4, 50, 0xFFFFFF);
        
        // Instructions
        drawCenteredText(matrices, this.textRenderer, new LiteralText("Press Right Alt to open this GUI"), this.width / 2, this.height - 20, 0xAAAAAA);
        
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    public void sendMessage(String message) {
        if (this.client != null && this.client.player != null) {
            this.client.player.sendMessage(new LiteralText(message), false);
        }
    }
    
    private void clearChat() {
        if (this.client != null && this.client.player != null) {
            for (int i = 0; i < 50; i++) {
                this.client.player.sendMessage(new LiteralText(""), false);
            }
        }
    }
}
