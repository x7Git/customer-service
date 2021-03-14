package org.acme.entity;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.entity.validation.Validation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Contract extends PanacheEntityBase implements Validation {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public Validator<Contract> validator() {
        return ValidatorBuilder.<Contract> of()
                .build();
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id.equals(contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
