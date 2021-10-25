package Case;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        AdresDAO adao = new AdresDAOHibernate(getSession());
        ReizigerDA rdao = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAO ovdao = new OVChipkaartDAOHibernate(getSession());
        ProductDAO pdao = new ProductDAOHibernate(getSession());
        ovdao.setRdao(rdao);
        rdao.setAdao(adao);
        rdao.setOvdao(ovdao);
        testFetchAll();
        testReizigerDAO(rdao, ovdao, pdao);
        testFetchAll();
    }

    private static void testReizigerDAO(ReizigerDA rdao, OVChipkaartDAO ovdao, ProductDAO pdao) throws SQLException {
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
        List<OVChipkaart> ovkaarten = new ArrayList<OVChipkaart>();
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        OVChipkaart sietskeov = new OVChipkaart(1234512345, java.sql.Date.valueOf("2022-12-12"), 2, 15.5);
        Product testproduct = new Product(8, "Testproduct", "Test!", 666.0);
        pdao.save(testproduct);
        sietskeov.addProduct(testproduct);
        sietske.addOvchipkaart(sietskeov);
        rdao.save(sietske);


        reizigers = rdao.findAll();

        System.out.println(reizigers.size() + " reizigers\n");

        // Update sietske's achternaam naar Aap
        System.out.println(rdao.findById(77));
        sietske.setAchternaam("Aap");
        rdao.update(sietske);
        System.out.println(rdao.findById(77));

        // Delete sietske
        rdao.delete(sietske);
        System.out.println(rdao.findById(77));
        pdao.delete(testproduct);
        // Find by gbdatum
        System.out.println(rdao.findByGbdatum("2002-10-22"));
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }


    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            System.out.println("\n---------- Test FetchAll() -------------");
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }
}