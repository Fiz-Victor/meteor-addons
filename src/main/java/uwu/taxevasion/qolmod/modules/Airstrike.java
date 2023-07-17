package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import uwu.taxevasion.qolmod.NotRat;
import uwu.taxevasion.qolmod.Utils;

import java.util.Random;

public class Airstrike extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> radius = sgGeneral.add(new IntSetting.Builder()
        .name("radius")
        .defaultValue(30)
        .sliderRange(1, 100)
        .build());

    private final Setting<Integer> power = sgGeneral.add(new IntSetting.Builder()
        .name("power")
        .defaultValue(10)
        .sliderRange(1, 127)
        .build());

    private final Setting<Integer> height = sgGeneral.add(new IntSetting.Builder()
        .name("height")
        .description("y level they spawn")
        .defaultValue(100)
        .sliderRange(-63, 320)
        .build());

    private final Setting<Integer> speed = sgGeneral.add(new IntSetting.Builder()
        .name("speed")
        .defaultValue(10)
        .sliderRange(1, 10)
        .build());

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("its in ticks")
        .defaultValue(2)
        .sliderRange(0, 20)
        .build());
    Vec3d origin = null;
    int i = 0;
    public Airstrike() {
        super(NotRat.Category, "airstrike", "holy shit can people stop skidding this");
    }

    private Vec3d thepos() {
        return new Vec3d(new Random().nextDouble(radius.get() * 2) - radius.get() + origin.x,
            height.get(),
            new Random().nextDouble(radius.get() * 2) - radius.get() + origin.z);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        origin = mc.player.getPos();
        ItemStack bomb = Utils.createItem("{EntityTag:{id:fireball,power:[0," + -speed.get() + ",0],Pos:[" + thepos().x + "," + thepos().y + "," + thepos().z + "],ExplosionPower:" + power.get() + "}}", Items.SALMON_SPAWN_EGG);
        ItemStack bfr = mc.player.getMainHandStack();
        BlockHitResult bhr = new BlockHitResult(mc.player.getPos(), Direction.DOWN, BlockPos.ofFloored(mc.player.getPos()), false);

        i++;
        if (i >= delay.get()) {
            Utils.spawnItem(bomb);
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
            Utils.spawnItem(bfr);
            i = 0;
        }
    }

    @Override
    public void onActivate() {
        if (!mc.player.getAbilities().creativeMode) {
            error("You need to be in creative mode.");
            toggle();
        }
    }
}
