package com.assignment.daofab.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.daofab.exception.ChildTransactionFetchingException;
import com.assignment.daofab.exception.InvalidParentIdException;
import com.assignment.daofab.exception.ParentTransactionFetchingException;
import com.assignment.daofab.response.ChildTransactionsResponse;
import com.assignment.daofab.response.ParentTransactionsResponse;
import com.assignment.daofab.service.ITransactionService;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@RestController
@RequestMapping("/daofab-api/v1")
public class TransactionController {

	@Autowired
	private ITransactionService transactionService;

	@GetMapping("/parent-transactions")
	public ResponseEntity<Set<ParentTransactionsResponse>> getParentTransactions(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws ParentTransactionFetchingException{
		if(page == null)
			page = 0; //Default page number

		if(size == null)
			size = 2; //Default page size

		Set<ParentTransactionsResponse> parentTransactions = transactionService.getParentTransactions(page, size);

		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(parentTransactions);

	}

	@GetMapping("/child-transactions/{parentId}")
	public ResponseEntity<Set<ChildTransactionsResponse>> getChildTransactions(@PathVariable("parentId") long parentId) throws InvalidParentIdException, ChildTransactionFetchingException{
		Set<ChildTransactionsResponse> childTransactions = transactionService.getChildTransactions(parentId);

		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(childTransactions);
	}
}
