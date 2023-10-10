/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 6, 2023
 * 
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 6, 2023
 */

package exceptions;

import java.io.Serial;

// This exception is thrown when there is an error accessing the database
public class DatabaseAccessException extends Exception {
	@Serial
	private static final long serialVersionUID = 1L;

	public DatabaseAccessException(String message) {
		super(message);
	}
}