package com.assignment.daofab.exception;

/**
 * 
 * @author Sanket Lathiya
 *
 */
public class ParentTransactionFetchingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ParentTransactionFetchingException() {
        super("Exception fetching parent transactions.");
    }

    public ParentTransactionFetchingException(String message) {
        super(message);
    }
}
