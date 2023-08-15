package com.blamejared.jeitweaker.gradle;

import masecla.modrinth4j.model.version.ProjectVersion;
import org.gradle.api.JavaVersion;

public final class Constants {
    public enum Release {
        ALPHA(net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_ALPHA, ProjectVersion.VersionType.ALPHA.name()),
        BETA(net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA, ProjectVersion.VersionType.BETA.name()),
        RELEASE(net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE, ProjectVersion.VersionType.RELEASE.name());
        
        private final String curseforge;
        private final String modrinth;
        
        Release(String curseforge, String modrinth) {
            
            this.curseforge = curseforge;
            this.modrinth = modrinth;
        }
        
        public String curseforge() {
            
            return curseforge;
        }
        
        public String modrinth() {
            
            return modrinth;
        }
    }
    
    public static final String MOD_ID = "jeitweaker";
    public static final String MOD_NAME = "JeiTweaker";
    public static final String MOD_AUTHOR = "Jared, TheSilkMiner";
    public static final String MOD_CURSE = "https://www.curseforge.com/minecraft/mc-mods/jeitweaker";
    public static final String MOD_CURSE_ID = "368718";
    public static final String MOD_MODRINTH_ID = "EiEOyeoL";
    public static final String MOD_GROUP = "com.blamejared.jeitweaker";
    public static final String MOD_VERSION = "8.0";
    public static final String MOD_AVATAR = "https://media.forgecdn.net/avatars/255/940/637203420213364761.png";

    public static final String GIT_FIRST_COMMIT = "12c42310f5b56b51ce36a5d1c7e6fdc00ead98c9";
    public static final String GIT_REPO = "https://github.com/CraftTweaker/JEITweaker";

    public static final String JAVA_VERSION = JavaVersion.VERSION_17.getMajorVersion();
    public static final String MINECRAFT_VERSION = "1.20.1";
    @SuppressWarnings("SpellCheckingInspection") public static final String CRAFTTWEAKER_VERSION = "14.0.12";
    @SuppressWarnings("SpellCheckingInspection") public static final String CRAFTTWEAKER_ANNOTATIONS_VERSION = "3.0.0.15";
    public static final String JEI_VERSION = "15.2.0.25";
    public static final String PARCHMENT_VERSION = "2023.08.13";
    public static final String FORGE_VERSION = "47.1.3";
    public static final String FABRIC_VERSION = "0.14.22";
    public static final String FABRIC_API_VERSION = "0.87.0+1.20.1";
    
    public static final Release FABRIC_RELEASE = Release.BETA;
    public static final Release FORGE_RELEASE = Release.BETA;
    
    private Constants() {}
}
