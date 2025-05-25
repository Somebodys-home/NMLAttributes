package io.github.Gabriel.NMLAttributes.profileSystem;

import io.github.Gabriel.NMLAttributes.attributeSystem.Attributes;

public class Profile {
    private Attributes attributes;

    public Profile(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
