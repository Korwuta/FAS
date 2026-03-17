package com.example.FAS.repository;

import com.example.FAS.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsByAccountCode(String accountCode);
    boolean existsByAccountCodeAndIdNot(String accountCode, Long id);
}
