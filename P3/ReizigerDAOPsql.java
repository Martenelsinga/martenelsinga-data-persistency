package P1;

import java.util.*;
import java.sql.*;

public class ReizigerDAOPsql implements ReizigerDA{
    Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Reiziger reiziger){
        try {
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, reiziger.getId());
            prep.setString(2, reiziger.getVoorletters());
            prep.setString(3, reiziger.getTussenvoegsel());
            prep.setString(4, reiziger.getAchternaam());
            prep.setDate(5, reiziger.getGeboortedatum());
            prep.executeQuery();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(Reiziger reiziger){
        try{
             String query = "UPDATE reiziger SET voorletters =?, tussenvoegsel =?, achternaam =?, geboortedatum =? WHERE reiziger_id =?";
             PreparedStatement prep = conn.prepareStatement(query);
             prep.setString(1, reiziger.getVoorletters());
             prep.setString(2, reiziger.getTussenvoegsel());
             prep.setString(3, reiziger.getAchternaam());
             prep.setDate(4, reiziger.getGeboortedatum());
             prep.setInt(5, reiziger.getId());
             prep.executeQuery();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(Reiziger reiziger){
    try{
        String query = "DELETE FROM reiziger WHERE reiziger_id =?;";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, reiziger.getId());
        prep.executeQuery();
        return true;
    } catch (Exception e){
        System.out.println(e.getMessage());
        return false;
    }
    }

    public Reiziger findById(Integer id){
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if(rs.next()) {
                Reiziger rzgr = new Reiziger(rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum"));
                return rzgr;
            }
            return null;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Reiziger> findByGbdatum(String datum){
        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum =?;";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet rs = prep.executeQuery();
            List<Reiziger> results = new ArrayList<Reiziger>();
            while (rs.next()) {
                Reiziger rzgr = new Reiziger(rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum"));
                results.add(rzgr);
            }
            return results;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Reiziger> findAll(){
        try {
            String query = "SELECT * FROM reiziger;";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            List<Reiziger> results = new ArrayList<Reiziger>();
            while (rs.next()) {
                // Ik breek hier het resultaat op om de uitkomsten 1 voor 1 in een Reiziger-object te zetten, dat dan weer
                // in een List komt te staan.
                Reiziger rzgr = new Reiziger(rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum"));
                results.add(rzgr);
            }
            return results;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
    }
    }
}
