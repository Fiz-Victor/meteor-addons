package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.server.MinecraftServer;
import uwu.taxevasion.qolmod.NotRat;

public class Voider extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> radius = sgGeneral.add(new IntSetting.Builder()
        .name("radius")
        .description("radius")
        .defaultValue(90)
        .sliderRange(1, 90)
        .build());
    int i = 0;

    public Voider() {
        super(NotRat.Category, "voider", "erekrjskjfofhsfhqe");
    }

    @Override
    public void onActivate() {
        MinecraftServer server = mc.getServer() == null ? mc.getServer() : mc.world.getServer();
        if (server.getVersion().startsWith("1.18") || server.getVersion().startsWith("1.19")) {
            i = 319;
        } else {
            i = 255;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        MinecraftServer server = mc.getServer() == null ? mc.getServer() : mc.world.getServer();
        ChatUtils.sendPlayerMsg(server.getVersion());
        if (!(mc.player.hasPermissionLevel(4))) {
            toggle();
            error("must have op");
        }
        i--;
        ChatUtils.sendPlayerMsg("/fill ~-" + radius.get() + " " + i + " ~-" + radius.get() + " ~" + radius.get() + " " + i + " ~" + radius.get() + " air");

        if (server.getVersion().startsWith("1.18") || server.getVersion().startsWith("1.19") && i == -64) {
            toggle();
        } else if (i == 0) {
            toggle();
        }
    }
}
