package me.enderluca.verium;

public class GameRulesConfig {
    private boolean noHunger;
    private boolean pvp;


    public void setNoHunger(boolean noHunger) {
        this.noHunger = noHunger;
    }
    public boolean getNoHunger(){
        return noHunger;
    }

    public void setPvp(boolean pvp){
        this.pvp = pvp;
    }
    public boolean getPvp(){
        return pvp;
    }
}
