package com.acikek.pt.core;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.Elements;
import com.acikek.pt.core.lang.ElementNames;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.mineral.MineralBlock;
import com.acikek.pt.core.mineral.Minerals;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSources;

import java.util.List;

public class PeriodicTable extends AbstractPeriodicTable {

    public static final Element HYDROGEN = Elements.gas(ElementNames.byId("hydrogen", "H"), 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.full("lithium", ElementSources.mineral(null), RefinedStates.metal(1.6f));
    public static final Element BERYLLIUM = Elements.full("beryllium", ElementSources.fullMineral(null), RefinedStates.metal(6.5f));
    public static final Element OXYGEN = Elements.gas(ElementNames.byId("oxygen", "O"), 0, 235);
    public static final Element SULFUR = Elements.full(ElementNames.byId("sulfur", "S"), ElementSources.mineral(null), RefinedStates.metal(0.0f));
    public static final Element ANTIMONY = Elements.full(ElementNames.byId("antimony", "Sb"), ElementSources.fullMineral(MineralBlocks.STIBNITE), RefinedStates.metal(4.0f));
    public static final Element OSMIUM = Elements.full("osmium", ElementSources.ore(), RefinedStates.metal(7.0f));

    public static final PeriodicTable INSTANCE = new PeriodicTable();

    @Override
    protected List<Element> createElements() {
        return List.of(HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, OXYGEN, ANTIMONY, OSMIUM);
    }
}
