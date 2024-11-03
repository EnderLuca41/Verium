package me.enderluca.verium;

import java.io.Serializable;

public enum ListingType {
    Blacklist,
    Whitelist;

//    /**
//     * Converts integer to enum, when {@code i} is out of bounds, returns default value for {@code ListingType}.
//     */
//    public static ListingType fromInt(int i){
//        return switch(i) {
//            case 0 -> Blacklist;
//            case 1 -> Whitelist;
//            default -> Blacklist;
//        };
//    }
//
//    public static int toInt(ListingType type){
//        return switch (type){
//            case Blacklist -> 0;
//            case Whitelist -> 1;
//        };
//    }
}
