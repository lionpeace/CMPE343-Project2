// For Password Validation
import java.util.regex.Pattern;

// For Date Validation
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class InputValidation {

    public boolean integerValidation(String input)
    {
        // String input for integer inputs
        // For employee ID choice, role choice & algorithm operation

        // Employee ID part
            // Employee list will be seen and Manager will choose which employee he/she want to edit.

        // Role Choice
            // Role list will be seen and Manager will choose which role he/she want to assign to the employee.

        // Algorithm Operation
            // Manager identifies size of array

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Input cannot be null or empty!");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("Input cannot contain spaces!");
            return false;
        }

        if(input.matches("-?\\d+"))
        {
            return true;
        }

        return true; // dummy
    }

    public boolean noNumberValidation(String input)
    {
        // String input for inputs without any number
        // Like; name, surname

        // This method will be used for name and surname inputs

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Input cannot be null or empty!");
            return false;
        }

        if(input.matches(".*[0-9].*"))
        {
            System.out.println("Input cannot contain numbers!");
            return false;
        }

        return true;
    }

    public boolean phoneNoValidation(String input)
    {
        // String input for inputs without letter
        // Like; phone_no

        // This method will be used for phone no inputs
        // Phone numbers will be like:
        // + is already defined
            // User will input (with country code) 905383599269

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Phone number cannot be null or empty!");
            return false;
        }

        if(input.matches("[\\d\\s]+") && input.matches(".*\\d.*"))
        {
            return true;
        }

        return false;
    }



    public boolean passwordValidation(String input)
    {
        // String input for password inputs
        // For password
        // It will be used Authentication Class

        // Password has to:
            // at least 1 Uppercase letter
            // at least 1 Lowercase letter
            // at least 1 Special character
            // minimum 8 length
            // maximum 50 length

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Password cannot be null or empty!");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("Password cannot contain spaces!");
            return false;
        }

        if(input.length() < 8 || input.length() > 50)
        {
            System.out.println("Password should be between 8 and 50 characters!");
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(input).find()) {
            System.out.println("Password must contain at least one uppercase letter!");
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(input).find()) {
            System.out.println("Password must contain at least one lowercase letter!");
            return false;
        }

        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(input).find()) {
            System.out.println("Password must contain at least one special letter!");
            return false;
        }

        return true;
    }

    public boolean emailValidation(String input)
    {
        // String input for email inputs

        // Email has to:
            // .com at the end
            // 1 @ character however:
                // at least 1 character should be there between .com and @
                // at least 1 character should be there before the @

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Email cannot be null or empty!");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("Email cannot contain spaces!");
            return false;
        }

        if(input.length() > 100)
        {
            System.out.println("Email cannot contain at most 100 characters!");
            return false;
        }

        if(!input.endsWith(".com"))
        {
            System.out.println("Invalid email format!");
            return false;
        }

        if (!Pattern.compile(".+@.+\\.com$").matcher(input).matches()) {
            System.out.println("Invalid email format!");
            return false;
        }

        if (input.chars().filter(ch -> ch == '@').count() != 1) {
            System.out.println("Invalid email format!");
            return false;
        }

        String domainPart = input.substring(input.indexOf('@') + 1, input.length() - 4);

        if (domainPart.isEmpty())
        {
            System.out.println("Invalid email format!");
            return false;
        }

        return true;
    }

    public boolean dateValidation(String input)
    {
        // String input for date inputs

        // Date should be input like:
            // YYYY-MM-DD

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("Date cannot be null or empty!");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("Date cannot contain spaces!");
            return false;
        }

        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(input, formatter);
            return true;
        }
        catch (DateTimeParseException e)
        {
            System.out.println("Invalid date format!");
            return false;
        }
    }

}
