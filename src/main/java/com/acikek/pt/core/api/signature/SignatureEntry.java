package com.acikek.pt.core.api.signature;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

public class SignatureEntry {

    private final Element element;
    private final int amount;
    private boolean primary;

    public SignatureEntry(Element element, int amount) {
        this.element = element;
        this.amount = amount;
    }

    public Element element() {
        return element;
    }

    public SignatureEntry withAmount(int newAmount) {
        var entry = new SignatureEntry(element, newAmount);
        if (primary) {
            entry.primary();
        }
        return entry;
    }

    public SignatureEntry withAmount(UnaryOperator<Integer> op) {
        return withAmount(op.apply(amount));
    }

    public ItemStack getStack(Item item) {
        return item.getDefaultStack().copyWithCount(amount);
    }

    public SignatureEntry primary() {
        primary = true;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public Text getQuantifiedText() {
        return element.getQuantifiedText(amount, primary);
    }

    public SignatureComponent unit() {
        return new ElementSignatureImpls.Unit(this);
    }

    public static SignatureEntry forObject(Object obj) {
        if (obj instanceof SignatureEntry entry) {
            return entry;
        }
        if (obj instanceof Element element) {
            return Signatures.single(element);
        }
        throw new IllegalStateException("object must be an ElementSignatureEntry or a single Element instance");
    }
}
