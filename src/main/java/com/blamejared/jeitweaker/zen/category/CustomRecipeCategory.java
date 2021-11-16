package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.CustomRecipeCategoryBridge;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Document("mods/JEI/Category/Custom")
@ZenCodeType.Name("mods.jei.category.Custom")
@ZenRegister
public final class CustomRecipeCategory extends SimpleJeiCategory {
    
    public static final class Coordinates {
        private final int x;
        private final int y;
        
        private Coordinates(final int x, final int y) {
            
            this.x = x;
            this.y = y;
        }
        
        private static Coordinates of(final int x, final int y) {
    
            if (x < 0 || y < 0) {
        
                throw new IllegalArgumentException("Coordinates (" + x + ", " + y + ") are out of bounds");
            }
            
            return new Coordinates(x, y);
        }
        
        public int x() {
            
            return this.x;
        }
        
        public int y() {
            
            return this.y;
        }
    }
    
    public static final class SlotData {
        
        private final int index;
        private final Coordinates coordinates;
        private final boolean input;
        
        private SlotData(final int index, final Coordinates coordinates, final boolean input) {
            
            this.index = index;
            this.coordinates = coordinates;
            this.input = input;
        }
        
        private static SlotData of(final int index, final int x, final int y, final boolean input) {
            
            if (index < 0) {
                
                throw new IllegalArgumentException("Unable to create slot with index " + index + " because it's negative");
            }
            
            return new SlotData(index, Coordinates.of(x, y), input);
        }
        
        public int index() {
            
            return this.index;
        }
        
        public Coordinates coordinates() {
            
            return this.coordinates;
        }
        
        public boolean isInput() {
            
            return this.input;
        }
    }
    
    public static final class DrawableData {
        
        private final JeiDrawable drawable;
        private final Coordinates coordinates;
        
        private DrawableData(final JeiDrawable drawable, final Coordinates coordinates) {
            
            this.drawable = drawable;
            this.coordinates = coordinates;
        }
        
        private static DrawableData of(final JeiDrawable drawable, final int x, final int y) {
    
            Objects.requireNonNull(drawable, "Drawable must not be null");
            return new DrawableData(drawable, Coordinates.of(x, y));
        }
        
        public JeiDrawable drawable() {
            
            return this.drawable;
        }
        
        public Coordinates coordinates() {
        
            return this.coordinates;
        }
    }
    
    public static final class TextData {
        
        private final Coordinates topLeft;
        private final Coordinates activeArea;
        private final List<MCTextComponent> text;
    
        private TextData(final Coordinates topLeft, final Coordinates activeArea, final List<MCTextComponent> text) {
            
            this.topLeft = topLeft;
            this.activeArea = activeArea;
            this.text = text;
        }
        
        private static TextData of(final int x, final int y, final int w, final int h, final List<MCTextComponent> text) {
            
            Objects.requireNonNull(text, "No text specified");
            return new TextData(Coordinates.of(x, y), w < 0 && h < 0? null : Coordinates.of(w, h), text);
        }
    
        public Coordinates topLeft() {
        
            return this.topLeft;
        }
    
        public Coordinates activeArea() {
        
            return this.activeArea;
        }
    
        public List<MCTextComponent> text() {
        
            return this.text;
        }
    
    }
    
    private final Int2ObjectMap<SlotData> slots;
    private final List<DrawableData> drawables;
    private final List<TextData> tooltips;
    private final List<TextData> textAreas;
    
    private JeiDrawable background;
    private boolean canBeShapeless;
    
    public CustomRecipeCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.slots = new Int2ObjectRBTreeMap<>();
        this.drawables = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.textAreas = new ArrayList<>();
        this.background = null;
        this.canBeShapeless = false;
    }
    
    @ZenCodeType.Setter("background")
    public void setBackground(final JeiDrawable background) {
        
        this.background = background;
    }
    
    @ZenCodeType.Setter("canHaveShapelessMarker")
    public void setCanHaveShapelessMarker(final boolean canHaveShapelessMarker) {
        
        this.canBeShapeless = canHaveShapelessMarker;
    }
    
    @ZenCodeType.Method("addSlot")
    public void addSlot(final int index, final int x, final int y, final boolean isInput) {
        
        if (this.slots.get(index) != null) {
            
            throw new IllegalArgumentException("Slot index " + index + " was already registered");
        }
        
        this.slots.put(index, SlotData.of(index, x, y, isInput));
    }
    
    @ZenCodeType.Method("addDrawable")
    public void addDrawable(final int x, final int y, final JeiDrawable drawable) {
        
        this.drawables.add(DrawableData.of(drawable, x, y));
    }
    
    @ZenCodeType.Method("addTooltip")
    public void addTooltip(final int x, final int y, final int w, final int h, final MCTextComponent... text) {
        
        this.tooltips.add(TextData.of(x, y, w, h, Collections.unmodifiableList(Arrays.asList(text))));
    }
    
    @ZenCodeType.Method("addText")
    public void addText(final int x, final int y, final MCTextComponent text) {
        
        this.textAreas.add(TextData.of(x, y, -1, -1, Collections.singletonList(text)));
    }
    
    @Override
    public JeiDrawable background() {
        
        return Objects.requireNonNull(this.background, "Background was not set");
    }
    
    @Override
    public Supplier<JeiCategoryPluginBridge> getBridgeCreator() {
        
        return () -> new CustomRecipeCategoryBridge(
                Collections.unmodifiableCollection(this.slots.values()),
                Collections.unmodifiableCollection(this.drawables),
                Collections.unmodifiableCollection(this.tooltips),
                Collections.unmodifiableCollection(this.textAreas),
                this.canBeShapeless
        );
    }
    
}
