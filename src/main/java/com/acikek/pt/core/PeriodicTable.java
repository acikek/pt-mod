package com.acikek.pt.core;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.Elements;
import com.acikek.pt.core.lang.ElementNames;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSources;

import java.util.List;

public class PeriodicTable extends AbstractPeriodicTable {

    public static final Element HYDROGEN = Elements.gas(ElementNames.byId("hydrogen", "H"), 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.full("lithium", ElementSources.mineral(), RefinedStates.metal(1.6f));
    public static final Element BERYLLIUM = Elements.full("beryllium", ElementSources.fullMineral(), RefinedStates.metal(6.5f));
    public static final Element OXYGEN = Elements.gas(ElementNames.byId("oxygen", "H"), 0, 235);
    public static final Element ANTIMONY = Elements.full(ElementNames.byId("antimony", "Sb"), ElementSources.fullMineral(), RefinedStates.metal(4.0f));

    public static final PeriodicTable INSTANCE = new PeriodicTable();

    @Override
    protected List<Element> createElements() {
        return List.of(HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, OXYGEN, ANTIMONY);
    }
}
