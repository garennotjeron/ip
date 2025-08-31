package dk.tasks;

import java.util.ArrayList;
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

    /**
     * Filters and displays all tasks in the current list that include the keyword given by the user.
     * @param keyword The keyword to be checked in each task's description
     * @return A list of tasks that include the keyword in its description
     */
    public List<Task> filterTasks(String keyword) {
        List<Task> filtered = new ArrayList<>();
        for (Task t: this.allTasks) {
            if (t.getDescription().contains(keyword)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < allTasks.size() + 1; i++ ) {
            stringBuilder.append(String.format(i + "." + allTasks.get(i-1).toString()));
            if (i < allTasks.size()) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
