package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import uwu.taxevasion.qolmod.NotRat;
import uwu.taxevasion.qolmod.Utils;

public class AutoAnchor extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .defaultValue(4)
        .sliderRange(1, 6)
        .build());

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .defaultValue(250)
        .sliderRange(50, 500)
        .build());

    public AutoAnchor() {
        super(NotRat.Category, "auto-anchor", "kabooms an anchor after you place it");
    }

    @EventHandler
    private void onPlace(PlaceBlockEvent event) {
        if (!(event.block instanceof RespawnAnchorBlock)) return;
        BlockPos bpos = event.blockPos;
        FindItemResult glowstone = InvUtils.findInHotbar(Items.GLOWSTONE);
        int prevSlot = mc.player.getInventory().selectedSlot;

        new Thread(() -> {
            Utils.sleep(delay.get() / 2);
            InvUtils.swap(glowstone.slot(), false);
            if (Utils.inRange(bpos, range.get()) && !mc.player.isSneaking())
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(new Vec3d(bpos.getX(), bpos.getY(), bpos.getZ()), Utils.direction(mc.player.getYaw()), bpos, false));
            Utils.sleep(delay.get() / 2);
            InvUtils.swap(prevSlot, false);
            if (Utils.inRange(bpos, range.get()) && !mc.player.isSneaking())
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(new Vec3d(bpos.getX(), bpos.getY(), bpos.getZ()), Utils.direction(mc.player.getYaw()), bpos, false));
        }).start();
    }
}
