import java.util.List;

public class TaskList {
    private List<Task> allTasks;

    public TaskList(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    public Task getTask(int index) {
        return this.allTasks.get(index);
    }

    public List<Task> getTasks() {
        return this.allTasks;
    }

    public void addTask(Task t) {
        this.allTasks.add(t);
    }

    public Task removeTask(int index) {
        return this.allTasks.remove(index);
    }

    public int getSize() {
        return this.allTasks.size();
    }
    public boolean isEmpty() {
        return this.allTasks.isEmpty();
    }

    @Override
    public String toString() {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < allTasks.size() + 1; i++ ) {
            stringBuilder.append(String.format(i + "." + allTasks.get(i-1).toString() + "\n"));
        }
        return result;
    }
}
