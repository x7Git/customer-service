package org.acme.config;

import io.quarkus.runtime.StartupEvent;
import org.acme.control.ContractService;
import org.acme.control.CustomerService;
import org.acme.entity.Contract;
import org.acme.entity.Customer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

@ApplicationScoped
public class ApplicationLifecycleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");
    private final ContractService contractService;
    private final CustomerService customerService;

    @ConfigProperty(name ="quarkus.http.port")
    String port;
    @ConfigProperty(name = "quarkus.profile")
    String profile;
    @ConfigProperty(name = "quarkus.application.name", defaultValue = "customer-service")
    String name;
    @ConfigProperty(name = "quarkus.application.version", defaultValue = "0.0.0")
    String version;
    @ConfigProperty(name = "quarkus.default-locale")
    String local;

    @Inject
    public ApplicationLifecycleBean(ContractService contractService, CustomerService customerService) {
        this.contractService = contractService;
        this.customerService = customerService;
    }

    void onStart(@Observes StartupEvent ev) {
        banner();

        Customer customer = new Customer();
        customer.setName("Mueller");
        customer.setPrename("Peter");
        customer.setAddress("Mainstraße 23");

        Contract contract = new Contract();
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now());

        customerService.setCustomer(customer);
        contractService.setContract(contract);
    }

    private void banner() {
        LOGGER.info("\n"+
                "         _____\n" +
                "   _  __/__  /\n" +
                "  | |/_/  / / \n" +
                " _>  <   / /  \n" +
                "/_/|_|  /_/   \n" +
                "\n" +
                name + ":"      + version   + "\n" +
                "HTTP-Port: "   + port      + "\n" +
                "Profile:   "   + profile   + "\n" +
                "Local:     "   + local     + "\n"
        );
    }
}
