package com.acikek.pt.core.mineral;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.lang.DisplayHolder;
import com.acikek.pt.core.lang.MineralDisplay;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.signature.ElementSignatureEntry;
import com.acikek.pt.core.signature.SignatureHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface Mineral extends DisplayHolder<MineralDisplay>, DatagenDelegator, SignatureHolder {

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
