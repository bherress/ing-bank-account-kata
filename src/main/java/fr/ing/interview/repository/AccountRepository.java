package fr.ing.interview.repository;

import fr.ing.interview.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, String> {

    Account findByIdAndCustomerId(String accountId, String customerId);
}
