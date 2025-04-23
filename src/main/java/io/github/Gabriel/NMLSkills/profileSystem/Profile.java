package io.github.Gabriel.NMLSkills.profileSystem;

import io.github.Gabriel.NMLSkills.attributeSystem.Attributes;

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
