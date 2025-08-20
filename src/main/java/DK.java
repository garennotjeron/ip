import java.util.Scanner;

public class DK {
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
            } else {
            System.out.println(userInput);
            }
        }
    }
}

