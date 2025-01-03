package hu.petrik.pasztorbotond_bevasarlobeadando;

public class Termekek {
    private int id;
    private String nev;
    private int egysegAr;
    private double mennyiseg;
    private String mertekegyseg;
    private double bruttoAr;



    public Termekek(int id, String nev, int egysegAr, double mennyiseg, String mertekegyseg) {
        this.id = id;
        this.nev = nev;
        this.egysegAr = egysegAr;
        this.mennyiseg = mennyiseg;
        this.mertekegyseg = mertekegyseg;
        CalcBruttoAr();
    }

    private void CalcBruttoAr(){
        this.bruttoAr = (double) Math.round(egysegAr * mennyiseg * 100) / 100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getEgysegAr() {
        return egysegAr;
    }

    public void setEgysegAr(int egysegAr) {
        this.egysegAr = egysegAr;
    }

    public double getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(double mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public double getBruttoAr() {
        CalcBruttoAr();
        return bruttoAr;
    }

    @Override
    public String toString() {
        return "Termekek{" +
                "nev='" + nev + '\'' +
                ", egysegAr=" + egysegAr +
                ", mennyiseg=" + mennyiseg +
                ", mertekegyseg='" + mertekegyseg + '\'' +
                ", bruttoAr=" + bruttoAr +
                '}';
    }
}
