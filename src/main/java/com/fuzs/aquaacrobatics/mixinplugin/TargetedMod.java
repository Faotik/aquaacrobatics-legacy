package com.fuzs.aquaacrobatics.mixinplugin;

import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

public enum TargetedMod implements ITargetMod {

    HODGEPODGE("com.mitchej123.hodgepodge.core.HodgepodgeCore", "hodgepodge"),
    ITEMPHYSIC("com.creativemd.itemphysic.asm.ItemPhysicEarlyMixins", "itemphysic"),

    ;

    private final TargetModBuilder builder;

    TargetedMod(String coreModClass, String modId) {
        this.builder = new TargetModBuilder().setCoreModClass(coreModClass)
            .setModId(modId);
    }

    @Override
    public TargetModBuilder getBuilder() {
        return builder;
    }
}
