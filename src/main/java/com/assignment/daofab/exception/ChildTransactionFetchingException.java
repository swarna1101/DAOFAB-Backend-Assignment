package com.assignment.daofab.exception;

/**
 * 
 * @author Sanket Lathiya
 *
 */
public class ChildTransactionFetchingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ChildTransactionFetchingException() {
        super("Exception fetching child transactions.");
    }

    public ChildTransactionFetchingException(String message) {
        super(message);
    }
}
