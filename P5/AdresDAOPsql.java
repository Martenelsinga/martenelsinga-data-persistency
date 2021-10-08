package Case;

import java.util.*;
import java.sql.*;

public class AdresDAOPsql implements AdresDAO{
    Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, adres.getAdres_id());
            prep.setString(2, adres.getPostcode());
            prep.setString(3, adres.getHuisnummer());
            prep.setString(4, adres.getStraat());
            prep.setString(5, adres.getWoonplaats());
            prep.setInt(6, adres.getReiziger_id());
            prep.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " adressave");
            return false;
        }
    }

    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET postcode =?, huisnummer =?, straat =?, woonplaats =? WHERE adres_id =?";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, adres.getPostcode());
            prep.setString(2, adres.getHuisnummer());
            prep.setString(3, adres.getStraat());
            prep.setString(4, adres.getWoonplaats());
            prep.setInt(5, adres.getAdres_id());
            prep.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " adresupdate");
            return false;
        }
    }

    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, adres.getAdres_id());
            prep.executeQuery();
            return true;
        } catch (Exception e) {
            if(e.getMessage().equals("Cannot invoke \"Case.Adres.getAdres_id()\" because \"adres\" is null")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, reiziger.getId());
            ResultSet rs = prep.executeQuery();
            rs.next();
            Adres adr = new Adres(rs.getInt("adres_id"),
                    rs.getString("postcode"),
                    rs.getString("huisnummer"),
                    rs.getString("straat"),
                    rs.getString("woonplaats"),
                    rs.getInt("reiziger_id"));
            return adr;
        } catch(Exception e){
            if(e.getMessage().equals("ResultSet not positioned properly, perhaps you need to call next.")) {
                return null;
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Adres> findAll() {
        try {
            String query = "SELECT * FROM adres;";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            List<Adres> results = new ArrayList<Adres>();
            while (rs.next()) {
                Adres adr = new Adres(rs.getInt("adres_id"),
                        rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        rs.getInt("reiziger_id"));
                results.add(adr);
            }
            return results;
        } catch(Exception e){
            System.out.println(e.getMessage() + " adresall");
            return null;
        }
    }
}