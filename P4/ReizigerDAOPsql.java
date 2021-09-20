package Case;

import java.util.*;
import java.sql.*;

public class ReizigerDAOPsql implements ReizigerDA{
    Connection conn;
    AdresDAO adao;
    OVChipkaartDAO ovdao;

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
            boolean bl = prep.execute();
            if(reiziger.getOvchipkaarten() != null){
                for (OVChipkaart ovkaart : reiziger.getOvchipkaarten()) {
                    ovdao.save(ovkaart);
                }
            }
            if(reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }
            return bl;
        } catch (Exception e){
            System.out.println(e.getMessage() + " reizigersave");
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
             boolean bl = prep.execute();
             // als de reiziger uberhaupt een adres heeft
            if (reiziger.getAdres() != null) {
                // als het adres nog niet in de database staat
                if (adao.findByReiziger(reiziger) == null) {
                    adao.save(reiziger.getAdres());
                } else{
                    adao.update(reiziger.getAdres());
                }
            }
            // als de reiziger ovchipkaarten heeft
            if(reiziger.getOvchipkaarten() != null){
                // als er nog niks in de database staat
                if (ovdao.findByReiziger(reiziger).size() == 0) {
                    for(OVChipkaart ovkaart : reiziger.getOvchipkaarten()){
                        ovdao.save(ovkaart);
                    }
                }else{
                    for(OVChipkaart ovkaart : reiziger.getOvchipkaarten()){
                        ovdao.update(ovkaart);
                    }
                }
            }
            return bl;
        } catch (Exception e){
            System.out.println(e.getMessage() + " reizigerupdate");
            return false;
        }
    }

    public boolean delete(Reiziger reiziger){
    try{
        String query = "DELETE FROM reiziger WHERE reiziger_id =?;";
        PreparedStatement prep = conn.prepareStatement(query);
        prep.setInt(1, reiziger.getId());
        if(reiziger.getOvchipkaarten() != null) {
            for (OVChipkaart ovkaart : reiziger.getOvchipkaarten()) {
                ovdao.delete(ovkaart);
            }
        }
        boolean bl = prep.execute();
        adao.delete(reiziger.getAdres());
        return bl;
    } catch (Exception e){
        System.out.println(e.getMessage() + " reizigerdelete");
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
                rzgr.setAdres(adao.findByReiziger(rzgr));
                rzgr.setOvchipkaarten(ovdao.findByReiziger(rzgr));
                return rzgr;
            }
            return null;
        } catch(Exception e){
            System.out.println(e.getMessage() + " reizigerfindid");
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
                rzgr.setAdres(adao.findByReiziger(rzgr));
                rzgr.setOvchipkaarten(ovdao.findByReiziger(rzgr));
                results.add(rzgr);
            }
            return results;
        } catch(Exception e){
            System.out.println(e.getMessage() + " reizigergbdatum");
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
                rzgr.setAdres(adao.findByReiziger(rzgr));
                rzgr.setOvchipkaarten(ovdao.findByReiziger(rzgr));
                results.add(rzgr);
            }
            return results;
        }catch (Exception e){
            System.out.println(e.getMessage() + " reizigerall");
            return null;
    }
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }
}
