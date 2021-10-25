package Case;

import org.hibernate.*;

import javax.persistence.TypedQuery;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO{
    private Session session;

    public AdresDAOHibernate() {
    }

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public boolean save(Adres adres){
        try{
            getSession().saveOrUpdate(adres);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " adressave");
            return false;
        }
    }

    public boolean update(Adres adres){
        try{
            getSession().saveOrUpdate(adres);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " adresupdate");
            return false;
        }
    }

    public boolean delete(Adres adres){
        try{
            getSession().delete(adres);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " adresdelete");
            return false;
        }
    }

    public Adres findByReiziger(Reiziger reiziger){
        try{
            String str = "FROM Adres WHERE reiziger_id = " + reiziger.getId();
            TypedQuery<Adres> query = getSession().createQuery(str);
            Adres adres = query.getSingleResult();
            return adres;
        }catch(Exception e){
            System.out.println(e.getMessage() + " adresfindbyreiziger");
            return null;
        }
    }

    public List<Adres> findAll(){
        return getSession().createQuery("from Adres").list();
    }


}
