package com.assignment.daofab.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.daofab.dao.ITransactionDao;
import com.assignment.daofab.exception.ChildTransactionFetchingException;
import com.assignment.daofab.exception.InvalidParentIdException;
import com.assignment.daofab.exception.ParentTransactionFetchingException;
import com.assignment.daofab.model.ChildTransaction;
import com.assignment.daofab.model.ParentTransaction;
import com.assignment.daofab.response.ChildTransactionsResponse;
import com.assignment.daofab.response.ParentTransactionsResponse;
import com.assignment.daofab.service.ITransactionService;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private ITransactionDao transactionDao;

	private Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Override
	public Set<ParentTransactionsResponse> getParentTransactions(int page, int size) throws ParentTransactionFetchingException {
		Set<ParentTransactionsResponse> parentTransactionList = new TreeSet<>();

		try {
			for(ParentTransaction parentTransaction : transactionDao.getParentTransactions(page, size)) {
				parentTransactionList.add(
						//Builder Design Pattern
						new ParentTransactionsResponse()
						.setParentId(parentTransaction.getId())
						.setSender(parentTransaction.getSender())
						.setReceiver(parentTransaction.getReceiver())
						.setTotalAmount(parentTransaction.getTotalAmount())
						.setTotalPaidAmount(transactionDao.getTotalPaidAmountForParentTransaction(parentTransaction.getId()))
						);
			}
		} catch (Exception e) {
			logger.error("Exception fetching parent transactions.", e);
			throw new ParentTransactionFetchingException();
		}

		return parentTransactionList;
	}

	@Override
	public Set<ChildTransactionsResponse> getChildTransactions(long parentId) throws InvalidParentIdException, ChildTransactionFetchingException {
		try {
			Optional<ParentTransaction> parent = transactionDao.getParentTransaction(parentId);

			if(!parent.isPresent()) {
				throw new InvalidParentIdException();
			}

			ParentTransaction parentTransaction = parent.get();

			Set<ChildTransactionsResponse> childTransactionList = new TreeSet<>();


			for(ChildTransaction childTransaction : transactionDao.getChildTransactions(parentTransaction.getId())) {
				childTransactionList.add(
						//Builder Design Pattern
						new ChildTransactionsResponse()
						.setChildId(childTransaction.getId())
						.setSender(parentTransaction.getSender())
						.setReceiver(parentTransaction.getReceiver())
						.setTotalAmount(parentTransaction.getTotalAmount())
						.setPaidAmount(childTransaction.getPaidAmount())
						);

			}
			return childTransactionList;
		}catch (InvalidParentIdException e) {
			logger.error("Invalid parent Id.", e);
			throw new InvalidParentIdException();
		}catch (Exception e) {
			logger.error("Exception fetching child transactions.", e);
			throw new ChildTransactionFetchingException();
		}
	}

}
