package uwu.taxevasion.qolmod.modules;

import meteordevelopment.meteorclient.events.meteor.MouseButtonEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import uwu.taxevasion.qolmod.NotRat;
import uwu.taxevasion.qolmod.Utils;

public class Boom extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Modes> mode = sgGeneral.add(new EnumSetting.Builder<Modes>()
        .name("mode")
        .description("the mode")
        .defaultValue(Modes.Instant)
        .build());

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("fastness of thing")
        .defaultValue(10)
        .sliderRange(1, 10)
        .visible(() -> mode.get() != Modes.Lightning || mode.get() != Modes.Instant || mode.get() != Modes.Arrow)
        .build());

    private final Setting<Integer> power = sgGeneral.add(new IntSetting.Builder()
        .name("power")
        .description("how big explosion")
        .defaultValue(10)
        .min(1)
        .sliderMax(127)
        .visible(() -> mode.get() == Modes.Instant || mode.get() == Modes.Motion)
        .build());

    private final Setting<String> nbt = sgGeneral.add(new StringSetting.Builder()
        .name("custom-nbt")
        .description("i will not help you whatsoever if you do not know how to use this")
        .defaultValue("{EntityTag:{id:\"wither\"}}")
        .visible(() -> mode.get() == Modes.Custom)
        .build());

    private final Setting<Boolean> auto = sgGeneral.add(new BoolSetting.Builder()
        .name("auto")
        .description("machine gun")
        .defaultValue(false)
        .build());

    public Boom() {
        super(NotRat.Category, "boom", "shoots something where you click");
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (!mc.options.attackKey.isPressed() || mc.currentScreen != null || !mc.player.getAbilities().creativeMode || auto.get())
            return;
        HitResult hr = mc.cameraEntity.raycast(300, 0, true);
        Vec3d owo = hr.getPos();
        BlockPos pos = BlockPos.ofFloored(owo);
        ItemStack bfr = mc.player.getMainHandStack();
        Vec3d sex = mc.player.getRotationVector().multiply(speed.get());
        BlockHitResult bhr = new BlockHitResult(mc.player.getEyePos(), Direction.DOWN, BlockPos.ofFloored(mc.player.getEyePos()), false);
        ItemStack egg = new ItemStack(Items.SALMON_SPAWN_EGG);
        NbtCompound tag = new NbtCompound();

        switch (mode.get()) {
            case Instant -> {
                Vec3d aaa = mc.player.getRotationVector().multiply(100);
                NbtList Pos = new NbtList();
                NbtList motion = new NbtList();
                Pos.add(NbtDouble.of(pos.getX()));
                Pos.add(NbtDouble.of(pos.getY()));
                Pos.add(NbtDouble.of(pos.getZ()));
                motion.add(NbtDouble.of(aaa.x));
                motion.add(NbtDouble.of(aaa.y));
                motion.add(NbtDouble.of(aaa.z));
                tag.put("Pos", Pos);
                tag.put("Motion", motion);
                tag.putInt("ExplosionPower", power.get());
                tag.putString("id", "minecraft:fireball");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Motion -> {
                NbtList motion = new NbtList();
                motion.add(NbtDouble.of(sex.x));
                motion.add(NbtDouble.of(sex.y));
                motion.add(NbtDouble.of(sex.z));
                tag.put("Motion", motion);
                tag.putInt("ExplosionPower", power.get());
                tag.putString("id", "minecraft:fireball");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Lightning -> {
                NbtList Pos = new NbtList();
                Pos.add(NbtDouble.of(pos.getX()));
                Pos.add(NbtDouble.of(pos.getY()));
                Pos.add(NbtDouble.of(pos.getZ()));
                tag.put("Pos", Pos);
                tag.putString("id", "minecraft:lightning_bolt");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Arrow -> {
                NbtList speed = new NbtList();
                speed.add(NbtDouble.of(sex.x));
                speed.add(NbtDouble.of(sex.y));
                speed.add(NbtDouble.of(sex.z));
                tag.put("Motion", speed);
                tag.putString("id", "minecraft:arrow");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Custom -> {
                Utils.spawnItem(Utils.createItem(nbt.get(), Items.SALMON_SPAWN_EGG));
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.options.attackKey.isPressed()) return;
        HitResult hr = mc.cameraEntity.raycast(300, 0, true);
        Vec3d owo = hr.getPos();
        BlockPos pos = BlockPos.ofFloored(owo);
        ItemStack bfr = mc.player.getMainHandStack();
        Vec3d sex = mc.player.getRotationVector().multiply(speed.get());
        BlockHitResult bhr = new BlockHitResult(mc.player.getEyePos(), Direction.DOWN, BlockPos.ofFloored(mc.player.getEyePos()), false);
        ItemStack egg = new ItemStack(Items.SALMON_SPAWN_EGG);
        NbtCompound tag = new NbtCompound();

        switch (mode.get()) {
            case Instant -> {
                Vec3d aaa = mc.player.getRotationVector().multiply(100);
                NbtList Pos = new NbtList();
                NbtList motion = new NbtList();
                Pos.add(NbtDouble.of(pos.getX()));
                Pos.add(NbtDouble.of(pos.getY()));
                Pos.add(NbtDouble.of(pos.getZ()));
                motion.add(NbtDouble.of(aaa.x));
                motion.add(NbtDouble.of(aaa.y));
                motion.add(NbtDouble.of(aaa.z));
                tag.put("Pos", Pos);
                tag.put("Motion", motion);
                tag.putInt("ExplosionPower", power.get());
                tag.putString("id", "minecraft:fireball");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Motion -> {
                NbtList motion = new NbtList();
                motion.add(NbtDouble.of(sex.x));
                motion.add(NbtDouble.of(sex.y));
                motion.add(NbtDouble.of(sex.z));
                tag.put("Motion", motion);
                tag.putInt("ExplosionPower", power.get());
                tag.putString("id", "minecraft:fireball");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Lightning -> {
                NbtList Pos = new NbtList();
                Pos.add(NbtDouble.of(pos.getX()));
                Pos.add(NbtDouble.of(pos.getY()));
                Pos.add(NbtDouble.of(pos.getZ()));
                tag.put("Pos", Pos);
                tag.putString("id", "minecraft:lightning_bolt");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Arrow -> {
                NbtList speed = new NbtList();
                speed.add(NbtDouble.of(sex.x));
                speed.add(NbtDouble.of(sex.y));
                speed.add(NbtDouble.of(sex.z));
                tag.put("Motion", speed);
                tag.putString("id", "minecraft:arrow");
                egg.setSubNbt("EntityTag", tag);
                Utils.spawnItem(egg);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
            case Custom -> {
                Utils.spawnItem(Utils.createItem(nbt.get(), Items.SALMON_SPAWN_EGG));
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
                Utils.spawnItem(bfr);
            }
        }
    }

    public enum Modes {
        Instant, Motion, Lightning, Arrow, Custom
    }
}
