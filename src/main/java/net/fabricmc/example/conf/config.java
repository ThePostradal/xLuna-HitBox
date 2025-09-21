package net.fabricmc.example.conf;

public class config {
    public static boolean isPanic() {
        return panic;
    }

    public static void setPanic(boolean panic) {
        config.panic = panic;
    }
    
    public static void resetPanic() {
        config.panic = false;
    }

    public static double getSize() {
        return size;
    }

    public static void setSize(double size) {
        config.size = size;
    }

    public static boolean isHitboxesEnabled() {
        return hitboxesEnabled;
    }

    public static void setHitboxesEnabled(boolean hitboxesEnabled) {
        config.hitboxesEnabled = hitboxesEnabled;
    }

    public static boolean isGuiOpen() {
        return guiOpen;
    }

    public static void setGuiOpen(boolean guiOpen) {
        config.guiOpen = guiOpen;
    }

    public static boolean isEspEnabled() {
        return espEnabled;
    }

    public static void setEspEnabled(boolean espEnabled) {
        config.espEnabled = espEnabled;
    }

    public static boolean isVisibilityEnabled() {
        return visibilityEnabled;
    }

    public static void setVisibilityEnabled(boolean visibilityEnabled) {
        config.visibilityEnabled = visibilityEnabled;
    }

    public static boolean isHandItemsEnabled() {
        return handItemsEnabled;
    }

    public static void setHandItemsEnabled(boolean handItemsEnabled) {
        config.handItemsEnabled = handItemsEnabled;
    }

    public static boolean isTriggerbotEnabled() {
        return triggerbotEnabled;
    }

    public static void setTriggerbotEnabled(boolean triggerbotEnabled) {
        config.triggerbotEnabled = triggerbotEnabled;
    }

    public static boolean isCriticalsOnly() {
        return criticalsOnly;
    }

    public static void setCriticalsOnly(boolean criticalsOnly) {
        config.criticalsOnly = criticalsOnly;
    }

    public static int getTriggerbotDelay() {
        return triggerbotDelay;
    }

    public static void setTriggerbotDelay(int triggerbotDelay) {
        config.triggerbotDelay = triggerbotDelay;
    }

    public static String getHitboxBind() {
        return hitboxBind;
    }

    public static void setHitboxBind(String hitboxBind) {
        config.hitboxBind = hitboxBind;
    }

    public static String getEspBind() {
        return espBind;
    }

    public static void setEspBind(String espBind) {
        config.espBind = espBind;
    }

    public static String getTriggerbotBind() {
        return triggerbotBind;
    }

    public static void setTriggerbotBind(String triggerbotBind) {
        config.triggerbotBind = triggerbotBind;
    }

    private static boolean panic;
    private static double size = 0.3;
    private static boolean hitboxesEnabled = true;
    private static boolean guiOpen = false;
    private static boolean espEnabled = false;
    private static boolean visibilityEnabled = false;
    private static boolean handItemsEnabled = false;
    private static boolean triggerbotEnabled = false;
    private static boolean criticalsOnly = false;
    private static int triggerbotDelay = 100; // ms
    private static String hitboxBind = "";
    private static String espBind = "";
    private static String triggerbotBind = "";
}
