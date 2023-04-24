package com.acikek.pt.core.api.mineral;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignatureEntry;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface Mineral extends DisplayHolder<MineralDisplay>, DatagenDelegator, SignatureHolder, MineralResultHolder {

    void init();

    void register(PTRegistry registry);

    default List<ElementSignatureEntry> getAllResultEntries() {
        return signature().stream()
                .flatMap(result -> result.all().stream())
                .toList();
    }

    default List<ElementSignatureEntry> getResultElements(World world) {
        return signature().stream()
                .flatMap(result -> result.get(world).stream())
                .toList();
    }

    default List<ItemStack> getResultStacks(World world) {
        return getResultElements(world).stream()
                .map(entry -> entry.getResultStack(world))
                .toList();
    }
}
