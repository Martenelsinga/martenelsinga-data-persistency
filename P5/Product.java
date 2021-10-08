package Case;

import java.util.List;

public class Product {
    private Integer product_nummer;
    private String naam;
    private String beschrijving;
    private Double prijs;
    private List<OVChipkaart> ovkaarten;

    public Product(Integer product_nummer, String naam, String beschrijving, Double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Integer getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(Integer product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvkaarten() {
        return ovkaarten;
    }

    public void addOvkaart(OVChipkaart ovkaart){
        this.ovkaarten.add(ovkaart);
    }

    // deze leek me riskant, gezien een product is dat gedeeld wordt door zo ontzettend veel kaarten.
//    public void setOvkaarten(List<OVChipkaart> ovkaarten) {
//        this.ovkaarten = ovkaarten;
//    }

    public void removeOvkaart(OVChipkaart ovkaart){
        this.ovkaarten.remove(ovkaart);
    }

    @Override
    public String toString() {
        return "{" + naam + "}";
    }
}
