package uwu.taxevasion.qolmod;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import uwu.taxevasion.qolmod.commands.*;
import uwu.taxevasion.qolmod.modules.*;

public class NotRat extends MeteorAddon {
    public static final Category Category = new Category("QoL", stack());

    private static ItemStack stack() {
        ItemStack a = new ItemStack(Items.POPPY);
        a.addEnchantment(Enchantment.byRawId(1), 1);
        return a;
    }

    @Override
    public void onInitialize() {
        Modules.get().add(new AnyPlacer());
        Modules.get().add(new AutoAnchor());
        Modules.get().add(new AutoExecute());
        Modules.get().add(new ChunkCrash());
        Modules.get().add(new ClickNuke());
        Modules.get().add(new GhostBlockFly());
        Modules.get().add(new Printer());
        Modules.get().add(new ProjectileDeflector());
        Modules.get().add(new ShulkerDupe());
        Modules.get().add(new Totem());
        Modules.get().add(new VeloFly());

        Commands.add(new BeehiveCommand());
        Commands.add(new ClearCommand());
        Commands.add(new CorruptCommand());
        Commands.add(new EffectCommand());
        Commands.add(new ForceOpCommand());
        Commands.add(new HideCommand());
        Commands.add(new KillCommand());
        Commands.add(new LagCommand());
        Commands.add(new ShriekCommand());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(Category);
    }

    public String getPackage() {
        return "uwu.taxevasion.qolmod";
    }
}
