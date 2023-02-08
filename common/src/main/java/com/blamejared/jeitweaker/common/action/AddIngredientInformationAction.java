package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class AddIngredientInformationAction<J, Z> extends JeiTweakerAction {
    private final JeiIngredient<J, Z> ingredient;
    private final List<Component> info;
    
    private AddIngredientInformationAction(final JeiIngredient<J, Z> ingredient, final List<Component> info) {
        this.ingredient = ingredient;
        this.info = info;
    }
    
    public static <J, Z> AddIngredientInformationAction<J, Z> of(final ZenJeiIngredient ingredient, final Component... info) {
        Objects.requireNonNull(ingredient, "ingredient");
        Objects.requireNonNull(info, "info");
        final JeiIngredient<J, Z> jeiIngredient = JeiIngredients.toJeiIngredient(ingredient);
        final List<Component> listedInfo = Arrays.asList(info);
        return new AddIngredientInformationAction<>(jeiIngredient, listedInfo);
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.RECIPE, this::addInfo));
    }
    
    @Override
    public boolean validate(final Logger logger) {
        if (this.info.isEmpty()) {
            logger.warn("Unable to add information for ingredient {} when it is empty", JeiIngredients.toCommandString(this.ingredient));
            return false;
        }
        return super.validate(logger);
    }
    
    @Override
    public String describe() {
        return "Adding information for ingredient %s to JEI".formatted(JeiIngredients.toCommandString(this.ingredient));
    }
    
    private void addInfo(final IRecipeRegistration registration) {
        final IIngredientType<J> type = JeiIngredients.jeiIngredientTypeOf(this.ingredient);
        final J ingredient = this.ingredient.jeiContent();
        final Component[] info = this.info.toArray(Component[]::new);
        registration.addIngredientInfo(ingredient, type, info);
    }
}
