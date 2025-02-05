package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.PartDAO;
import pl.zajavka.domain.Part;
import pl.zajavka.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PartCatalogService {

    private final PartDAO partDAO;

    @Transactional
    public Part findPart(String partSerialNumber) {
        Optional<Part> service = partDAO.findBySerialNumber(partSerialNumber);
        if (service.isEmpty()) {
            throw new NotFoundException("Could not find service by part code: [%s]".formatted(partSerialNumber));
        }
        return service.get();
    }

    public List<Part> findAll() {
        List<Part> parts = partDAO.findAll();
        log.info("Available parts: [{}]", parts);
        return parts;
    }
}
