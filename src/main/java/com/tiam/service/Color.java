package com.tiam.service;

public class Color {
    private String name;
    private String hex;

    public static final Color[] colors = {
        new Color("Mocha", "#2E2A24"),
        new Color("Almond", "#b3ae9e"), 
        new Color("Amethyst", "#9b27b0"), 
        new Color("SlateBlue", "#6d7aa9"), 
        new Color("MintGreen", "#3fcda2"), 
        new Color("AshGray", "#868686"), 
        new Color("RubyRed", "#cd3f6a"), 
        new Color("CoralPink", "#d76b66"), 
        new Color("DeepSea", "#104a7a")
    };


    public Color(String name, String hex) {
        this.name = name;
        this.hex = hex;
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
