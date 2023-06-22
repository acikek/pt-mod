package com.acikek.pt.core.api.mineral;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.MaterialHolder;
import com.acikek.pt.core.api.data.DataHolder;
import com.acikek.pt.core.api.data.DataKey;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface Mineral extends ContentBase, DataHolder, DisplayHolder<MineralDisplay>, SignatureHolder, DatagenDelegator, MaterialHolder {

    DataKey<Block> BLOCK = DataKey.of("mineral_block", Block.class);
    DataKey<Block> CLUSTER = DataKey.of("mineral_cluster", Block.class);
    DataKey<Item> RAW_FORM = DataKey.of("mineral_raw_form", Item.class);

    DataKey<Item> RESULT = DataKey.of("mineral_result_item", Item.class);

    void init();

    static <T extends DataHolder> Item getResultItem(List<T> holders, World world) {
        var list = holders.stream()
                .filter(holder -> holder.getData().contains(RESULT))
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        var holder = list.get(world.random.nextInt(list.size()));
        return holder.getData().get(RESULT);
    }

    static Item getResultItem(Element element, World world) {
        var sourceItem = getResultItem(element.getSources(), world);
        if (sourceItem != null) {
            return sourceItem;
        }
        return getResultItem(element.getRefinedStates(), world);
    }

    static List<ItemStack> getResultStacks(Element element, World world) {
        return element.signature().getResults(world).stream()
                .map(entry -> entry.getStack(getResultItem(element, world)))
                .toList();
    }
}
