package com.blamejared.jeitweaker.common.api.action;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;

/**
 * Represents an action carried out by JeiTweaker.
 *
 * <p>Every action that is carried out by JeiTweaker should extend this class, as it guarantees homogeneous handling
 * across the set of functionalities provided by the mod.</p>
 *
 * <p>The action is a {@link IRuntimeAction}, meaning that it will be executed on every game reload to account for JEI
 * plugin reloading. Moreover, the action will be applied solely if required according to
 * {@link JeiTweakerApi#shouldApplyAction()}.</p>
 *
 * @since 4.0.0
 */
public abstract class JeiTweakerAction implements IRuntimeAction {
    
    /**
     * Enqueues the given {@link JeiCommand} to be executed in the next JEI plugin reload.
     *
     * <p>Implementors should prefer this method to the equivalent {@link JeiTweakerApi#enqueueCommand(JeiCommand)} for
     * more expressive code and to favor future API changes as needed.</p>
     *
     * @param command The command that should be enqueued.
     * @param <T> The type of the command.
     * @see JeiCommand
     *
     * @since 4.0.0
     */
    protected final <T> void enqueueCommand(final JeiCommand<T> command) {
        JeiTweakerApi.get().enqueueCommand(command);
    }
    
    /**
     * {@inheritDoc}
     *
     * @implNote By default, this method returns {@link JeiTweakerApi#shouldApplyAction()}. Implementors should not need
     * to override this method.
     *
     * @since 4.0.0
     */
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source) {
        return JeiTweakerApi.get().shouldApplyAction();
    }
    
    // TODO("getSystemName")
    
}
