package com.acikek.pt.core;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.Elements;
import com.acikek.pt.core.lang.ElementNames;
import com.acikek.pt.core.lang.MineralNaming;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.mineral.MineralBlock;
import com.acikek.pt.core.mineral.Minerals;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSources;

import java.util.List;

public class PeriodicTable extends AbstractPeriodicTable {

    public static final MineralBlock TEST = Minerals.block(
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

    public static final MineralBlock LITHIOPHILITE = Minerals.block(
            "Lithiophilite",
            () -> List.of(Elements.unit(PeriodicTable.LITHIUM), /*Mn,*/ /*P,*/ Elements.unit(PeriodicTable.OXYGEN))
    );

    public static final MineralBlock STIBNITE = Minerals.block(
            MineralNaming.full("Stibnite", "Shard"),
            () -> List.of(Elements.unit(PeriodicTable.ANTIMONY, 2), Elements.unit(PeriodicTable.SULFUR, 3))
    );

    /*public static final MineralBlock CHRYSOBERYL = Minerals.block(
            MineralNaming.full("Chrysoberyl", "Gemstone"),
            () -> List.of(Elements.single(PeriodicTable.BERYLLIUM), /*Al2, Elements.single(PeriodicTable.OXYGEN, 4))
    );*/

    public static final MineralBlock GYPSUM = Minerals.block(
            MineralNaming.full("Gypsum", "Crystal"),
            () -> List.of(/*Ca,*/ Elements.unit(PeriodicTable.SULFUR), Elements.unit(PeriodicTable.OXYGEN, 4))
    );

    public static final Element HYDROGEN = Elements.gas(ElementNames.byId("hydrogen", "H"), 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.full("lithium", ElementSources.mineral(LITHIOPHILITE), RefinedStates.metal(1.6f));
    public static final Element BERYLLIUM = Elements.full("beryllium", ElementSources.fullMineral(TEST), RefinedStates.metal(6.5f));
    public static final Element OXYGEN = Elements.gas(ElementNames.byId("oxygen", "O"), 0, 235);
    public static final Element SULFUR = Elements.full(ElementNames.byId("sulfur", "S"), ElementSources.fullMineral(GYPSUM), RefinedStates.sack());
    public static final Element ANTIMONY = Elements.full(ElementNames.byId("antimony", "Sb"), ElementSources.fullMineral(STIBNITE), RefinedStates.metal(4.0f));
    public static final Element PLATINUM = Elements.full(ElementNames.byId("platinum", "Pt"), ElementSources.ore(), RefinedStates.metal(4.5f));
    public static final Element OSMIUM = Elements.full("osmium", ElementSources.ore(), RefinedStates.metal(7.0f));

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
