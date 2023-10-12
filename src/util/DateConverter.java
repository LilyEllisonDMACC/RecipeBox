/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 7, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 7, 2023
 */

package util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.util.Calendar;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<java.util.Date, java.sql.Date> {

    @Override
    public Date convertToDatabaseColumn(java.util.Date attribute) {
        return new Date(attribute.getTime());
    }

    @Override
    public java.util.Date convertToEntityAttribute(Date dbData) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dbData.getTime());
        return cal.getTime();
    }
}
