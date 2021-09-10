package P1;

public class Adres {
    private Integer adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Integer reiziger_id;

    public Adres(Integer adres_id, String postcode, String huisnummer, String straat, String woonplaats, Integer reiziger_id) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public Integer getAdres_id() {
        return adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public Integer getReiziger_id() {
        return reiziger_id;
    }

    public void setAdres_id(Integer adres_id) {
        this.adres_id = adres_id;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setReiziger_id(Integer reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        return  "Adres #" + adres_id +
                ": " + straat + " " + huisnummer +
                ", " + postcode + " te " + woonplaats +
                ". Hoort bij reiziger #" + reiziger_id;
    }
}
