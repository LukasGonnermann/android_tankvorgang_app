package com.example.tankauswertung;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SettingsTest {
    Settings settings;

    @Before
    public void setUp() {
        this.settings = new Settings();
    }

    @Test
    public void changeDarkMode() {
        assertEquals(settings.getDarkModeSafe(), Settings.DarkModeStatus.SYSTEM);
        settings.setDarkModeSafe(Settings.DarkModeStatus.AN);
        assertEquals(settings.getDarkModeSafe(), Settings.DarkModeStatus.AN);
        settings.setDarkModeSafe(Settings.DarkModeStatus.AUS);
        assertEquals(settings.getDarkModeSafe(), Settings.DarkModeStatus.AUS);
        settings.setDarkModeSafe(Settings.DarkModeStatus.SYSTEM);
        assertEquals(settings.getDarkModeSafe(), Settings.DarkModeStatus.SYSTEM);
    }
}
