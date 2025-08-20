public class DK {
    public static void main(String[] args) {
        printLine();
        printIntro();
        printEnding();
    }

    public static void printIntro() {
        System.out.println("Hello! I'm DK \nWhat can I do for you?");
        printLine();
    }

    public static void printEnding() {
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println("__________________________________");
    }
}

