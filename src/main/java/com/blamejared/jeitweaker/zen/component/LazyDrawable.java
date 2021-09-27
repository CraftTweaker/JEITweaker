package com.blamejared.jeitweaker.zen.component;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;

final class LazyDrawable implements JeiDrawable {
    private volatile JeiDrawable delegate;
    private volatile IDrawable content;

    LazyDrawable(final JeiDrawable delegate) {

        this.delegate = delegate;
    }

    @Override
    public IDrawable getDrawable(final IGuiHelper helper) {

        if (this.content == null) {

            synchronized (this) {

                if (this.content == null) {

                    this.content = this.delegate.getDrawable(helper);
                    this.delegate = null;
                }
            }
        }

        return this.content;
    }
}
