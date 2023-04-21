package com.acikek.pt.core.mineral;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.lang.NameHolder;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.signature.ElementSignatureEntry;
import com.acikek.pt.core.lang.MineralNaming;
import com.acikek.pt.core.signature.SignatureHolder;
import com.acikek.pt.core.source.MaterialHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface Mineral extends NameHolder<MineralNaming>, DatagenDelegator, SignatureHolder, MaterialHolder {

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
