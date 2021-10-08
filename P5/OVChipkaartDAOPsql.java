package Case;

import java.util.*;
import java.sql.*;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    Connection conn;
    ReizigerDA rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(OVChipkaart ovchipkaart){
        try {
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, ovchipkaart.getKaart_nummer());
            prep.setDate(2, ovchipkaart.getGeldig_tot());
            prep.setInt(3, ovchipkaart.getKlasse());
            prep.setDouble(4, ovchipkaart.getSaldo());
            prep.setInt(5, ovchipkaart.getReiziger_id());
            prep.executeQuery();
            for(Product product : ovchipkaart.getProducten()){
                String query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, CURRENT_DATE);";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovchipkaart.getKaart_nummer());
                prep2.setInt(2, product.getProduct_nummer());
                prep2.setString(3, "actief");
                prep2.executeQuery();
            }
            return true;
        } catch (Exception e) {
            if(e.getMessage().equals("No results were returned by the query.")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(OVChipkaart ovchipkaart){
        try {
            String query = "UPDATE ov_chipkaart SET  geldig_tot =?, klasse =?, saldo =?, reiziger_id =? WHERE kaart_nummer =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setDate(1, ovchipkaart.getGeldig_tot());
            prep.setInt(2, ovchipkaart.getKlasse());
            prep.setDouble(3, ovchipkaart.getSaldo());
            prep.setInt(4, ovchipkaart.getReiziger_id());
            prep.setInt(5, ovchipkaart.getKaart_nummer());
            prep.executeQuery();
            for(Product product : ovchipkaart.getProducten()){
                String query2 = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer =? AND product_nummer =?";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovchipkaart.getKaart_nummer());
                prep2.setInt(2, product.getProduct_nummer());
                ResultSet rs = prep2.executeQuery();
                String query3;
                if (rs.next()) {
                    query3 = "UPDATE ov_chipkaart_product SET kaart_nummer =?, product_nummer =?, last_update = CURRENT_DATE;";
                } else {
                    query3 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, last_update) VALUES (?, ?, CURRENT_DATE);";
                }
                PreparedStatement prep3 = conn.prepareStatement(query3);
                prep3.setInt(1, ovchipkaart.getKaart_nummer());
                prep3.setInt(2, product.getProduct_nummer());
                prep3.executeQuery();
            }
            return true;
        } catch (Exception e) {
            if(e.getMessage().equals("No results were returned by the query.")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
}

    public boolean delete(OVChipkaart ovchipkaart){
            try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, ovchipkaart.getKaart_nummer());
            prep.executeQuery();
            String query2 = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?;";
            PreparedStatement prep2 = conn.prepareStatement(query2);
            prep2.setInt(1, ovchipkaart.getKaart_nummer());
            prep2.executeQuery();
            return true;
            } catch (Exception e) {
                if(e.getMessage().equals("No results were returned by the query.")) {
                    return false;
                }
                System.out.println(e.getMessage());
                return false;
            }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, reiziger.getId());
            ResultSet rs = prep.executeQuery();
            List<OVChipkaart> ovlijst = new ArrayList<OVChipkaart>();
            while(rs.next()){
                OVChipkaart ovk =  new OVChipkaart(rs.getInt("kaart_nummer"),
                    rs.getDate("geldig_tot"),
                    rs.getInt("klasse"),
                    rs.getDouble("saldo"),
                    rs.getInt("reiziger_id"));
                 ovk.setReiziger(reiziger);
                ovlijst.add(ovk);
            }
            List<Product> producten = new ArrayList<Product>();
            for(OVChipkaart ovkaart : ovlijst){
                String query2 = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer =?;";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovkaart.getKaart_nummer());
                ResultSet rs2 = prep2.executeQuery();
                while(rs2.next()){
                    Integer prodnr = rs2.getInt("product_nummer");
                    String query3 = "SELECT * FROM product WHERE product_nummer =?;";
                    PreparedStatement prep3 = conn.prepareStatement(query3);
                    prep3.setInt(1, prodnr);
                    ResultSet rs3 = prep3.executeQuery();
                    while(rs3.next()){
                        Product prod = new Product(rs3.getInt("product_nummer"),
                                rs3.getString("naam"),
                                rs3.getString("beschrijving"),
                                rs3.getDouble("prijs"));
                        producten.add(prod);
                    }
                }
                ovkaart.setProducten(producten);
            }
            return ovlijst;
        } catch(Exception e){
            if(e.getMessage().equals("No results were returned by the query.")) {
                return null;
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<OVChipkaart> findAll(){
        try {
            String query = "SELECT * FROM ov_chipkaart;";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            List<OVChipkaart> ovlijst = new ArrayList<OVChipkaart>();
            while (rs.next()) {
                OVChipkaart ovk =  new OVChipkaart(rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        rs.getInt("reiziger_id"));
                 ovk.setReiziger(rdao.findById(ovk.getReiziger_id()));
                ovlijst.add(ovk);
            }
            List<Product> producten = new ArrayList<Product>();
            for(OVChipkaart ovkaart : ovlijst) {
                String query2 = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer =?;";
                PreparedStatement prep2 = conn.prepareStatement(query2);
                prep2.setInt(1, ovkaart.getKaart_nummer());
                ResultSet rs2 = prep2.executeQuery();
                while (rs2.next()) {
                    Integer prodnr = rs2.getInt("product_nummer");
                    String query3 = "SELECT * FROM product WHERE product_nummer =?;";
                    PreparedStatement prep3 = conn.prepareStatement(query3);
                    prep3.setInt(1, prodnr);
                    ResultSet rs3 = prep3.executeQuery();
                    while (rs3.next()) {
                        Product prod = new Product(rs3.getInt("product_nummer"),
                                rs3.getString("naam"),
                                rs3.getString("beschrijving"),
                                rs3.getDouble("prijs"));
                        producten.add(prod);
                    }
                }
                ovkaart.setProducten(producten);
            }
            return ovlijst;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void setRdao(ReizigerDA rdao) {
        this.rdao = rdao;
    }
}