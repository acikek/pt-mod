package com.acikek.pt.core.api;

import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.element.Elements;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.mineral.Minerals;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.source.ElementSources;

import java.util.List;

public class PeriodicTable extends AbstractPeriodicTable {

    public static final Mineral TEST = Minerals.block(
            "Test Mineral",
            () -> List.of(
                    Elements.amount(PeriodicTable.BERYLLIUM, 3).primary().unit(),
                    Elements.wrap(5,
                            Elements.amount(PeriodicTable.SULFUR, 2),
                            Elements.single(PeriodicTable.OXYGEN).primary()
                    ),
                    Elements.random(7, Elements.single(PeriodicTable.ANTIMONY).primary(), PeriodicTable.OSMIUM, PeriodicTable.PLATINUM),
                    Elements.hydrate(4)
            )
    );

    public static final Mineral LITHIOPHILITE = Minerals.block(
            "Lithiophilite",
            () -> List.of(Elements.single(PeriodicTable.LITHIUM).primary().unit(), /*Mn,*/ /*P,*/ Elements.unit(PeriodicTable.OXYGEN))
    );

    public static final Mineral STIBNITE = Minerals.fullBlock(
            MineralDisplay.byName("Stibnite", "Shard"),
            () -> List.of(Elements.amount(PeriodicTable.ANTIMONY, 2).primary().unit(), Elements.unit(PeriodicTable.SULFUR, 3))
    );

    /*public static final MineralBlock CHRYSOBERYL = Minerals.block(
            MineralNaming.full("Chrysoberyl", "Gemstone"),
            () -> List.of(Elements.single(PeriodicTable.BERYLLIUM), /*Al2, Elements.single(PeriodicTable.OXYGEN, 4))
    );*/

    public static final Mineral GYPSUM = Minerals.fullBlock(
            MineralDisplay.byName("Gypsum", "Crystal"),
            () -> List.of(/*Ca,*/ Elements.single(PeriodicTable.SULFUR).primary().unit(), Elements.unit(PeriodicTable.OXYGEN, 4))
    );

    public static final Element HYDROGEN = Elements.gas(ElementDisplay.byId("hydrogen", "H", 2), 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.full("lithium", ElementSources.mineral(LITHIOPHILITE), RefinedStates.metal(1.6f));
    public static final Element BERYLLIUM = Elements.full("beryllium", ElementSources.mineral(TEST), RefinedStates.metal(6.5f));
    public static final Element OXYGEN = Elements.gas(ElementDisplay.byId("oxygen", "O"), 0, 235);
    public static final Element SULFUR = Elements.full(ElementDisplay.byId("sulfur", "S"), ElementSources.mineral(GYPSUM), RefinedStates.sack());
    public static final Element ANTIMONY = Elements.full(ElementDisplay.byId("antimony", "Sb"), ElementSources.mineral(STIBNITE), RefinedStates.metal(4.0f));
    public static final Element PLATINUM = Elements.full(ElementDisplay.byId("platinum", "Pt"), ElementSources.ore(2), RefinedStates.metal(4.5f));
    public static final Element OSMIUM = Elements.full("osmium", ElementSources.ore(3), RefinedStates.metal(7.0f));

    public static final AbstractPeriodicTable INSTANCE = new PeriodicTable();

    @Override
    protected List<Mineral> createMinerals() {
        return List.of(TEST, LITHIOPHILITE, STIBNITE, /*CHRYSOBERYL,*/ GYPSUM);
    }

    @Override
    protected List<Element> createElements() {
        return List.of(HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, OXYGEN, SULFUR, ANTIMONY, PLATINUM, OSMIUM);
    }
}
