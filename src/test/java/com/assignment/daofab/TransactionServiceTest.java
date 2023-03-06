package com.assignment.daofab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.daofab.dao.ITransactionDao;
import com.assignment.daofab.exception.ChildTransactionFetchingException;
import com.assignment.daofab.exception.InvalidParentIdException;
import com.assignment.daofab.exception.ParentTransactionFetchingException;
import com.assignment.daofab.model.ChildTransaction;
import com.assignment.daofab.model.ParentTransaction;
import com.assignment.daofab.response.ChildTransactionsResponse;
import com.assignment.daofab.response.ParentTransactionsResponse;
import com.assignment.daofab.service.impl.TransactionService;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@SpringBootTest
public class TransactionServiceTest {

	@Mock
	private ITransactionDao transactionDao;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void getParentTransactionsTest() throws ParentTransactionFetchingException {
		long totalPaidAmount = 200;

		ParentTransaction parentTransaction = new ParentTransaction();
		parentTransaction.setId(1);
		parentTransaction.setSender("ABC");
		parentTransaction.setReceiver("XYZ");
		parentTransaction.setTotalAmount(200);

		List<ParentTransaction> parentTransactionList = new ArrayList<>();
		parentTransactionList.add(parentTransaction);

		when(transactionDao.getParentTransactions(anyInt(), anyInt())).thenReturn(parentTransactionList);
		when(transactionDao.getTotalPaidAmountForParentTransaction(anyLong())).thenReturn(totalPaidAmount);

		Set<ParentTransactionsResponse> parentTransactionsResponses = transactionService.getParentTransactions(0, 2);
		ParentTransactionsResponse response = parentTransactionsResponses.stream().findFirst().get();

		assertNotNull(parentTransactionsResponses);
		assertEquals(1, parentTransactionsResponses.size());
		assertEquals(1, response.getId());
		assertEquals("ABC", response.getSender());
		assertEquals("XYZ", response.getReceiver());
		assertEquals(200, response.getTotalAmount());
		assertEquals(totalPaidAmount, response.getTotalPaidAmount());
	}

	@Test
	public void parentTransactionFetchingExceptionTest() {
		when(transactionDao.getParentTransactions(anyInt(), anyInt())).thenThrow(new RuntimeException());

		Exception exception = assertThrows(ParentTransactionFetchingException.class, () -> {
			transactionService.getParentTransactions(0, 2);
		});

		assertEquals("Exception fetching parent transactions.", exception.getMessage());
	}

	@Test
	public void getChildTransactionsTest() throws ParentTransactionFetchingException, InvalidParentIdException, ChildTransactionFetchingException {
		long paidAmount = 10;

		ParentTransaction parentTransaction = new ParentTransaction();
		parentTransaction.setId(1);
		parentTransaction.setSender("ABC");
		parentTransaction.setReceiver("XYZ");
		parentTransaction.setTotalAmount(200);

		ChildTransaction childTransaction = new ChildTransaction();
		childTransaction.setId(1);
		childTransaction.setParentId(parentTransaction.getId());
		childTransaction.setPaidAmount(paidAmount);

		List<ChildTransaction> childTransactionList = new ArrayList<>();
		childTransactionList.add(childTransaction);

		when(transactionDao.getParentTransaction(anyLong())).thenReturn(Optional.of(parentTransaction));
		when(transactionDao.getChildTransactions(anyLong())).thenReturn(childTransactionList);

		Set<ChildTransactionsResponse> childTransactionsResponses = transactionService.getChildTransactions(parentTransaction.getId());
		ChildTransactionsResponse response = childTransactionsResponses.stream().findFirst().get();

		assertNotNull(childTransactionsResponses);
		assertEquals(1, childTransactionsResponses.size());
		assertEquals(1, response.getId());
		assertEquals("ABC", response.getSender());
		assertEquals("XYZ", response.getReceiver());
		assertEquals(200, response.getTotalAmount());
		assertEquals(paidAmount, response.getPaidAmount());
	}

	@Test
	public void invalidParentIdExceptionTest() {
		when(transactionDao.getParentTransaction(anyLong())).thenReturn(Optional.ofNullable(null));

		Exception exception = assertThrows(InvalidParentIdException.class, () -> {
			transactionService.getChildTransactions(1);
		});

		assertEquals("Invalid parent id.", exception.getMessage());
	}

	@Test
	public void childTransactionFetchingException() {
		when(transactionDao.getParentTransaction(anyLong())).thenReturn(Optional.of(new ParentTransaction()));
		when(transactionDao.getChildTransactions(anyLong())).thenThrow(new RuntimeException());

		Exception exception = assertThrows(ChildTransactionFetchingException.class, () -> {
			transactionService.getChildTransactions(1);
		});

		assertEquals("Exception fetching child transactions.", exception.getMessage());
	}
}
