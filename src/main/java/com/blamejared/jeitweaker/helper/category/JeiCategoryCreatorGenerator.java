package com.blamejared.jeitweaker.helper.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

enum JeiCategoryCreatorGenerator {
    INSTANCE;
    
    private static final String OBJECT_INTERNAL_NAME = Type.getInternalName(Object.class);
    private static final String CATEGORY_CREATOR_INTERNAL_NAME = Type.getInternalName(JeiCategoryCreator.class);
    
    private static final String OF_DESCRIPTOR = Type.getMethodDescriptor(
            Type.getType(JeiCategory.class),
            Type.getType(ResourceLocation.class), Type.getType(MCTextComponent.class), Type.getType(JeiDrawable.class), Type.getType(RawJeiIngredient[].class)
    );
    
    private static final String TARGET_DESCRIPTOR = Type.getMethodDescriptor(
            Type.VOID_TYPE,
            Type.getType(ResourceLocation.class), Type.getType(MCTextComponent.class), Type.getType(JeiDrawable.class), Type.getType(RawJeiIngredient[].class)
    );
    
    private static final JeiCategoryCreatorLoader LOADER = new JeiCategoryCreatorLoader(JeiCategoryCreatorGenerator.class.getClassLoader());
    
    <T extends JeiCategory> Optional<JeiCategoryCreator<T>> generate(final Class<T> typeToken) {
        
        return this.lookup(typeToken)
                .map(this::generate)
                .map(this::instantiate);
    }
    
    private <T extends JeiCategory> Optional<Constructor<T>> lookup(final Class<T> typeToken) {
        
        try {
    
            return Optional.of(typeToken.getConstructor(ResourceLocation.class, MCTextComponent.class, JeiDrawable.class, RawJeiIngredient[].class));
        } catch (final NoSuchMethodException e) {
    
            CraftTweakerAPI.logThrowing(
                    "Unable to generate category creator for class {} because no compatible constructor was found",
                    e,
                    typeToken.getName()
            );
            return Optional.empty();
        }
    }
    
    private <T extends JeiCategory> String generate(final Constructor<T> constructor) {
        
        final String className = String.format(
                "%s$%s$%s",
                JeiCategoryCreator.class.getName(),
                constructor.getDeclaringClass().getSimpleName(),
                Integer.toUnsignedString(ThreadLocalRandom.current().nextInt())
        );
        final String classInternalName = className.replace('.', '/');
        final String targetClassInternalName = constructor.getDeclaringClass().getName().replace('.', '/');
        
        final byte[] classData = this.generate(classInternalName, targetClassInternalName);
        
        LOADER.addClass(className, classData);
        return className;
    }
    
    private byte[] generate(final String thisInternalName, final String targetInternalName) {
        
        final ClassWriter writer = new ClassWriter(0);
        this.createHeader(writer, thisInternalName);
        this.createConstructor(writer);
        this.createOverride(writer, targetInternalName);
        return this.finish(writer);
    }
    
    private void createHeader(final ClassWriter writer, final String thisInternalName) {
        
        final int version = Opcodes.V1_8;
        final int access = Opcodes.ACC_PUBLIC | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_SUPER;
        writer.visit(version, access, thisInternalName, null, OBJECT_INTERNAL_NAME, new String[] { CATEGORY_CREATOR_INTERNAL_NAME });
        //noinspection SpellCheckingInspection
        writer.visitSource(".dyngen", null);
    }
    
    private void createConstructor(final ClassWriter writer) {
        
        final MethodVisitor init = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        init.visitCode();
        init.visitVarInsn(Opcodes.ALOAD, 0);
        init.visitMethodInsn(Opcodes.INVOKESPECIAL, OBJECT_INTERNAL_NAME, "<init>", "()V", false);
        init.visitInsn(Opcodes.RETURN);
        init.visitMaxs(1, 1);
        init.visitEnd();
    }
    
    private void createOverride(final ClassWriter writer, final String targetName) {
        
        final MethodVisitor of = writer.visitMethod(Opcodes.ACC_PUBLIC, "of", OF_DESCRIPTOR, null, null);
        of.visitCode();
        of.visitTypeInsn(Opcodes.NEW, targetName);
        of.visitInsn(Opcodes.DUP);
        of.visitVarInsn(Opcodes.ALOAD, 1);
        of.visitVarInsn(Opcodes.ALOAD, 2);
        of.visitVarInsn(Opcodes.ALOAD, 3);
        of.visitVarInsn(Opcodes.ALOAD, 4);
        of.visitMethodInsn(Opcodes.INVOKESPECIAL, targetName, "<init>", TARGET_DESCRIPTOR, false);
        of.visitInsn(Opcodes.ARETURN);
        of.visitMaxs(6, 5);
        of.visitEnd();
    }
    
    private byte[] finish(final ClassWriter writer) {
        
        writer.visitEnd();
        return writer.toByteArray();
    }
    
    @SuppressWarnings("unchecked")
    private <T extends JeiCategory> JeiCategoryCreator<T> instantiate(final String name) {
        
        try {
            
            return (JeiCategoryCreator<T>) LOADER.loadClass(name).newInstance();
        } catch(final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            
            throw new RuntimeException("A critical error was encountered when initializing category creator " + name, e);
        }
    }
    
}
