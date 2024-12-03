// For Password Validation
import java.util.regex.Pattern;

// For Date Validation
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidation {

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

    public boolean noNumberValidation(String input)
    {
        // String input for inputs without any number
        // Like; name, surname

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

    public boolean phoneNoValidation(String input)
    {
        // String input for inputs without letter
        // Like; phone_no

        // This method will be used for phone no inputs
        // Phone numbers will be like:
        // User will input (with country code) 905383599269

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

    public boolean passwordValidation(String input)
    {
        // String input for password inputs
        // For password
        // It will be used Authentication Class

        // Password has to:
            // at least 1 Uppercase letter
            // at least 1 Lowercase letter
            // at least 1 Special character
            // at least 1 Digit
            // minimum 8 length
            // maximum 50 length

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

    public boolean dateValidation(String input)
    {
        // String input for date inputs

        // Date should be input like:
            // YYYY-MM-DD

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
