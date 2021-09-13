package P1;

import java.sql.Date;
import java.util.*;

public class Reiziger{
    private Integer reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovchipkaarten;
    public Reiziger(Integer reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
    public Integer getId() {
        return reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setId(Integer reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setAdres(Adres adres){ this.adres = adres; }

    public Adres getAdres() { return adres; }

    @Override
    public String toString() {
        if(tussenvoegsel == null){
            return "#" + reiziger_id +
                    ": " + voorletters + "." +
                    " " + achternaam + " (" + geboortedatum +
                    ") woont op " + adres.getStraat() + " te " + adres.getWoonplaats();

        }
        return  "#" + reiziger_id +
                ": " + voorletters + "." +
                " " + tussenvoegsel + " " + achternaam + " (" + geboortedatum +
                ") woont op " + adres.getStraat() + " te " + adres.getWoonplaats();
    }
}
