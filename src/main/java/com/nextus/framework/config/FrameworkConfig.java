package com.nextus.framework.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/${env}.properties",
        "classpath:config/default.properties"
})
public interface FrameworkConfig extends Config {

    @Key("base.url")
    @DefaultValue("https://localhost")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chromium")
    String browser();

    @Key("headless")
    @DefaultValue("true")
    boolean isHeadless();

    @Key("record.video")
    @DefaultValue("false")
    boolean recordVideo();

    @Key("screenshot.on.failure")
    @DefaultValue("true")
    boolean takeScreenshotOnFailure();
}