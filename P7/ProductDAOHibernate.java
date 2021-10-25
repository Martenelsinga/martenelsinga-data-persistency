package Case;

import org.hibernate.*;

import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private Session session;

    public ProductDAOHibernate() {
    }

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public boolean save(Product product){
        try{
            getSession().saveOrUpdate(product);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " productsave");
            return false;
        }
    }

    public boolean update(Product product){
        try{
            getSession().saveOrUpdate(product);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " productupdate");
            return false;
        }
    }

    public boolean delete(Product product){
        try{
            getSession().delete(product);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage() + " productdelete");
            return false;
        }
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovkaart){
        try{
//            String str = "SELECT products FROM Product WHERE product_nummer in (SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = :ovkaart.getKaart_nummer())";
            return getSession().createQuery("FROM OVChipkaart k WHERE k.kaart_nummer = ?1", Product.class).setInteger(1, ovkaart.getKaart_nummer()).list();
//            query.setParameter("ovkaart", ovkaart);
//            List<Product> product = query.list();
//            return product;
        }catch(Exception e){
            System.out.println(e.getMessage() + " productfindbykaart");
            return null;
        }
    }

    public List<Product> findAll(){
        return getSession().createQuery("from Product", Product.class).list();
    }


}
