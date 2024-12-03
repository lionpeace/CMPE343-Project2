// For Password Validation
import java.util.regex.Pattern;

// For Date Validation
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Provides input validation for various user inputs.
 * Validation methods for general input, integers, characters are done here.
 */

public class InputValidation {

    /**
     * Validates a generic string entry. Checks that the input is not empty or null.
     * contains no spaces or tabs and cannot exceed 150 characters.
     * @param input Returns the input string to validate.
     * Returns true if the input is valid, false otherwise.
     */

    public boolean defaultInputValidation(String input)
    {
        // String input for default validation structure
        // For username and password input

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nInput cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nInput cannot contain spaces!\n");
            return false;
        }

        if(input.length() > 100)
        {
            System.out.println("\nInput cannot exceed 100 characters!\n");
            return false;
        }

        return true;
    }

    /**
     * Method that validates a string input as an integer. Makes sure the input is not null,
     * does not contain a blank space or tab and represents a valid integer.
     * @param input Returns the input string to validate.
     * Returns true if the input is an integer, false otherwise.
     */

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
            System.out.println("\nInput cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nInput cannot contain spaces!\n");
            return false;
        }

        if(input.matches("-?\\d+"))
        {
            return true;
        }

        else if(!input.matches("-?\\d+"))
        {
            System.out.println("\nPlease enter an integer value!\n");
            return false;
        }

        return true; // dummy
    }

    /**
     * Method that provides validation for a single character input. Check whether the input is null.
     * empty, a single letter, and contains no spaces or tabs.
     * @param input Returns the input string to validate.
     * Returns true if the input is a valid single character, false otherwise.
     */

    public boolean charValidation(String input)
    {
        // String input for return main menu
        // Just for Return Main Menu

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nInput cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nInput cannot contain spaces!\n");
            return false;
        }

        if(input.length() > 1)
        {
            System.out.println("\nPlease enter just one letter!\n");
            return false;
        }

        char character = input.charAt(0);

        if(!Character.isLetter(character))
        {
            System.out.println("\nPlease enter a valid letter!\n");
            return false;
        }

        return true;
    }

    /**
     * Method that validates a string input to ensure that it does not contain any numbers.
     * or checks for special characters. This is used for first or last name entries.
     *
     * @param input Returns the input string to validate.
     * Returns true if the input is valid, false otherwise.
     */

    public boolean noNumberValidation(String input)
    {
        // String input for inputs without any number
        // This method will be used for name and surname inputs

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nInput cannot be null or empty!\n");
            return false;
        }

        if(input.matches(".*[0-9].*"))
        {
            System.out.println("\nInput cannot contain numbers!\n");
            return false;
        }

        if (input.matches(".*[^a-zA-ZğüşıöçĞÜŞİÖÇ ].*"))
        {
            System.out.println("\nInput cannot contain special characters!\n");
            return false;
        }

        if(input.length() > 150)
        {
            System.out.println("\nInput cannot exceed 150 characters!\n");
            return false;
        }

        return true;
    }

    /**
     * Method for verifying a phone number entry. Allows input to contain only numbers and spaces.
     * @param input Returns the phone number string to validate.
     * Returns true if the input is valid, false otherwise.
     */

    public boolean phoneNoValidation(String input)
    {
        // String input for inputs without letter
        // Like; phone_no

        if(input.length() > 40)
        {
            System.out.println("\nInput cannot exceed 40 characters!\n");
            return false;
        }

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nPhone number cannot be null or empty!\n");
            return false;
        }

        if(input.matches("[\\d\\s]+") && input.matches(".*\\d.*"))
        {
            return true;
        }

        else
        {
            System.out.println("\nPlease enter a valid phone number!\n");
            return false;
        }
    }

    /**
     * Method to verify password entry. It is checked whether the password meets the specified criteria.
     * - At least 1 capital letter
     * - At least 1 lowercase letter
     * - At least 1 special character
     * - Minimum length of 8 characters, maximum length of 50 characters
     *
     * @param input Returns the password string to verify.
     * Returns true if the password is valid, false otherwise.
     */

    public boolean passwordValidation(String input)
    {
        // String input for password inputs
        // It will be used Authentication Class

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nPassword cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nPassword cannot contain spaces!\n");
            return false;
        }

        if(input.length() < 8 || input.length() > 50)
        {
            System.out.println("\nPassword should be between 8 and 50 characters!\n");
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(input).find()) {
            System.out.println("\nPassword must contain at least one uppercase letter!\n");
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(input).find()) {
            System.out.println("\nPassword must contain at least one lowercase letter!\n");
            return false;
        }

        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(input).find()) {
            System.out.println("\nPassword must contain at least one special letter!\n");
            return false;
        }

        if (!Pattern.compile("[0-9]").matcher(input).find())
        {
            System.out.println("\nPassword must contain at least one digit!\n");
            return false;
        }

        return true;
    }

    /**
     * Method that validates an email input. The email is ensured to meet basic format rules. Password:
     * - Contains a single '@'
     * - ends with '.com'
     * - No spaces or Turkish characters
     * @param input Returns the email string to validate.
     * Returns true if the email is valid, false otherwise.
     */

    public boolean emailValidation(String input)
    {
        // String input for email inputs

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nEmail cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nEmail cannot contain spaces!\n");
            return false;
        }

        if(input.length() > 100)
        {
            System.out.println("\nEmail cannot contain at most 100 characters!\n");
            return false;
        }

        if(!input.endsWith(".com"))
        {
            System.out.println("\nInvalid email format!\n");
            return false;
        }

        if (input.matches(".*[ğüşıöçĞÜŞİÖÇ].*")) {
            System.out.println("\nEmail cannot contain Turkish characters!\n");
            return false;
        }

        if (!Pattern.compile(".+@.+\\.com$").matcher(input).matches()) {
            System.out.println("\nInvalid email format!\n");
            return false;
        }

        if (input.chars().filter(ch -> ch == '@').count() != 1) {
            System.out.println("\nInvalid email format!\n");
            return false;
        }

        String domainPart = input.substring(input.indexOf('@') + 1, input.length() - 4);

        if (domainPart.isEmpty())
        {
            System.out.println("\nInvalid email format!\n");
            return false;
        }

        return true;
    }

    /**
     * Method that validates a date entry in YYYY-MM-DD format. Checks if the date is valid.
     * Follows the specified format and takes into account leap years in February.
     *
     * @param input Returns the date string to validate.
     * Returns true if the date is valid, false otherwise.
     */

    public boolean dateValidation(String input)
    {
        // String input for date inputs

        if(input == null || input.trim().isEmpty())
        {
            System.out.println("\nDate cannot be null or empty!\n");
            return false;
        }

        if(input.contains(" ") || input.contains("\t"))
        {
            System.out.println("\nDate cannot contain spaces!\n");
            return false;
        }

        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(input, formatter);

            if (date.getMonthValue() == 2 && date.getDayOfMonth() > 29)
            {
                System.out.println("\nInvalid date! February cannot have more than 29 days.\n");
                return false;
            }

            if (date.getMonthValue() == 2 && date.getDayOfMonth() == 29 && !date.isLeapYear())
            {
                System.out.println("\nInvalid date! February 29 is only valid in a leap year.\n");
                return false;
            }

            return true;
        }
        catch (DateTimeParseException e)
        {
            System.out.println("\nInvalid date format! Please use YYYY-MM-DD format.\n");
            return false;
        }
    }

}
