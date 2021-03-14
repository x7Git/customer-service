package org.acme.control;

import org.acme.entity.Customer;
import org.acme.entity.CustomerRepository;
import org.acme.entity.Customer_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {
    private final EntityManager entityManager;
    private final CustomerRepository customerRepository;

    @Inject
    public CustomerService(EntityManager entityManager, CustomerRepository customerRepository) {
        this.entityManager = entityManager;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public UUID setCustomer(Customer customer){
        customerRepository.persist(customer);
        return customer.getId();
    }

    public Customer findCustomerById(UUID id){
        return customerRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    public List<Customer> findCustomers(MultivaluedMap<String, String> queryParameters){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);

        Root<Customer> rootCustomer = query.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        if(queryParameters.isEmpty()){
            return customerRepository.listAll();
        }

        if (queryParameters.containsKey(Customer_.NAME)) {
            predicates.add(builder.equal(rootCustomer.get(Customer_.name), queryParameters.get(Customer_.NAME)));
        }
        if (queryParameters.containsKey(Customer_.PRENAME)) {
            predicates.add(builder.equal(rootCustomer.get(Customer_.prename), queryParameters.get(Customer_.PRENAME)));
        }
        if (queryParameters.containsKey(Customer_.ADDRESS)) {
            predicates.add(builder.equal(rootCustomer.get(Customer_.ADDRESS), queryParameters.get(Customer_.ADDRESS)));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public void updateCustomer(Customer updateCustomer, UUID id){
        Customer oldCustomer = findCustomerById(id);
        oldCustomer.setName(updateCustomer.getName());
        oldCustomer.setPrename(updateCustomer.getPrename());
        oldCustomer.setAddress(updateCustomer.getAddress());
        customerRepository.persist(oldCustomer);
    }

    @Transactional
    public void delete(UUID id){
        customerRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll(){
        customerRepository.deleteAll();
    }
}
