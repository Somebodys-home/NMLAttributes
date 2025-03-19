package io.github.Gabriel.NMLSkills.player.profileSystem;

import io.github.Gabriel.NMLSkills.player.attributeSystem.Attributes;

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
