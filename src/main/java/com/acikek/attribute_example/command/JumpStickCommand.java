package com.acikek.attribute_example.command;

import com.acikek.attribute_example.AttributeExample;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.UUID;

public class JumpStickCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack stack = new ItemStack(Items.STICK);
        // Adding a modifier for the attribute can be done through NBT or with this method.
        // It takes the attribute itself, a modifier for that attribute, and a slot that the modifier applies to.
        stack.addAttributeModifier(
                AttributeExample.GENERIC_JUMP_BOOST,
                new EntityAttributeModifier(
                        // The UUID in the first parameter is the "source" of the modifier.
                        // If you wanted to retrieve this modifier back from the stack/entity, you should store this in a variable.
                        // In any case, though, all it needs to be is unique.
                        UUID.fromString("de17612a-adec-11ec-b909-0242ac120002"),
                        // This modifier "name" is used in NBT and potion tooltips.
                        "Jump stick modifier",
                        // The modifier lets you specify the value and also how to apply it.
                        // In this case, we're adding 0.5 to the base value.
                        0.5,
                        EntityAttributeModifier.Operation.ADDITION
                ),
                EquipmentSlot.MAINHAND
        );
        context.getSource().getPlayer().giveItemStack(stack);
        return 0;
    }

    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("jumpstick").executes(this));
    }
}
