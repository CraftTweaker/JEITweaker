package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.util.EnvironmentVerifier;
import com.blamejared.jeitweaker.common.util.JeiCategoriesState;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

@CraftTweakerPlugin(JeiTweakerConstants.MOD_ID + ":common")
public final class JeiTweakerCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    @Override
    public void initialize() {
        EnvironmentVerifier.scanAndReportEnvironment(JeiTweakerInitializer.get().jeiTweakerLogger());
        JeiTweakerInitializer.get().pluginManager().initializePlugins();
    }
    
    @Override
    public void registerCommands(final ICommandRegistrationHandler handler) {
        handler.registerDump(
                JeiTweakerConstants.rl("jei_categories").toString(),
                Component.translatable(JeiTweakerConstants.MOD_ID + ".command.description.dump.jei_categories"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    JeiCategoriesState.get()
                            .knownCategoryStates()
                            .forEach(CraftTweakerAPI.LOGGER::info);
                    CommandUtilities.send(
                            CommandUtilities.openingLogFile(
                                    Component.translatable(
                                            "crafttweaker.command.list.check.log",
                                            CommandUtilities.makeNoticeable(
                                                    Component.translatable("crafttweaker.command.misc.recipes")
                                            ),
                                            CommandUtilities.getFormattedLogFile()
                                    ).withStyle(ChatFormatting.GREEN)
                            ),
                            player
                    );
    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
}
