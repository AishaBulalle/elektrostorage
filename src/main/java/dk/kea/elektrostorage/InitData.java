package dk.kea.elektrostorage;

import dk.kea.elektrostorage.entity.Assembly;
import dk.kea.elektrostorage.entity.AssemblyItem;
import dk.kea.elektrostorage.entity.Component;
import dk.kea.elektrostorage.entity.Order;
import dk.kea.elektrostorage.entity.OrderLine;
import dk.kea.elektrostorage.entity.Supplier;
import dk.kea.elektrostorage.repository.AssemblyItemRepository;
import dk.kea.elektrostorage.repository.AssemblyRepository;
import dk.kea.elektrostorage.repository.ComponentRepository;
import dk.kea.elektrostorage.repository.OrderLineRepository;
import dk.kea.elektrostorage.repository.OrderRepository;
import dk.kea.elektrostorage.repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class InitData {

    // Trin 1: Suppliers + Components (Del 1)
    @Bean
    CommandLineRunner seedBasics(SupplierRepository suppliers, ComponentRepository components) {
        return args -> {
            if (suppliers.count() > 0) return; // seed kun første gang

            // --- 2–3 leverandører ---
            Supplier s1 = suppliers.save(new Supplier("Nordic Electro ApS", "Lyngbyvej 12, 2800 Lyngby"));
            Supplier s2 = suppliers.save(new Supplier("Parts&Bits A/S", "Industriparken 3, 2750 Ballerup"));
            Supplier s3 = suppliers.save(new Supplier("Dansk Komponent A/S", "Fabriksvænget 7, 2600 Glostrup"));

            // --- 10 bestilbare komponenter (orderable=true) ---
            components.save(new Component(s1, "LED-5MM-RED", true));
            components.save(new Component(s1, "RES-1K-0.25W", true));
            components.save(new Component(s2, "BAT-HOLDER-9V", true));
            components.save(new Component(s2, "BAT-9V", true));
            components.save(new Component(s3, "IC-555-TIMER", true));
            components.save(new Component(s3, "CAP-100N", true));
            components.save(new Component(s1, "SW-TOGGLE", true));
            components.save(new Component(s2, "PCB-2x5CM", true));
            components.save(new Component(s3, "WIRE-RED-1M", true));
            components.save(new Component(s1, "WIRE-BLACK-1M", true));
        };
    }

    // Trin 2: Orders + Lines + Assembly + Items (Del 1 fuldt + klar til Del 2)
    @Bean
    CommandLineRunner seedExtended(SupplierRepository suppliers,
                                   ComponentRepository components,
                                   OrderRepository orders,
                                   OrderLineRepository orderLines,
                                   AssemblyRepository assemblies,
                                   AssemblyItemRepository assemblyItems) {
        return args -> {
            // Undgå duplikater hvis app’en startes igen
            if (orders.count() > 0 || assemblies.count() > 0) return;

            // Antag de tre første suppliers er dem vi lige lavede
            List<Supplier> supAll = suppliers.findAll();
            if (supAll.size() < 3) return; // sikkerhed
            Supplier s1 = supAll.get(0);
            Supplier s2 = supAll.get(1);
            Supplier s3 = supAll.get(2);

            // Slå de komponenter op, vi lavede i seedBasics
            Component led    = components.findAll().stream().filter(c -> "LED-5MM-RED".equals(c.getExternalSku())).findFirst().orElse(null);
            Component res1k  = components.findAll().stream().filter(c -> "RES-1K-0.25W".equals(c.getExternalSku())).findFirst().orElse(null);
            Component holder = components.findAll().stream().filter(c -> "BAT-HOLDER-9V".equals(c.getExternalSku())).findFirst().orElse(null);
            Component bat9   = components.findAll().stream().filter(c -> "BAT-9V".equals(c.getExternalSku())).findFirst().orElse(null);
            if (led == null || res1k == null || holder == null || bat9 == null) return; // sikkerhed

            // 1 kladdeordre (ikke sendt/modtaget) med 3 linjer á 10
            Order draft = new Order();
            draft.setSupplier(s1);
            draft = orders.save(draft);

            OrderLine l1 = new OrderLine();
            l1.setOrder(draft); l1.setComponent(led);   l1.setQuantity(10);
            orderLines.save(l1);
            OrderLine l2 = new OrderLine();
            l2.setOrder(draft); l2.setComponent(res1k); l2.setQuantity(10);
            orderLines.save(l2);
            OrderLine l3 = new OrderLine();
            l3.setOrder(draft); l3.setComponent(holder); l3.setQuantity(10);
            orderLines.save(l3);
            draft.getLines().addAll(List.of(l1, l2, l3));
            orders.save(draft);

            // 1 færdig ordre: 100 stk af én komponent (modtaget)
            Order received = new Order();
            received.setSupplier(s3);
            received.setTrackingCode("TRK-100");
            received.setFinalizedDate(LocalDate.now().minusDays(10));
            received.setExpectedDate(LocalDate.now().minusDays(3));
            received.setReceivedDate(LocalDate.now().minusDays(2));
            received = orders.save(received);

            OrderLine l4 = new OrderLine();
            l4.setOrder(received); l4.setComponent(bat9); l4.setQuantity(100);
            orderLines.save(l4);
            received.getLines().add(l4);
            orders.save(received);

            // Stykliste "Lysende LED": 1x LED, 1x 1kΩ, 1x holder, 1x 9V batteri
            Assembly a = new Assembly();
            a.setName("Lysende LED");
            a.setResultingComponent(led);
            a = assemblies.save(a);

            AssemblyItem ai1 = new AssemblyItem();
            ai1.setAssembly(a); ai1.setComponent(led);   ai1.setQuantity(1);
            assemblyItems.save(ai1);
            AssemblyItem ai2 = new AssemblyItem();
            ai2.setAssembly(a); ai2.setComponent(res1k); ai2.setQuantity(1);
            assemblyItems.save(ai2);
            AssemblyItem ai3 = new AssemblyItem();
            ai3.setAssembly(a); ai3.setComponent(holder); ai3.setQuantity(1);
            assemblyItems.save(ai3);
            AssemblyItem ai4 = new AssemblyItem();
            ai4.setAssembly(a); ai4.setComponent(bat9);  ai4.setQuantity(1);
            assemblyItems.save(ai4);
        };
    }
}
