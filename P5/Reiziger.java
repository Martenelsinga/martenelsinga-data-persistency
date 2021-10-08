package Case;

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

    public List<OVChipkaart> getOvchipkaarten() { return ovchipkaarten; }

    public void setOvchipkaarten(List<OVChipkaart> ovkaarten){ this.ovchipkaarten = ovkaarten; }

    public boolean addOvchipkaart(OVChipkaart ovkaart){
        if(this.ovchipkaarten == null){
            List<OVChipkaart> ovkaarten = new ArrayList<OVChipkaart>();
            ovkaarten.add(ovkaart);
            this.setOvchipkaarten(ovkaarten);
            return true;
        }
        else if(this.ovchipkaarten.contains(ovkaart)){
            return false;
        }
        this.ovchipkaarten.add(ovkaart);
        return true;
    }

    @Override
    public String toString() {
        String returner = "#" + reiziger_id +
                ": " + voorletters + "." + " ";
        if (tussenvoegsel != null) {
            returner += tussenvoegsel + " ";
        }
        returner += achternaam + " (" + geboortedatum + ")";
        if (adres != null) {
            returner += ", woont op " + adres.getStraat() + " " + adres.getHuisnummer() + " te " + adres.getWoonplaats();
        }
        if(ovchipkaarten != null) {
            if (!ovchipkaarten.isEmpty()) {
                returner += ", heeft de volgende kaarten: ";
                for (OVChipkaart ovkaart : ovchipkaarten) {
                    returner += ovkaart.toString() + " ";
                }
            }
        }
        return returner;
    }
}
