package com.acikek.pt.core.api.mineral;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.MaterialHolder;
import com.acikek.pt.core.api.data.DataHolder;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.signature.SignatureHolder;

public interface Mineral<D> extends ContentBase, DataHolder<D>, DisplayHolder<MineralDisplay>, SignatureHolder, MineralResultHolder, DatagenDelegator, MaterialHolder {

    void init();
}
