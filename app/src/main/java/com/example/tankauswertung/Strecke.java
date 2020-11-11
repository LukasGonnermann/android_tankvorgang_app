package com.example.tankauswertung;

public class Strecke {
    private double distanz;
    enum Streckentyp {innerorts, kombiniert, ausserorts} //Umgeändert von int in enum
    private int tankstand; // 1-100[%]
    Streckentyp streckentyp;

    public Strecke(double pDistanz, Streckentyp pStreckentyp, int pTankstand) {
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

    public Streckentyp getStreckentyp() {
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

    public void setStreckentyp(Streckentyp pStreckentyp) {
        this.streckentyp = pStreckentyp;
    }

    public void streckeBearbeiten(double pDistanz, Streckentyp pStreckentyp, int pTankstand) {
        this.setDistanz(pDistanz);
        this.setStreckentyp(pStreckentyp);
        this.setTankstand(pTankstand);
    }
}
