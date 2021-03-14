package org.acme.control;

import org.acme.entity.Contract;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ContractService {
    @Transactional
    public UUID setContract(Contract contract){
        contract.persist();
        return contract.getId();
    }

    public Contract findContractById(UUID id){
        return Contract.findById(id);
    }

    public List<Contract> findAllContract(){
        return Contract.listAll();
    }

    @Transactional
    public void updateContract(Contract contract){
        // TODO
        Contract.update("", contract);
    }

    @Transactional
    public void delete(UUID id){
        Contract.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        Contract.deleteAll();
    }
}
