package pl.zajavka.business.dao;

import pl.zajavka.domain.Salesman;

import java.util.List;
import java.util.Optional;

public interface SalesmanDAO {

    List<Salesman> findAvailable();

    Optional<Salesman> findByPesel(String pesel);
}
