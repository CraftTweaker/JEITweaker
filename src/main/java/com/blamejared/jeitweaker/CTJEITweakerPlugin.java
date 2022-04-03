package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;

@CraftTweakerPlugin("jeitweaker:default")
public class CTJEITweakerPlugin implements ICraftTweakerPlugin {
    
    @Override
    public void registerCommands(ICommandRegistrationHandler handler) {
        
        if(!ModList.get().isLoaded("jei")) {
            return;
        }
        handler.registerDump("jei_categories", new TranslatableComponent("jeitweaker.command.description.dump.jei_categories"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            CraftTweakerAPI.LOGGER.info("List of all known JEI categories: ");
            StateManager.INSTANCE.jeiGlobalState()
                    .getCurrentJeiCategories()
                    .stream()
                    .map(it -> "- " + it)
                    .sorted()
                    .forEach(CraftTweakerAPI.LOGGER::info);
            
            CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("jeitweaker.command.misc.categories")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
            
            return Command.SINGLE_SUCCESS;
        }));
    }
    
}
