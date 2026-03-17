package com.example.FAS.repository;

import com.example.FAS.model.AccountCategory;
import com.example.FAS.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountCategoryRepository extends JpaRepository<AccountCategory,Long> {
    List<AccountCategory> findAllByAccountType(AccountType accountType);
}
