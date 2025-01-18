package com.tiam.service;

public class Color {
    private String name;
    private String hex;

    public static final Color[] colors = {
        new Color("Coral", "#F97316"),       // Soft orange
        new Color("Plum", "#8B5CF6"),        // Gentle purple
        new Color("Azure", "#0284C7"),       // Light blue
        new Color("Graphite", "#4B5563"),    // Neutral gray
        new Color("Sky", "#0EA5E9"),         // Bright light blue
        new Color("Mint", "#10B981"),        // Softer, fresh green
        new Color("Amber", "#F59E0B"),       // Rich golden yellow
        new Color("Rose", "#F43F5E"),        // Vibrant pinkish-red
        new Color("Teal", "#14B8A6"),        // Fresh blue-green
        new Color("Flamingo", "#F87171"),    // Warm pastel coral-pink
        new Color("Charcoal", "#1F2937"),    // Dark gray with blue undertones
        new Color("Lilac", "#C084FC")        // Soft lilac
    };
    

    public Color(String name, String hex) {
        this.name = name;
        this.hex = hex;
    }

    public static Color getColorFromName(String name) {
        for (Color color : colors) {
            if (color.getName().equals(name)) {
                return color;
            }
        }
        return null;
    }


    public String getHex() {
        return hex;
    }

    public String getName() {
        return name;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setName(String name) {
        this.name = name;
    }

}
