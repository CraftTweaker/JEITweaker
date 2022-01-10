package com.blamejared.jeitweaker.bridge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Links a {@link com.blamejared.jeitweaker.zen.category.JeiCategory} to the implementation, providing additional
 * information that is required by both JeiTweaker and JEI to properly handle the custom category.
 *
 * @since 1.1.0
 */
public interface JeiCategoryPluginBridge {

    /**
     * Initializes the interface used to present the recipe by adding slots to the given {@code group}.
     *
     * <p>Implementations of this interface must not discriminate on the type of the group obtained: rather the exact
     * same behavior must be carried out regardless of the parameters passed in.</p>
     *
     * @param group The group which the slots need to be added to.
     * @param coordinateFixer A {@link CoordinateFixer} that can adjust the slot positions based on the group type.
     * @param <G> The type of ingredients stored in the group.
     *
     * @since 1.1.0
     */
    <G> void initializeGui(final IGuiIngredientGroup<G> group, final CoordinateFixer coordinateFixer);

    /**
     * Gets the amount of input slots that are provided by the category.
     *
     * @return The amount of input slots provided by the category.
     *
     * @since 1.1.0
     */
    int getInputSlotsAmount();

    /**
     * Gets the amount of output slots that are provided by the category.
     *
     * @return The amount of output slots provided by the category.
     *
     * @since 1.1.0
     */
    int getOutputSlotsAmount();

    /**
     * Gets whether this category allows the shapeless marker to be displayed in the JEI interface.
     *
     * @return Whether the shapeless marker can be displayed in this category.
     *
     * @since 1.1.0
     */
    boolean allowShapelessMarker();

    /**
     * Gets whether this category allows recipes to specify and render arbitrary tooltips.
     *
     * @return Whether this category allows recipes to specify and render arbitrary tooltips.
     *
     * @since 1.1.0
     */
    boolean allowCustomTooltips();

    /**
     * Draws additional components that this category might require.
     *
     * <p>Additional components may be animations, additional non-ingredient slots, or text overlays.</p>
     *
     * @param poseStack The {@link PoseStack PoseStack} used for rendering.
     * @param mouseX The current x position of the cursor.
     * @param mouseY The current y position of the cursor.
     * @param guiHelper An instance of {@link IGuiHelper}.
     * @param graphicsConsumer A {@link Consumer} for {@link RecipeGraphics} which can be used to obtain graphical
     *                         information from the recipe that is currently being rendered.
     *
     * @since 1.1.0
     */
    void drawAdditionalComponent(final PoseStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer);

    /**
     * Gets a list of {@link Component} which can be used to render a tooltip at the current mouse position.
     *
     * <p>If the recipe {@linkplain #allowCustomTooltips() allows arbitrary tooltips}, those are automatically managed
     * by JeiTweaker: the bridge <strong>must not</strong> handle the display of arbitrary tooltips. This method is
     * solely responsible of gathering information for default tooltips that the category defines (e.g. energy amounts
     * or fuel consumption), which have to be present in all recipes.</p>
     *
     * @param x The current x position of the cursor.
     * @param y The current y position of the cursor.
     * @param helper An instance of {@link IGuiHelper}.
     * @param graphicsConsumer A {@link Consumer} for {@link RecipeGraphics} which can be used to obtain tooltip
     *                         information from the recipe that is currently being queried for tooltips.
     * @return A {@link List} containing {@link Component}s that should be displayed as tooltips in the current
     * location, or an {@linkplain java.util.Collections#emptyList() empty list} if no tooltip should be displayed.
     *
     * @since 1.1.0
     */
    List<Component> getTooltips(final double x, final double y, final IGuiHelper helper, final Consumer<RecipeGraphics> graphicsConsumer);
}
