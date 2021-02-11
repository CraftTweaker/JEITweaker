package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.commands.CTCommandCollectionEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    
    @SubscribeEvent
    public void onCommandCollection(CTCommandCollectionEvent event) {
        
        event.registerDump("jeiCategories", "Lists the different JEI categories", commandContext -> {
            
            CraftTweakerAPI.logDump("List of all known JEI categories: ");
            for(ResourceLocation recipeCategory : JEIAddonPlugin.JEI_CATEGORIES) {
                CraftTweakerAPI.logDump("- %s", recipeCategory.toString());
            }
            
            final StringTextComponent message = new StringTextComponent(TextFormatting.GREEN + "Categories written to the log" + TextFormatting.RESET);
            commandContext.getSource().sendFeedback(message, true);
            return 0;
        });
    }
    
}
