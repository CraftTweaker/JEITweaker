package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.commands.CTCommandCollectionEvent;
import com.blamejared.crafttweaker.impl.commands.CommandCaller;
import com.blamejared.jeitweaker.implementation.state.StateManager;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    
    @SubscribeEvent
    public void onCommandCollection(CTCommandCollectionEvent event) {
        
        // Cast required due to deprecation. TODO("Remove in 1.17")
        event.registerDump("jeiCategories", "Lists the different JEI categories", (CommandCaller) commandContext -> {
            
            CraftTweakerAPI.logDump("List of all known JEI categories: ");
            StateManager.INSTANCE.jeiGlobalState()
                    .getCurrentJeiCategories()
                    .stream()
                    .map(it -> "- " + it)
                    .sorted()
                    .forEach(CraftTweakerAPI::logDump);
            
            // TODO("Move to Translatable Text Components")
            final StringTextComponent message = new StringTextComponent(TextFormatting.GREEN + "Categories written to the log" + TextFormatting.RESET);
            commandContext.getSource().sendFeedback(message, true);
            return 0;
        });
    }
    
}
