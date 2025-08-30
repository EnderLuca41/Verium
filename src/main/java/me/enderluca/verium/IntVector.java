package me.enderluca.verium;

import org.bukkit.util.Vector;

public record IntVector(int x, int y, int z) {
    public IntVector(Vector floatVector){
        this(floatVector.getBlockX(), floatVector.getBlockY(), floatVector.getBlockZ());
    }

    public Vector toFloatVector(){
        return new Vector(x, y, z);
    }

    @Override
    public String toString(){
        return x + "," + y + "," + z;
    }

    public static IntVector fromString(String str){
        String[] parts = str.split(",");
        if(parts.length != 3)
            throw new IllegalArgumentException("Invalid IntVector string: " + str);
        return new IntVector(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
