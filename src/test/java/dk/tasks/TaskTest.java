package dk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void getCompletion_success() {
        Task t1 = new Task("already done", true);
        assertEquals("X",  t1.getCompletion());

        Task t2 = new Task("not done yet", false);
        assertEquals(" ", t2.getCompletion());
    }

    @Test
    void updateCompletion_success() {
        Task t = new Task ("testing", true);
        assertEquals("X", t.getCompletion());
        t.updateCompletion();
        assertEquals(" ", t.getCompletion());
    }
}
