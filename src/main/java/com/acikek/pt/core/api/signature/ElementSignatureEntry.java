package com.acikek.pt.core.api.signature;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.element.Elements;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

public class ElementSignatureEntry {

    private final Element element;
    private final int amount;
    private boolean primary;

    public ElementSignatureEntry(Element element, int amount) {
        this.element = element;
        this.amount = amount;
    }

    public Element element() {
        return element;
    }

    public ElementSignatureEntry withAmount(int newAmount) {
        var entry = new ElementSignatureEntry(element, newAmount);
        if (primary) {
            entry.primary();
        }
        return entry;
    }

    public ElementSignatureEntry withAmount(UnaryOperator<Integer> op) {
        return withAmount(op.apply(amount));
    }

    public ItemStack getResultStack(World world) {
        return element.getMineralResultItem(world).getDefaultStack().copyWithCount(amount);
    }

    public ElementSignatureEntry primary() {
        primary = true;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public Text getQuantifiedText() {
        return element.getQuantifiedText(amount, primary);
    }

    public ElementSignature unit() {
        return new ElementSignatureImpls.Unit(this);
    }

    public static ElementSignatureEntry forObject(Object obj) {
        if (obj instanceof ElementSignatureEntry entry) {
            return entry;
        }
        if (obj instanceof Element element) {
            return Elements.single(element);
        }
        throw new IllegalStateException("object must be an ElementSignatureEntry or a single Element instance");
    }
}
