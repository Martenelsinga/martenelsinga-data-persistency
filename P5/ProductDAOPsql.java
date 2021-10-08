package Case;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{
    Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Product product){
        try {
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?);";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, product.getProduct_nummer());
            prep.setString(2, product.getNaam());
            prep.setString(3, product.getBeschrijving());
            prep.setDouble(4, product.getPrijs());
            prep.executeQuery();
            for (OVChipkaart ovkaart : product.getOvkaarten()) {
                String query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, CURRENT_DATE);";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovkaart.getKaart_nummer());
                prep2.setInt(2, product.getProduct_nummer());
                prep2.setString(3, "actief");
                prep2.executeQuery();
            }
            return true;
            } catch(Exception e){
                if (e.getMessage().equals("No results were returned by the query.")) {
                    return false;
                }
                System.out.println(e.getMessage());
                return false;
            }
        }

    public boolean update(Product product){
        try {
            String query = "UPDATE product SET  naam =?, beschrijving =?, prijs =? WHERE procuct_nummer =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, product.getNaam());
            prep.setString(2, product.getBeschrijving());
            prep.setDouble(3, product.getPrijs());
            prep.setInt(4, product.getProduct_nummer());
            prep.executeQuery();
            for (OVChipkaart ovkaart : product.getOvkaarten()) {
                String query2 = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer =? AND product_nummer =?";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovkaart.getKaart_nummer());
                prep2.setInt(2, product.getProduct_nummer());
                ResultSet rs = prep2.executeQuery();
                String query3;
                if (rs.next()) {
                    query3 = "UPDATE ov_chipkaart_product SET kaart_nummer =?, product_nummer =?, last_update = CURRENT_DATE;";
                } else {
                    query3 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, last_update) VALUES (?, ?, CURRENT_DATE);";
                }
                PreparedStatement prep3 = conn.prepareStatement(query3);
                prep3.setInt(1, ovkaart.getKaart_nummer());
                prep3.setInt(2, product.getProduct_nummer());
                prep3.executeQuery();
            }
            return true;
        } catch (Exception e) {
            if (e.getMessage().equals("No results were returned by the query.")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(Product product){
        try {
            String query = "DELETE FROM product WHERE product_nummer =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, product.getProduct_nummer());
            prep.executeQuery();
            String query2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?;";
            PreparedStatement prep2 = conn.prepareStatement(query2);
            prep2.setInt(1, product.getProduct_nummer());
            prep2.executeQuery();
            return true;
        } catch (Exception e) {
            if (e.getMessage().equals("No results were returned by the query.")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovkaart){
        try {
            String query = "SELECT p.* FROM product p WHERE p.product_nummer IN (SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = ?)";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, ovkaart.getKaart_nummer());
            ResultSet rs = prep.executeQuery();
            List<Product> producten = new ArrayList<>();
            while(rs.next()){
                Product prod = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs"));
                producten.add(prod);
            }
            return producten;
        }catch (Exception e) {
            if (e.getMessage().equals("No results were returned by the query.")) {
                return null;
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Product> findAll(){
        try {
            String query = "SELECT p.* FROM product p;";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            List<Product> producten = new ArrayList<>();
            while(rs.next()){
                Product prod = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs"));
                producten.add(prod);
            }
            return producten;
        }catch (Exception e) {
            if (e.getMessage().equals("No results were returned by the query.")) {
                return null;
            }
            System.out.println(e.getMessage());
            return null;
        }
    }
}
