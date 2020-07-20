package com.example.dictionary.model;

public class TuVung {
    private String tuTA;
    private String tuTV;
    private String phienAm;

    public TuVung() {
    }

    public TuVung(String tuTA, String tuTV) {
        this.tuTA = tuTA;
        this.tuTV = tuTV;
    }

    public TuVung(String tuTA, String tuTV, String phienAm) {
        this.tuTA = tuTA;
        this.tuTV = tuTV;
        this.phienAm = phienAm;
    }

    public String getTuTA() {
        return tuTA;
    }

    public void setTuTA(String tuTA) {
        this.tuTA = tuTA;
    }

    public String getTuTV() {
        return tuTV;
    }

    public void setTuTV(String tuTV) {
        this.tuTV = tuTV;
    }

    public String getPhienAm() {
        return phienAm;
    }

    public void setPhienAm(String phienAm) {
        this.phienAm = phienAm;
    }

    @Override
    public String toString() {
        return "tuTA= " + tuTA + '\n' +
                "tuTV= " + tuTV + '\n' +
                "phienAm= " + phienAm + " |||";
    }
}
