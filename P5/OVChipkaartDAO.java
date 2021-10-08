package Case;

import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovchipkaart);
    boolean update(OVChipkaart ovchipkaart);
    boolean delete(OVChipkaart ovchipkaart);
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findAll();
    void setRdao(ReizigerDA rdao);
}
