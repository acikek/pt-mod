package com.acikek.pt.block;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MineralBlock extends Block {

    public Text formula;

    public MineralBlock(Settings settings, String formula) {
        super(settings);
        this.formula = parse(formula);
    }

    public static String subscript(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            result.append(Character.toString(0x2080 + (c - 48)));
        }
        return result.toString();
    }

    public static MutableText parseComponent(String component) {
        char first = component.charAt(0);
        if (first == '(' || first == ')') {
            return Text.literal(component);
        }
        if (first >= 97 && first <= 122) {
            boolean isBold = component.endsWith("*");
            String element = isBold ? component.substring(0, component.length() - 2) : component;
            MutableText symbol = Text.translatable("symbol.pt." + element);
            if (isBold) {
                symbol = symbol.styled(style -> style.withBold(true));
            }
            return symbol;
        }
        String result = switch (first) {
            case ':' -> subscript(component.substring(1));
            case '.' -> "·";
            default -> ", ";
        };
        return Text.literal(result);
    }

    public static MutableText parse(String line) {
        MutableText result = Text.empty();
        for (String component : line.split(" ")) {
            result = result.append(parseComponent(component).formatted(Formatting.GRAY));
        }
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(formula);
        super.appendTooltip(stack, world, tooltip, options);
    }
}
