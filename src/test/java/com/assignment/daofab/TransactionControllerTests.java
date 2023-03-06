package com.assignment.daofab;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.daofab.response.ChildTransactionsResponse;
import com.assignment.daofab.response.ParentTransactionsResponse;
import com.assignment.daofab.service.ITransactionService;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {

	@MockBean
	private ITransactionService transactionService;

	@Autowired 
	private MockMvc mockMvc;

	@Test
	void getParentTransactionsTest() throws Exception {
		Set<ParentTransactionsResponse> parentTransactions = new TreeSet<>();

		ParentTransactionsResponse parentTransactionsResponse = new ParentTransactionsResponse()
				.setParentId(1)
				.setSender("ABC")
				.setReceiver("XYZ")
				.setTotalAmount(200)
				.setTotalPaidAmount(100);

		parentTransactions.add(parentTransactionsResponse);

		when(transactionService.getParentTransactions(anyInt(), anyInt())).thenReturn(parentTransactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/daofab-api/v1/parent-transactions"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].id").value(parentTransactionsResponse.getId()))
		.andExpect(jsonPath("$.[0].sender").value(parentTransactionsResponse.getSender()))
		.andExpect(jsonPath("$.[0].receiver").value(parentTransactionsResponse.getReceiver()))
		.andExpect(jsonPath("$.[0].totalAmount").value(parentTransactionsResponse.getTotalAmount()))
		.andExpect(jsonPath("$.[0].totalPaidAmount").value(parentTransactionsResponse.getTotalPaidAmount()));
	}

	@Test
	void getChildTransactionsTest() throws Exception {
		long parentId = 1;

		Set<ChildTransactionsResponse> childTransactions = new TreeSet<>();

		ChildTransactionsResponse childTransactionsResponse = new ChildTransactionsResponse()
				.setChildId(1)
				.setSender("ABC")
				.setReceiver("XYZ")
				.setTotalAmount(200)
				.setPaidAmount(10);

		childTransactions.add(childTransactionsResponse);

		when(transactionService.getChildTransactions(anyLong())).thenReturn(childTransactions);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/daofab-api/v1/child-transactions/%s", parentId)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].id").value(childTransactionsResponse.getId()))
		.andExpect(jsonPath("$.[0].sender").value(childTransactionsResponse.getSender()))
		.andExpect(jsonPath("$.[0].receiver").value(childTransactionsResponse.getReceiver()))
		.andExpect(jsonPath("$.[0].totalAmount").value(childTransactionsResponse.getTotalAmount()))
		.andExpect(jsonPath("$.[0].paidAmount").value(childTransactionsResponse.getPaidAmount()));
	}

}
