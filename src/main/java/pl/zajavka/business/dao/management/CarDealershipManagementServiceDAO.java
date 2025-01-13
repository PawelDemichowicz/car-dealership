package pl.zajavka.business.dao.management;

import java.util.List;

public interface CarDealershipManagementServiceDAO {

    void purge();

    void saveAll(List<?> entities);
}
