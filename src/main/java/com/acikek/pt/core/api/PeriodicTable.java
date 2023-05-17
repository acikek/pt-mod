package com.acikek.pt.core.api;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.element.Elements;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.mineral.Minerals;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.signature.Signatures;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Map;

public class PeriodicTable extends AbstractPeriodicTable {

    public static final Mineral<?> TEST = Minerals.block(
            "Test Mineral",
            () -> List.of(
                    Signatures.amount(PeriodicTable.BERYLLIUM, 3).primary().unit(),
                    Signatures.wrap(5,
                            Signatures.unit(PeriodicTable.SULFUR, 2),
                            Signatures.single(PeriodicTable.OXYGEN).primary().unit()
                    ),
                    Signatures.random(7, Signatures.single(PeriodicTable.ANTIMONY).primary(), PeriodicTable.OSMIUM, PeriodicTable.PLATINUM),
                    Signatures.hydrate(4)
            )
    );

    public static final Mineral<?> LITHIOPHILITE = Minerals.block(
            "Lithiophilite",
            () -> List.of(Signatures.single(PeriodicTable.LITHIUM).primary().unit(), /*Mn,*/ /*P,*/ Signatures.unit(PeriodicTable.OXYGEN))
    );

    public static final Mineral<?> COLEMANITE = Minerals.block(
            "Colemanite",
            () -> List.of(/* Ca2, */ Signatures.amount(PeriodicTable.BORON, 6).primary().unit(), Signatures.unit(PeriodicTable.OXYGEN, 11), Signatures.hydrate(5))
    );

    public static final Mineral<?> ANTHRACITE = Minerals.blockWithRawForm(
            MineralDisplay.byName("Anthracite", "Coal"),
            () -> List.of(Signatures.amount(PeriodicTable.CARBON, 8).primary().unit(), Signatures.unit(PeriodicTable.OXYGEN, 2), Signatures.unit(PeriodicTable.HYDROGEN))
    );

    public static final Mineral<?> STIBNITE = Minerals.fullBlock(
            MineralDisplay.byName("Stibnite", "Shard"),
            () -> List.of(Signatures.amount(PeriodicTable.ANTIMONY, 2).primary().unit(), Signatures.unit(PeriodicTable.SULFUR, 3))
    );

    /*public static final MineralBlock CHRYSOBERYL = Minerals.block(
            MineralNaming.full("Chrysoberyl", "Gemstone"),
            () -> List.of(Signatures.single(PeriodicTable.BERYLLIUM), /*Al2, Signatures.single(PeriodicTable.OXYGEN, 4))
    );*/

    public static final Mineral<?> GYPSUM = Minerals.fullBlock(
            MineralDisplay.byName("Gypsum", "Crystal"),
            () -> List.of(/*Ca,*/ Signatures.single(PeriodicTable.SULFUR).primary().unit(), Signatures.unit(PeriodicTable.OXYGEN, 4))
    );

    public static final Element HYDROGEN = Elements.gas(ElementDisplay.byId("hydrogen", "H", 2), 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.basic("lithium", RefinedStates.metal(1.6f), ElementSources.mineral(LITHIOPHILITE));
    public static final Element BERYLLIUM = Elements.basic("beryllium", RefinedStates.metal(6.5f), ElementSources.mineral(TEST));
    public static final Element BORON = Elements.basic(ElementDisplay.byId("boron", "B"), RefinedStates.metal(10.0f), ElementSources.mineral(COLEMANITE));
    public static final Element CARBON = Elements.create(ElementDisplay.byId("carbon", "C"), Map.of(
            RefinedStates.metal(1.5f), List.of(ElementSources.mineral(ANTHRACITE)),
            RefinedStates.builder()
                    .id(PT.id("vanilla"))
                    .item(Items.COAL)
                    .block(Blocks.COAL_BLOCK)
                    .build(),
            List.of(ElementSources.oreBuilder().ore(Blocks.COAL_ORE).deepslateOre(Blocks.DEEPSLATE_COAL_ORE).build())
    ));
    public static final Element OXYGEN = Elements.gas(ElementDisplay.byId("oxygen", "O"), 0, 235);
    public static final Element SULFUR = Elements.basic(ElementDisplay.byId("sulfur", "S"), RefinedStates.sack(), ElementSources.mineral(GYPSUM));
    public static final Element VANADIUM = Elements.basic(ElementDisplay.byId("vanadium", "V"), RefinedStates.metal(8.0f), ElementSources.ore(2));
    public static final Element CHROMIUM = Elements.basic(ElementDisplay.byId("chromium", "Cr"), RefinedStates.metal(9.5f), ElementSources.ore(3));
    public static final Element MANGANESE = Elements.basic(ElementDisplay.byId("manganese", "Mn"), RefinedStates.metal(7.0f), ElementSources.ore(1));
    public static final Element IRON = Elements.basic(
            ElementDisplay.byId("iron", "Fe"),
            RefinedStates.wrap(Items.IRON_INGOT, Items.IRON_NUGGET, Blocks.IRON_BLOCK),
            ElementSources.wrapOre(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Items.RAW_IRON, Blocks.RAW_IRON_BLOCK)
    );
    public static final Element COBALT = Elements.basic("cobalt", RefinedStates.metal(6.0f), ElementSources.ore(3));
    public static final Element NICKEL = Elements.basic("nickel", RefinedStates.metal(5.0f), ElementSources.ore(2));
    public static final Element SILVER = Elements.basic(ElementDisplay.byId("silver", "Ag"), RefinedStates.metal(3.5f), ElementSources.ore(1));
    public static final Element CADMIUM = Elements.basic(ElementDisplay.byId("cadmium", "Cd"), RefinedStates.metal(3.0f), ElementSources.ore(2));
    public static final Element ANTIMONY = Elements.basic(ElementDisplay.byId("antimony", "Sb"), RefinedStates.metal(4.0f), ElementSources.mineral(STIBNITE));
    public static final Element PLATINUM = Elements.basic(ElementDisplay.byId("platinum", "Pt"), RefinedStates.metal(4.5f), ElementSources.ore(2));
    public static final Element OSMIUM = Elements.basic("osmium", RefinedStates.metal(7.0f), ElementSources.ore(3));

    public static final AbstractPeriodicTable INSTANCE = new PeriodicTable();

    @Override
    protected List<Mineral<?>> createMinerals() {
        return List.of(TEST, LITHIOPHILITE, COLEMANITE, ANTHRACITE, STIBNITE, /*CHRYSOBERYL,*/ GYPSUM);
    }

    @Override
    protected List<Element> createElements() {
        return List.of(HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, BORON, CARBON, OXYGEN, SULFUR, VANADIUM, CHROMIUM, MANGANESE, IRON, COBALT, NICKEL, SILVER, CADMIUM, ANTIMONY, PLATINUM, OSMIUM);
    }
}
