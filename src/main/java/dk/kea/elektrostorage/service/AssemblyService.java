// AssemblyService.java
package dk.kea.elektrostorage.service;

import dk.kea.elektrostorage.dto.AssemblyDTO;
import dk.kea.elektrostorage.dto.AssemblyItemDTO;
import dk.kea.elektrostorage.entity.Assembly;
import dk.kea.elektrostorage.entity.AssemblyItem;
import dk.kea.elektrostorage.repository.AssemblyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssemblyService {
    private final AssemblyRepository repo;
    private final ComponentService componentService;

    public AssemblyService(AssemblyRepository repo, ComponentService componentService) {
        this.repo = repo;
        this.componentService = componentService;
    }

    public List<AssemblyDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    public AssemblyDTO get(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Assembly not found: " + id));
    }

    @Transactional
    public AssemblyDTO create(String name, Long resultingComponentId, List<AssemblyItemDTO> items) {
        Assembly a = new Assembly();
        a.setName(name);
        if (resultingComponentId != null) {
            a.setResultingComponent(componentService.getOrThrow(resultingComponentId));
        }
        for (AssemblyItemDTO i : items) {
            AssemblyItem ai = new AssemblyItem();
            ai.setAssembly(a);
            ai.setComponent(componentService.getOrThrow(i.componentId()));
            ai.setQuantity(i.quantity());
            a.getItems().add(ai);
        }
        a = repo.save(a);
        return toDTO(a);
    }

    private AssemblyDTO toDTO(Assembly a) {
        var items = a.getItems().stream()
                .map(i -> new AssemblyItemDTO(i.getComponent().getId(), i.getQuantity()))
                .toList();
        Long resId = a.getResultingComponent()==null ? null : a.getResultingComponent().getId();
        return new AssemblyDTO(a.getId(), a.getName(), resId, items);
    }
}
