/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 * 
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

package exceptions;

import java.io.Serial;

public class DatabaseAccessException extends Exception {
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public DatabaseAccessException(String message) {
		super(message);
	}
}