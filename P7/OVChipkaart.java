package Case;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="ov_chipkaart")
public class OVChipkaart {
    @Id
    private Integer kaart_nummer;
    private Date geldig_tot;
    private Integer klasse;
    private Double saldo;
    @ManyToOne
    @JoinColumn(name="reiziger_id")
    private Reiziger reiziger;
    @ManyToMany
    @JoinTable(name="ov_chipkaart_product",
            joinColumns = { @JoinColumn(name="kaart_nummer")},
            inverseJoinColumns = { @JoinColumn(name = "product_nummer")}
    )
    private List<Product> producten;

    public OVChipkaart(){}
    public OVChipkaart(Integer kaart_nummer, Date geldig_tot, Integer klasse, Double saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;

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

    public void setReiziger(Reiziger reiziger){
        this.reiziger = reiziger;
    }

    public Reiziger getReiziger(){
        return reiziger;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product){
        if(this.producten == null){
            this.producten = new ArrayList<Product>();
        }
        this.producten.add(product);
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void removeProduct(Product product){
        this.producten.remove(product);
    }

    public boolean equals(OVChipkaart other){
        return this.getKaart_nummer() == other.getKaart_nummer();
    }

    @Override
    public String toString() {
        StringBuilder producten = new StringBuilder();
        if(this.producten.size() != 0) {
            for (Product product : this.getProducten()) {
                producten.append(" ").append(product.toString());
            }
                return "OVChipkaart{" +
                        "kaart_nummer=" + kaart_nummer +
                        ", geldig_tot=" + geldig_tot +
                        ", klasse=" + klasse +
                        ", saldo=" + saldo +
                        "}, heeft producten " +
                        producten;
        }
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                "}";
    }
}
