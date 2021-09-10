package P1;

import java.util.List;

public interface ReizigerDA {
    // for some reason werkt mijn IntelliJ niet als ik deze klasse ReizigerDAO noem, dan herkent hij het niet als een interface.
    // Om deze reden heb ik gekozen om hem ReizigerDA te noemen.
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(Integer id);
    List<Reiziger> findByGbdatum(String datum);
    List<Reiziger> findAll();
}
