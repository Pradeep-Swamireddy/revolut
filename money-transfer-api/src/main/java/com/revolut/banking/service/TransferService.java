package com.revolut.banking.service;

import com.revolut.banking.entities.Transfer;
import com.revolut.banking.exceptions.TransferException;

public interface TransferService {

	Transfer transferMoney(Transfer transaction) throws TransferException;

}
