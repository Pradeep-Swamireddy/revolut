package com.revolut.banking.repository;

import com.revolut.banking.entities.Transfer;
import com.revolut.banking.exceptions.TransferException;

public interface TransferRepository {

	Transfer transferMoney(Transfer transaction) throws TransferException;

}
