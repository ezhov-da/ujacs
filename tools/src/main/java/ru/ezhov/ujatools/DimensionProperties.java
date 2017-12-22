package ru.ezhov.ujatools;

import java.awt.Dimension;

public class DimensionProperties {
    public final static Dimension getDimension(String propertyDimension) {
        String[] arr = propertyDimension.split(",");
        Dimension dimension = new Dimension(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
        return dimension;
    }
}
