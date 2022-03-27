package com.acikek.attribute_example.mixin;

import com.acikek.attribute_example.AttributeExample;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public @Nullable abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    // This method adds to default attributes to all living entities through a builder.
    // In order for our attribute to be recognized by an entity, we need to add it here.
    @Inject(method = "createLivingAttributes", at = @At(value = "RETURN"))
    private static void addCustomAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        // The return value is a List, so we can just add onto the reference.
        cir.getReturnValue().add(AttributeExample.GENERIC_JUMP_BOOST);
    }

    @Inject(method = "getJumpVelocity", at = @At(value = "RETURN"), cancellable = true)
    private void applyJumpBoost(CallbackInfoReturnable<Float> cir) {
        // The object containing all the attributes and modifiers on the current entity is called an EntityAttributeInstance.
        // If we didn't add our attribute to the builder in the previous method, this call would return null.
        EntityAttributeInstance instance = getAttributeInstance(AttributeExample.GENERIC_JUMP_BOOST);
        if (instance != null) {
            // EntityAttributeInstance#getValue computes the final value with all the modifiers applied.
            cir.setReturnValue(cir.getReturnValue() * (float) instance.getValue());
        }
    }
}
