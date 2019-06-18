package pt.restapi.business.bondary;

import javax.ejb.ApplicationException;

/**
 * Created by brunohorta on 01/02/16.
 */
@ApplicationException(rollback = true)
public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super("User not found in database");
    }
}
