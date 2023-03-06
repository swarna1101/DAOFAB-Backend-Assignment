package com.assignment.daofab.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.assignment.daofab.model.ChildTransaction;
import com.assignment.daofab.model.ParentTransaction;

/**
 * 
 * @author Sanket Lathiya
 *
 */
public interface ITransactionDao {

	void setParentTransactions(List<ParentTransaction> parentTransactions);

	void setChildTransactionsMap(Map<Long, List<ChildTransaction>> childTransactionsMap);

	List<ParentTransaction> getParentTransactions(int page, int size);
	
	List<ChildTransaction> getChildTransactions(long parentId);

	Optional<ParentTransaction> getParentTransaction(long parentId);

	long getTotalPaidAmountForParentTransaction(long parentId);

}
