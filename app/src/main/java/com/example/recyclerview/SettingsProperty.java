package com.hlgkaifu.recyclerview;

public class SettingsProperty {

    private String property, value;
    private boolean active, toggleable;

    public SettingsProperty(String property, String value, boolean active, boolean toggleable) {
        this.property = property;
        this.value = value;
        this.active = active;
        this.toggleable = toggleable;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public void setToggleable(boolean toggleable) {
        this.toggleable = toggleable;
    }
}
