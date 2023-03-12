package com.blamejared.jeitweaker.gradle;

import org.gradle.api.JavaVersion;

public final class Constants {
    public enum Release {
        ALPHA,
        BETA,
        RELEASE
    }
    
    public static final String MOD_ID = "jeitweaker";
    public static final String MOD_NAME = "JeiTweaker";
    public static final String MOD_AUTHOR = "Jared, TheSilkMiner";
    public static final String MOD_CURSE = "https://www.curseforge.com/minecraft/mc-mods/jeitweaker";
    public static final String MOD_CURSE_ID = "368718";
    public static final String MOD_GROUP = "com.blamejared.jeitweaker";
    public static final String MOD_VERSION = "4.0";
    public static final String MOD_AVATAR = "https://media.forgecdn.net/avatars/255/940/637203420213364761.png";

    public static final String GIT_FIRST_COMMIT = "12c42310f5b56b51ce36a5d1c7e6fdc00ead98c9";
    public static final String GIT_REPO = "https://github.com/CraftTweaker/JEITweaker";

    public static final String JAVA_VERSION = JavaVersion.VERSION_17.getMajorVersion();
    public static final String MINECRAFT_VERSION = "1.19.2";
    @SuppressWarnings("SpellCheckingInspection") public static final String CRAFTTWEAKER_VERSION = "10.1.36";
    @SuppressWarnings("SpellCheckingInspection") public static final String CRAFTTWEAKER_ANNOTATIONS_VERSION = "3.0.0.11";
    public static final String JEI_VERSION = "11.5.2.1007";
    public static final String PARCHMENT_VERSION = "2022.10.09";
    public static final String FORGE_VERSION = "43.2.4";
    public static final String FABRIC_VERSION = "0.14.13";
    public static final String FABRIC_API_VERSION = "0.73.0+1.19.2";
    
    public static final Release FABRIC_RELEASE = Release.BETA;
    public static final Release FORGE_RELEASE = Release.BETA;
    
    private Constants() {}
}
