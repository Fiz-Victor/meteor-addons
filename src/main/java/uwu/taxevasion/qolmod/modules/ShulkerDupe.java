package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import uwu.taxevasion.qolmod.NotRat;

public class ShulkerDupe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> toggle = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle")
        .description("toggles after duping")
        .defaultValue(true)
        .build());

    private final Setting<Integer> slot1 = sgGeneral.add(new IntSetting.Builder()
        .name("min-slot")
        .defaultValue(0)
        .sliderMin(0)
        .sliderMax(26)
        .build());

    private final Setting<Integer> slot2 = sgGeneral.add(new IntSetting.Builder()
        .name("max-slot")
        .defaultValue(26)
        .sliderMin(0)
        .sliderMax(26)
        .build());

    public ShulkerDupe() {
        super(NotRat.Category, "shulker-dupe", "open a shulker");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.currentScreen instanceof ShulkerBoxScreen && mc.player != null) {
            HitResult wow = mc.crosshairTarget;
            BlockHitResult bhr = (BlockHitResult) wow;
            mc.interactionManager.updateBlockBreakingProgress(bhr.getBlockPos(), Direction.DOWN);
        }
    }

    @EventHandler
    public void onSendPacket(PacketEvent.Sent event) {
        if (!(event.packet instanceof PlayerActionC2SPacket) || ((PlayerActionC2SPacket) event.packet).getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK)
            return;

        for (int i = slot1.get(); i < slot2.get(); i++) {
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
        }
        if (toggle.get())
            toggle();
    }
}
