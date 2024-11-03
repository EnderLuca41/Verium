package me.enderluca.verium;

import java.util.ArrayList;
import java.util.List;

/**
 * Blacklist/Whitelist
 */
public class AccessList<T> {

    private final List<T> list;
    private ListingType type;

    public AccessList(ListingType type){
        list = new ArrayList<>();
        this.type = type;
    }

    public AccessList(ListingType type, List<T> list){
        this.list = list;
        this.type = type;
    }


    public void setType(ListingType val){
        type = val;
    }

    public ListingType getType(){
        return type;
    }


    public boolean hasAccess(T object){
        boolean contains = list.contains(object);

        return switch (type) {
            case Blacklist -> !contains;
            case Whitelist -> contains;
        };
    }

    public boolean add(T object){
        boolean contains = list.contains(object);

        if(contains)
            return false;

        list.add(object);
        return true;
    }

    public boolean remove(T object){
        return list.remove(object);
    }

    public void removeAt(int index){
        list.remove(index);
    }

    /**
     * Returns the underlying list of the data structure
     */
    public List<T> getList(){
        return list;
    }
}
