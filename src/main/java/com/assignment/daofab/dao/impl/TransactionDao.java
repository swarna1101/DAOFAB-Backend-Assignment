package com.assignment.daofab.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.assignment.daofab.dao.ITransactionDao;
import com.assignment.daofab.model.ChildTransaction;
import com.assignment.daofab.model.ParentTransaction;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@Repository
public class TransactionDao implements ITransactionDao {

	private List<ParentTransaction> parentTransactions;
	private Map<Long, List<ChildTransaction>> childTransactionsMap;
	
	@Override
	public void setParentTransactions(List<ParentTransaction> parentTransactions) {
		this.parentTransactions = parentTransactions;
	}
	
	@Override
	public void setChildTransactionsMap(Map<Long, List<ChildTransaction>> childTransactionsMap) {
		this.childTransactionsMap = childTransactionsMap;
	}

	@Override
	public List<ParentTransaction> getParentTransactions(int page, int size) {
		/*
		return IntStream.range(0, parentTransactions.size())
		.filter(index -> (index >= (page*size) && index < (page*size + size)))
		.mapToObj(index -> parentTransactions.get(index))
		.collect(Collectors.toList());
		*/
		
		List<ParentTransaction> parentTransactionList = new ArrayList<>();
		
		int i=(page*size);
		
		while(i<(page*size + size) && i<parentTransactions.size()) {
			parentTransactionList.add(parentTransactions.get(i));
			i++;
		}
		
		return parentTransactionList;
	}

	@Override
	public List<ChildTransaction> getChildTransactions(long parentId) {
		return childTransactionsMap.get(parentId) == null ? Collections.emptyList() : childTransactionsMap.get(parentId);
	}

	@Override
	public Optional<ParentTransaction> getParentTransaction(long parentId) {
		return parentTransactions.stream().filter(p -> p.getId() == parentId).findFirst();
	}

	@Override
	public long getTotalPaidAmountForParentTransaction(long parentId) {
		List<ChildTransaction> childTransactions = childTransactionsMap.get(parentId);
		
		if(childTransactions != null) {
			return childTransactions.stream().mapToLong(child -> child.getPaidAmount()).sum();
		}else {
			return 0;
		}
	}
}
