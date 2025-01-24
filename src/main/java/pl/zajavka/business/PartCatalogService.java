package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.PartDAO;
import pl.zajavka.domain.Part;

import java.util.Optional;

@AllArgsConstructor
public class PartCatalogService {

    private final PartDAO partDAO;

    @Transactional
    public Part findPart(String partSerialNumber) {
        Optional<Part> service = partDAO.findBySerialNumber(partSerialNumber);
        if (service.isEmpty()) {
            throw new RuntimeException("Could not find service by part code: [%s]".formatted(partSerialNumber));
        }
        return service.get();
    }
}
