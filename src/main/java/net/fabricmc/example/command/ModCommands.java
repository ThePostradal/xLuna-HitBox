package net.fabricmc.example.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.example.conf.config;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

public class ModCommands {
    
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("Luna")
            .then(ClientCommandManager.literal("HitBox")
                .then(ClientCommandManager.literal("toggle")
                    .executes(ModCommands::toggleHitboxes))
                .then(ClientCommandManager.literal("size")
                    .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(0.1, 2.0))
                        .executes(ModCommands::setSize)))
                .then(ClientCommandManager.literal("panic")
                    .executes(ModCommands::panic))
                .executes(ModCommands::showHitboxStatus))
            .then(ClientCommandManager.literal("Esp")
                .then(ClientCommandManager.literal("toggle")
                    .executes(ModCommands::toggleEsp))
                .executes(ModCommands::showEspStatus))
            .then(ClientCommandManager.literal("Bind")
                .then(ClientCommandManager.literal("HitBox")
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(ModCommands::bindHitBox)))
                .then(ClientCommandManager.literal("Esp")
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(ModCommands::bindEsp)))
                .then(ClientCommandManager.literal("TriggerBot")
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(ModCommands::bindTriggerBot))))
            .executes(ModCommands::showMainStatus));
    }
    
    private static int showMainStatus(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        
        source.sendFeedback(new LiteralText("§6=== Luna Client ==="));
        source.sendFeedback(new LiteralText("§fHitboxes: " + (config.isHitboxesEnabled() ? "§aENABLED" : "§cDISABLED")));
        source.sendFeedback(new LiteralText("§fESP: " + (config.isEspEnabled() ? "§aENABLED" : "§cDISABLED")));
        source.sendFeedback(new LiteralText("§6=================="));
        source.sendFeedback(new LiteralText("§7Commands: /Luna HitBox, /Luna Esp"));
        source.sendFeedback(new LiteralText("§7Keys: U/I (size), Right Alt (GUI), P (panic)"));
        
        return 1;
    }
    
    private static int toggleHitboxes(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        config.setHitboxesEnabled(!config.isHitboxesEnabled());
        source.sendFeedback(new LiteralText("§a[Luna] §fHitboxes " + (config.isHitboxesEnabled() ? "§aENABLED" : "§cDISABLED")));
        return 1;
    }
    
    private static int setSize(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        double size = DoubleArgumentType.getDouble(context, "value");
        config.setSize(size);
        source.sendFeedback(new LiteralText("§a[Luna] §fSize set to: §e" + String.format("%.2f", size)));
        return 1;
    }
    
    private static int panic(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        config.setPanic(true);
        source.sendFeedback(new LiteralText("§c[Luna] §fPanic mode activated!"));
        return 1;
    }
    
    private static int showHitboxStatus(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        source.sendFeedback(new LiteralText("§a[Luna] §fHitboxes: " + (config.isHitboxesEnabled() ? "§aON" : "§cOFF") + " §f| Size: §e" + String.format("%.2f", config.getSize())));
        return 1;
    }
    
    private static int toggleEsp(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        config.setEspEnabled(!config.isEspEnabled());
        source.sendFeedback(new LiteralText("§a[Luna] §fESP " + (config.isEspEnabled() ? "§aENABLED" : "§cDISABLED")));
        return 1;
    }
    
    private static int showEspStatus(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        source.sendFeedback(new LiteralText("§a[Luna] §fESP: " + (config.isEspEnabled() ? "§aON" : "§cOFF")));
        return 1;
    }
    
    private static int bindHitBox(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        String key = StringArgumentType.getString(context, "key");
        
        if (key.equalsIgnoreCase("delete")) {
            config.setHitboxBind("");
            source.sendFeedback(new LiteralText("§a[Luna] §fHitBox bind removed"));
        } else {
            config.setHitboxBind(key);
            source.sendFeedback(new LiteralText("§a[Luna] §fHitBox bound to: §e" + key));
        }
        return 1;
    }
    
    private static int bindEsp(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        String key = StringArgumentType.getString(context, "key");
        
        if (key.equalsIgnoreCase("delete")) {
            config.setEspBind("");
            source.sendFeedback(new LiteralText("§a[Luna] §fESP bind removed"));
        } else {
            config.setEspBind(key);
            source.sendFeedback(new LiteralText("§a[Luna] §fESP bound to: §e" + key));
        }
        return 1;
    }
    
    private static int bindTriggerBot(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        String key = StringArgumentType.getString(context, "key");
        
        if (key.equalsIgnoreCase("delete")) {
            config.setTriggerbotBind("");
            source.sendFeedback(new LiteralText("§a[Luna] §fTriggerBot bind removed"));
        } else {
            config.setTriggerbotBind(key);
            source.sendFeedback(new LiteralText("§a[Luna] §fTriggerBot bound to: §e" + key));
        }
        return 1;
    }
}
