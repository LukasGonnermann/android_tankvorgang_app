package com.example.tankauswertung;

public class Strecke {
    private double distanz;
    private int streckentyp; //0: Innerorts, 1: kombiniert, 2: ausserorts
    private int tankstand; // 1-100[%]

    public Strecke(double pDistanz, int pStreckentyp, int pTankstand) {
        distanz = pDistanz;
        streckentyp = pStreckentyp;
        tankstand = pTankstand;
    }

    public int getTankstand() {
        return tankstand;
    }

    public double getDistanz() {
        return distanz;
    }

    public int getStreckentyp() {
        return streckentyp;
    }

    public boolean setTankstand(int tankstand) {
        if (tankstand >= 0 && tankstand <= 100) {
            this.tankstand = tankstand;
            return true;
        } else
            return false;
    }

    public void setDistanz(double distanz) {
        this.distanz = distanz;
    }

    public boolean setStreckentyp(int streckentyp) {
        if (streckentyp >= 0 && streckentyp <= 2) {
            this.streckentyp = streckentyp;
            return true;
        } else
            return false;
    }

    public void streckeBearbeiten(double pDistanz, int pStreckentyp, int pTankstand) {
        this.setDistanz(pDistanz);
        this.setStreckentyp(pStreckentyp);
        this.setTankstand(pTankstand);
    }
}
