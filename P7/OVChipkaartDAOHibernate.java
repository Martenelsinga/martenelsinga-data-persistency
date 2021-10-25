package Case;

import org.hibernate.Session;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{
    private Session session;
    private ReizigerDA rdao;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }
    public Session getSession() {
        return session;
    }
    public void setSession(Session session){
        this.session = session;
    }
    public void setRdao(ReizigerDA rdao){
        this.rdao = rdao;
    }

    public boolean save(OVChipkaart ovkaart){
        try{
            getSession().saveOrUpdate(ovkaart);
            for(Product product : ovkaart.getProducten()){
                getSession().saveOrUpdate(product);
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " ovkaartsave");
            return false;
        }
    }

    public boolean update(OVChipkaart ovkaart){
        try{
            getSession().saveOrUpdate(ovkaart);
            for(Product product : ovkaart.getProducten()){
                getSession().saveOrUpdate(product);
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " ovkaartupdate");
            return false;
        }
    }

    public boolean delete(OVChipkaart ovkaart){
        try{
            getSession().delete(ovkaart);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " ovkaartdelete");
            return false;
        }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        try{
            return getSession().createQuery("FROM OVChipkaart WHERE reiziger = " + reiziger).list();
        }catch(Exception e) {
            System.out.println(e.getMessage() + " ovfindbyreiziger" );
            return null;
        }
    }

    public List<OVChipkaart> findAll(){
        return getSession().createQuery("from OVChipkaart ", OVChipkaart.class).list();
    }
}
