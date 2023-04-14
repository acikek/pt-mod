package com.acikek.pt.datagen;

import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.source.ElementSource;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PTEnglishLangProvider extends FabricLanguageProvider {

    protected PTEnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    private static void generateForSource(TranslationBuilder builder, Element element, ElementSource source) {
        // TODO minerals
        if (source.getType() == ElementSource.Type.ORE) {
            builder.add(source.sourceBlock(), element.naming().englishName() + " Ore");
            builder.add(source.deepslateSourceBlock(), "Deepslate " + element.naming().englishName() + " Ore");
            String rawName = "Raw " + element.naming().englishName();
            builder.add(source.rawSourceBlock(), "Block of " + rawName);
            builder.add(source.rawSourceItem(), rawName);
        }
    }

    private static void generateForState(TranslationBuilder builder, Element element, ElementRefinedState state) {
        if (state.hasRefinedFluid()) {
            // TODO test
            builder.add(state.refinedFluid().getDefaultState().getBlockState().getBlock(), element.naming().englishName());
        }
        String itemName = switch (state.getType()) {
            case METAL -> element.naming().englishName() + " Ingot";
            case CHAMBER -> element.naming().englishName() + " Tank";
            case BASIN -> "Bottle of " + element.naming().englishName();
        };
        builder.add(state.refinedItem(), itemName);
        String miniSuffix = switch (state.getType()) {
            case METAL -> "Nugget";
            case CHAMBER -> "Cell";
            case BASIN -> "Vial";
        };
        builder.add(state.miniRefinedItem(), element.naming().englishName() + " " + miniSuffix);
        String blockName = switch (state.getType()) {
            case METAL -> "Block of " + element.naming().englishName();
            case CHAMBER -> element.naming().englishName() + " Chamber";
            case BASIN -> element.naming().englishName() + " Basin";
        };
        builder.add(state.refinedBlock(), blockName);
    }

    public static void generateForElement(TranslationBuilder builder, Element element) {
        builder.add(element.getNameKey(), element.naming().englishName());
        builder.add(element.getSymbolKey(), element.naming().symbol());
        if (element.hasSource()) {
            generateForSource(builder, element, element.source());
        }
        generateForState(builder, element, element.state());
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (Element element : PeriodicTable.INSTANCE.getElements()) {
            generateForElement(translationBuilder, element);
        }
    }
}
