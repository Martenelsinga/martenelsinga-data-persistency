package Case;

import java.sql.Date;

public class OVChipkaart {
    private Integer kaart_nummer;
    private Date geldig_tot;
    private Integer klasse;
    private Double saldo;
    private Integer reiziger_id;
    private Reiziger reiziger;

    public OVChipkaart(Integer kaart_nummer, Date geldig_tot, Integer klasse, Double saldo, Integer reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public Integer getKaart_nummer() {
        return kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public Integer getKlasse() {
        return klasse;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Integer getReiziger_id() {
        return reiziger_id;
    }

    public void setKaart_nummer(Integer kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(Integer klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setReiziger_id(Integer reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setReiziger(Reiziger reiziger){
        this.reiziger = reiziger;
    }

    public Reiziger getReiziger(){
        return reiziger;
    }

    public boolean equals(OVChipkaart other){
        return this.getKaart_nummer() == other.getKaart_nummer();
    }
}
