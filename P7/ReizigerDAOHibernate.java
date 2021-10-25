package Case;
import org.hibernate.*;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDA{
    private Session session;
    private OVChipkaartDAO ovdao;
    private AdresDAO adao;

    public ReizigerDAOHibernate(){
    }

    public ReizigerDAOHibernate(Session session){
        this.session = session;
    }

    public OVChipkaartDAO getOvdao() {
        return ovdao;
    }

    @Override
    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    public AdresDAO getAdao() {
        return adao;
    }

    @Override
    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public Session getSession(){
        return session;
    }

    public boolean save(Reiziger reiziger){
        try {
            getSession().saveOrUpdate(reiziger);
            if (reiziger.getOvchipkaarten() != null) {
                for (OVChipkaart ovkaart : reiziger.getOvchipkaarten()) {
                    ovdao.save(ovkaart);
                }
            }
            if (reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " reizigersave");
            return false;
        }
    }

    public boolean update(Reiziger reiziger){
        try {
            getSession().saveOrUpdate(reiziger);
            if (reiziger.getOvchipkaarten() != null) {
                for (OVChipkaart ovkaart : reiziger.getOvchipkaarten()) {
                    ovdao.save(ovkaart);
                }
            }
            if (reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " reizigerupdate");
            return false;
        }
    }

    public boolean delete(Reiziger reiziger){
        try{
            getSession().delete(reiziger);
            if (reiziger.getOvchipkaarten() != null) {
                for (OVChipkaart ovkaart : reiziger.getOvchipkaarten()) {
                    ovdao.delete(ovkaart);
                }
            }
            if (reiziger.getAdres() != null) {
                adao.delete(reiziger.getAdres());
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " reizigerdelete");
            return false;
        }
    }

    public Reiziger findById(Integer reiziger_id){
        try{
            return getSession().get(Reiziger.class, reiziger_id);
        }catch(Exception e) {
            System.out.println(e.getMessage() + " ovfindbyreiziger" );
            return null;
        }
    }

    public List<Reiziger> findByGbdatum(String datum){
        return getSession().createQuery("FROM Reiziger WHERE geboortedatum = ?1", Reiziger.class).setDate(1, Date.valueOf(datum)).list();
    }

    public List<Reiziger> findAll(){
        return getSession().createQuery("FROM Reiziger").list();
    }

}
