package bruh.tasklist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import bruh.task.LocalDateTimeOrString;
import bruh.task.Task;

/**
 * Encapsulates a list of tasks, as well as associated functions.
 */
public class TaskList implements Serializable {
    private final List<Task> tasks;
    private final boolean isFiltered;

    /**
     * Constructor for a pre-existing task list.
     *
     * @param tasks The pre-existing list of tasks.
     */
    private TaskList(List<Task> tasks) {
        this.tasks = tasks;
        this.isFiltered = true;
    }

    /**
     * Constructor for a new task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.isFiltered = false;
    }


    /**
     * Returns a list of all the tasks in the list as a string.
     *
     * @return A string of all the tasks in the list.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return getTaskCountDesc();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ").append(isFiltered ? "matching " : "").append("tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("\n%d.%s", i + 1, tasks.get(i).toString()));
        }
        return sb.toString();
    }

    /**
     * Returns a description of the number of tasks in the list.
     *
     * @return A description of the number of tasks in the list.
     */
    public String getTaskCountDesc() {
        return tasks.isEmpty() ? "There are no " + (isFiltered ? "matching " : "") + "tasks."
                : String.format("Now you have %d%s task%s in the list.", tasks.size(), (isFiltered ? " matching" : ""),
                tasks.size() == 1 ? "" : "s");
    }

    /**
     * Adds a specified task to the list.
     *
     * @param taskToAdd The task to be added to the list.
     */
    public void add(Task taskToAdd) {
        tasks.add(taskToAdd);
    }

    /**
     * Removes a specified task from the list.
     *
     * @param indexToDelete The index of the task to be removed.
     * @return The removed task.
     */
    public Task remove(int indexToDelete) {
        return tasks.remove(indexToDelete);
    }

    /**
     * Marks a specified task in the list as done.
     *
     * @param indexToMarkDone The index of the task to be mark done.
     * @return The task marks as done.
     */
    public Task markAsDone(int indexToMarkDone) {
        Task task = tasks.get(indexToMarkDone);
        task.markAsDone();
        return task;
    }

    /**
     * Filters the task list by a specified predicate,
     * returning a new task list.
     *
     * @param predicate The predicate to filter the task list by.
     * @return A new task list containing the filtered items.
     */
    private TaskList filterByPredicate(Predicate<Task> predicate) {
        List<Task> filtered = tasks.stream().filter(predicate).collect(Collectors.toList());
        return new TaskList(filtered);
    }

    /**
     * Filters the task list by a specified date & time.
     *
     * @param dateTimeOrString The date & time to filter by.
     * @return A new task list containing the filtered items.
     */
    public TaskList filterByDateTimeOrString(LocalDateTimeOrString dateTimeOrString) {
        return filterByPredicate(task -> task.isAtDateTime(dateTimeOrString));
    }

    /**
     * Filters the task list by a specified search term.
     *
     * @param searchTerm The search term to filter by.
     * @return A new task list containing the filtered items.
     */
    public TaskList filterBySearch(String searchTerm) {
        return filterByPredicate(task -> task.descriptionContains(searchTerm));
    }
}
