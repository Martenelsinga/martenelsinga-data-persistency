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