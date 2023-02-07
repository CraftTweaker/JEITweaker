package com.blamejared.jeitweaker.common.util;

import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public final class EnvironmentVerifier {
    private record ModList(Set<String> names, String special) {
        ModList(final Set<String> names) {
            this(names, null);
        }
        
        ModList(final String... mods) {
            this(Set.of(mods));
        }
        
        boolean matches() {
            for (final String name : this.names()) {
                if (!PlatformBridge.INSTANCE.isModLoaded(name)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private enum EnvironmentSupport {
        FULL(Level.INFO, "the combination of mods %s is fully supported%s"),
        PARTIAL(Level.WARN, "the combination of mods %s is partially supported: some features might be missing or performance might be degraded%s"),
        NONE(Level.ERROR, "the combination of mods %s is unsupported: no features will behave correctly%s");
        
        private final Level level;
        private final String formatMessage;
        
        EnvironmentSupport(final Level level, final String formatMessage) {
            this.level = level;
            this.formatMessage = formatMessage;
        }
        
        Level level() {
            return this.level;
        }
        
        String message(final ModList mods) {
            return this.formatMessage.formatted(mods.names(), mods.special() == null? "" : ("; " + mods.special()));
        }
    }
    
    private record ModEnvironment(ModList list, EnvironmentSupport support) {
        boolean matches() {
            return this.list().matches();
        }
        
        void report(final Logger logger) {
            final String message = "JEITweaker Environmental Check: %s".formatted(this.support().message(this.list()));
            logger.log(this.support().level(), message);
        }
    }
    
    private static final String JEI = "jei";
    @SuppressWarnings("SpellCheckingInspection") private static final String REI = "roughlyenoughitems";
    private static final String REI_PC = "rei_plugin_compatibilities";
    
    private static final List<ModEnvironment> ENVIRONMENTS = List.of(
            new ModEnvironment(new ModList(JEI), EnvironmentSupport.FULL),
            new ModEnvironment(new ModList(Set.of(REI, REI_PC)/*, "consider using ReiTweaker instead"*/), EnvironmentSupport.PARTIAL),
            new ModEnvironment(new ModList(Set.of(REI)/*, "consider using ReiTweaker instead"*/), EnvironmentSupport.NONE),
            new ModEnvironment(new ModList(), EnvironmentSupport.NONE)
    );
    
    private EnvironmentVerifier() {}
    
    public static void scanAndReportEnvironment(final Logger reportTo) {
        for (final ModEnvironment environment : ENVIRONMENTS) {
            if (environment.matches()) {
                environment.report(reportTo);
                break;
            }
        }
    }
}
