import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class DK {

    private static final List<String> allInputs = new ArrayList<>(); // limit set for size of array for all user inputs

    public static void main(String[] args) {
        printLine();
        printIntro();
        echoInput();
    }

    public static void printIntro() {
        System.out.println("Hello! I'm DK \nWhat can I do for you?");
        printLine();
    }

    public static void printEnding() {
        System.out.println("Bye byeee. Hope to see you again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println("__________________________________");
    }

    public static void echoInput() {
        String userInput = "";
        while (!userInput.equals("bye")) {
            Scanner scanner = new Scanner(System.in);
            userInput = scanner.nextLine();
            printLine();
            if (userInput.equals("bye")) {
                printEnding();
            } else if (userInput.equals("list")) {
                displayList();
            }
            else {
                allInputs.add(userInput);
                System.out.println("added: " + userInput);
            }
        }
    }

    public static void displayList() {
        for (int i = 1; i < allInputs.size() + 1; i++) {
            System.out.println(i + ". " + allInputs.get(i-1));
        }
    }

}

