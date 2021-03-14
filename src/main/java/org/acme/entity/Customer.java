package org.acme.entity;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.acme.entity.validation.Validation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Customer implements Validation {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private String name;
    private String prename;
    private String address;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Validator<Customer> validator(){
        return ValidatorBuilder.<Customer> of()
                .constraint(Customer::getName,"name", c -> c.notNull()
                        .greaterThanOrEqual(2)
                        .lessThanOrEqual(20))
                .constraint(Customer::getPrename, "prename", c -> c.notNull()
                        .greaterThanOrEqual(2)
                        .lessThanOrEqual(20))
                .constraint(Customer::getAddress, "address", c -> c.notNull()
                        .greaterThanOrEqual(4)
                        .lessThanOrEqual(20))
                .build();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prename='" + prename + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
