package IgorPhone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class PhoneBook {

    String fileName = "D:/Java/contacts.csv"; // Write here path to contacts file.
    File f = new File(fileName);

    public ArrayList<People> contacts = new ArrayList<People>();

    Scanner sc = new Scanner(System.in);


    /**
     * His is a function that adds a new number.
     * At this stage, we check how many characters are in the name
     * In addition, a typed phone number is also checked
     */

    public void addNewContact() throws IOException {
        People p = new People();
        int counterOfName = 0;
        int counterOfNumber = 0;

        while (true) {
            System.out.println("Please enter the correct contact name. Name must be in range 4 - 30 symbols.");
            p.name = sc.next();
            if (p.name.matches("^.{4,30}$")) {
                break;
            } else {
                counterOfName++;
                if (counterOfName > 2) {
                    System.out.println("You have tried to enter an incorrect name 3 times. Phone Book is terminated now");
                    System.exit(0);

                }

            }

        }
        p.setName(p.name);


        while (true) {
            System.out.println("Please enter the correct contact number. Number may contain only '+', spaces and digits. Min length 8, max length 15.");
            p.phoneNumber = sc.next();
            if (p.phoneNumber.matches("^\\+?[0-9 ]{8,15}$")) {
                break;
            } else {
                counterOfNumber++;
                if (counterOfNumber > 2) {
                    System.out.println("You have tried to enter an incorrect number 3 times. Phone Book is terminated now");
                    System.exit(0);

                }
            }

        }
        p.setPhoneNumber(p.phoneNumber);
        contacts.add(p);
        System.out.println("Successfully added contact " + p.name + " to Phone Book");


    }


    /**
     * This is a function that saves all data in an external file
     */
    public void saveToFile() throws IOException {

        FileWriter fw = new FileWriter(f, true);

        if (f.exists()) {

            System.err.println("file exists");
        } else {

            System.err.println("Create new file");
            f.createNewFile();
        }
        for (People people : contacts) {

            System.out.println("name " + people.getName() + " save to file ");
            System.out.println("and number " + people.getPhoneNumber() + " save to file ");
            fw.write(String.format("\"%s\" ", people.getName()));
            fw.write(", ");
            fw.write(String.format("\"%s\" ", people.getPhoneNumber()));
            fw.write("\n");
        }
        fw.close();
        contacts.clear();
    }

    /**
     * There is a function of sorting the name values in reverse order.
     */
    public void sortingInReverseOrder() throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        System.out.println("This list sorting  by alphabetically");
        Collections.sort(lines);
        System.out.println(lines);
        System.out.println();
        System.out.println("This list sorting by alphabetically in reverse order");
        Collections.sort(lines, Collections.reverseOrder());
        System.out.println(lines);
    }

    /**
     * There is a function that returns duplicate values.
     */
    public void viewDuplicateValues() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        Set<String> set = new HashSet<String>();
        for (String line : lines) {
            boolean flag = set.add(line);
            if (!flag) {
                System.out.println(line + " is duplicate name and number");
            }

        }

    }


    /**
     * This is a function that returns a list of all entries in the book.
     * In case there is no record in the book, a detailed  message appears.
     * In addition, there is a function of sorting the name values in reverse order.
     */
    public void readContacts() throws IOException {

        try {
            System.out.println("Below is a list of all contacts in the Phone book");
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\\Z");
            System.out.println("Name   ||  Number");
            System.out.println(sc.next());
        } catch (Exception e) {
            System.out.println("You Phone book is empty contacts or not found in specific path");

        }
    }

    /**
     * This is a function that deletes a number from the phone book.
     * There is a validation check to see if the name exists in the phone book.
     */
    public void deleteNewContact() throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        System.out.println("Please enter contact name for delete:");
        Scanner input = new Scanner(System.in);
        String valueToDelete = input.nextLine().trim();
        for (String s : lines) {
            if (s.equals(valueToDelete)) {
                //         contacts.remove(valueToDelete);
                s.replace(valueToDelete, null);
                writer.write(lines + System.lineSeparator());
                System.out.println(contacts);
                System.out.println(lines);
            }

        }

    }

    /**
     * This is a function where you can search a name by user number.
     */
    public void FindContactInFile() throws IOException {
        System.out.println("Please enter the name you would like to search for: ");
        Scanner kb = new Scanner(System.in);
        String name = kb.nextLine();
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            if (line.contains(name)) {
                System.out.println("The following results were found for contact " + name);
                System.out.println(line);

            }
        }

    }

    /**
     * This is a function where you can edit a number by name.
     * In addition, it is checked whether a typed name exists in the file
     */
    public void changeValue() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        Scanner input = new Scanner(System.in);
        System.out.println("Enter old name");
        String valueToChange = input.nextLine().trim();
        for (String line1 : lines) {
            if (line1.contains(valueToChange)) {
                System.out.println("Enter new name");
                String newValue = input.nextLine().trim();
                FileWriter writer = new FileWriter(fileName);
                f.delete();
                f.createNewFile();
                for (String line : lines) {
                    line.replace(valueToChange, newValue);
                    lines = Collections.singletonList((line.replace(valueToChange, newValue)));
                    writer.write(lines + System.lineSeparator());
                    System.out.println(lines);

                }
                writer.close();
                System.out.println("The name " + valueToChange + " was changed to " + newValue);
                break;

            } else {
                System.out.println("The name " + valueToChange + " does not appears in Phone book");
                break;
            }


        }

    }

    /**
     * This is a function that returns a main menu.
     * It's shows all the commands that a user can run in the program.
     */
    public void userMenu() throws IOException {

        System.out.println("Hello , please choose from the following options: \n add - add new contact , \n change - update exists contact , \n read - list of all contacts in Phone book , \n delete -  delete specific contact by name, \n search -  search contact by name, \n reverse - sorting list in alphabetically and reverse order, \n duplicate - show duplicate values in Phone book   \n help - for help"
                + ",\n exit -  quit. ");

        Scanner input = new Scanner(System.in);
        String line = input.nextLine().trim();

        while (!line.equals("exit")) {

            switch (line) {

                case "add":
                    System.out.println("your choose - add");
                    addNewContact();
                    saveToFile();
                    break;

                case "reverse":
                    System.out.println("your choose - reverse");
                    sortingInReverseOrder();
                    break;

                case "delete":
                    System.out.println("your choose - delete");
                    deleteNewContact();
                    break;

                case "read":
                    System.out.println("your choose - read");
                    readContacts();
                    break;

                case "exit":
                    break;

                case "search":
                    System.out.println("your choose - search");
                    FindContactInFile();
                    break;

                case "duplicate":
                    System.out.println("your choose - Duplicate");
                    viewDuplicateValues();
                    break;

                case "help":
                    userMenu();

                case "change":
                    System.out.println("your choose - change");
                    changeValue();
                    break;

                default:
                    System.err.println("Invalid command please try again");
                    break;

            }
            System.out.print("\n> ");
            line = input.nextLine().trim();

        }
        System.out.println("Good bay, Phone Book is terminated now.");
        System.exit(0);
        input.close();
    }
}










