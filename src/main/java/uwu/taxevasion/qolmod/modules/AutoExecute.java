package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.StringListSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import uwu.taxevasion.qolmod.NotRat;

import java.util.List;

public class AutoExecute extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<String>> cmds = sgGeneral.add(new StringListSetting.Builder()
        .name("commands")
        .description("Commands to run")
        .defaultValue(List.of("/say wow", "/tellraw @a {\"text\":\"balls\"}"))
        .build());

    public AutoExecute() {
        super(NotRat.Category, "auto-execute", "Automatically executes commands on activate");
    }

    @EventHandler
    public void onActivate() {
        if (!(cmds.get().isEmpty())) {
            for (int i = 0; i < cmds.get().size(); i++) {
                ChatUtils.sendPlayerMsg(cmds.get().get(i));
            }
            info("Tried to run commands, toggling");
            toggle();
        } else {
            error("you need commands idiot");
            toggle();
        }
    }
}
