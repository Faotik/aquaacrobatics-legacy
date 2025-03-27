package com.fuzs.aquaacrobatics.entity;

@SuppressWarnings("unused")
public class EntitySize {

    public final float width;
    public final float height;
    public final float yOffset;
    public final boolean fixed;

    public EntitySize(float widthIn, float heightIn, float yOffsetIn, boolean fixedIn) {
        this.width = widthIn;
        this.height = heightIn;
        this.yOffset = yOffsetIn;
        this.fixed = fixedIn;
    }

    public EntitySize scale(float factor) {
        return scale(factor, factor, factor);
    }

    public EntitySize scale(float widthFactor, float heightFactor, float yOffsetFactor) {
        return !this.fixed && (widthFactor != 1.0F || heightFactor != 1.0F) ? flexible(this.width * widthFactor, this.height * heightFactor, this.yOffset * yOffsetFactor) : this;
    }

    public static EntitySize flexible(float widthIn, float heightIn, float yOffsetIn) {
        return new EntitySize(widthIn, heightIn, yOffsetIn, false);
    }

    public static EntitySize fixed(float widthIn, float heightIn, float yOffsetIn) {
        return new EntitySize(widthIn, heightIn, yOffsetIn, true);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {

            return true;
        } else if (obj instanceof EntitySize) {

            EntitySize other = (EntitySize) obj;
            return this.width == other.width && this.height == other.height && this.yOffset == other.yOffset;
        }
        return false;
    }

    @Override
    public String toString() {
        return "EntityDimensions w=" + this.width + ", h=" + this.height + ", yOffset=" + this.yOffset + ", fixed=" + this.fixed;
    }
}
