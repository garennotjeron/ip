package dk.storage;

import dk.tasks.TaskList;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {


    private final String filePath = "data";
    private final String fileName = filePath + "/testing.txt";

    @Test
    void creationOfFileWhenMissing_success() {
        assertFalse(Files.exists(Paths.get(this.fileName)));
        Storage s = new Storage(this.filePath, this.fileName);
        assertTrue(Files.exists(Paths.get(this.fileName)));
        try {
            Files.delete(Paths.get(this.fileName));
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    void loadTasks_success() {
        String withContent = filePath + "/filled.txt";
        Storage s = new Storage(this.filePath, withContent);
        TaskList tl = s.getAllTasks();

        assertTrue(Files.exists(Paths.get(withContent)));

        assertEquals(2, tl.getSize());

        assertEquals("return book", tl.getTask(0).getDescription());
        assertEquals("X", tl.getTask(0).getCompletion());

        assertEquals("join book club", tl.getTask(1).getDescription());
        assertEquals(" ", tl.getTask(1).getCompletion());
    }
}
