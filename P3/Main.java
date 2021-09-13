package P1;
import java.sql.*;
import java.util.*;
public class Main {
    public static void main(String[] args){
        String url = "jdbc:postgresql://localhost:5433/ovchip";
        String userName = "postgres";
        String passWord = "5Kmdpp";
        try{
            Connection conn = DriverManager.getConnection(url, userName, passWord);
            AdresDAOPsql adao = new AdresDAOPsql(conn);
            ReizigerDA rda = new ReizigerDAOPsql(conn, adao);
            testReizigerDAO(rda);

        }catch(SQLException e){
            System.out.println(e);
        }

    }

    private static void testReizigerDAO(ReizigerDA rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        List<OVChipkaart> ovkaarten = new ArrayList<OVChipkaart>;
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update sietske's achternaam naar Aap
        sietske.setAchternaam("Aap");
        rdao.update(sietske);
        System.out.println(rdao.findById(77));

        // Delete sietske
        rdao.delete(sietske);
        System.out.println(rdao.findById(77));

        // Find by gbdatum
        System.out.println(rdao.findByGbdatum("2002-10-22"));

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}


