package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {

}
