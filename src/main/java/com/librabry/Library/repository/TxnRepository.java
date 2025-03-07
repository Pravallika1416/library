package com.librabry.Library.repository;

import com.librabry.Library.model.Txn;
import com.librabry.Library.model.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn,Integer> {

    Txn findByUserPhoneNumberAndBookBookNoAndTxnStatus(String phoneNumber, String bookNo, TxnStatus txnStatus);
}
