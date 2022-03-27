package com.acikek.attribute_example;

import com.acikek.attribute_example.command.JumpStickCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AttributeExample implements ModInitializer {

    // The 'fallback' is the default attribute value before any modifiers are applied.
    // In a clamped attribute, the 'min' and 'max' values will control the boundaries of the final value.
    // A tracked attribute will be synchronized with the client.
    public static final EntityAttribute GENERIC_JUMP_BOOST = new ClampedEntityAttribute("attribute.name.generic_jump_boost", 1.0, 0.0, 2.0).setTracked(true);

    @Override
    public void onInitialize() {
        Registry.register(Registry.ATTRIBUTE, new Identifier("attribute_example", "generic.jump_boost"), GENERIC_JUMP_BOOST);
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> new JumpStickCommand().register(dispatcher));
    }
}
