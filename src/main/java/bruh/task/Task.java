package bruh.task;

import java.io.Serializable;

import bruh.exception.BruhException;
import bruh.exception.InvalidArgumentException;

/**
 * Represents a task that can be mark as done.
 */
public abstract class Task implements Serializable {
    private final String description;
    private final char symbol;
    private boolean isDone;

    /**
     * Constructor for a task that can be mark as done.
     *
     * @param description The description of the task.
     * @param symbol      The symbol representing the task type.
     */
    public Task(String description, char symbol) {
        this.description = description;
        this.symbol = symbol;
        this.isDone = false;
    }

    /**
     * Returns the appropriate task, as specified by the keyword in the input.
     *
     * @param  inputs        An array of the string input, divided into the first word and the rest
     *                       of the input.
     * @return               The task as specified by the input.
     * @throws BruhException if an error occurs in creating the task.
     */
    public static Task createTask(String[] inputs) throws BruhException {
        assert inputs.length == 2 : "2 elements in inputs array";
        String keyword = inputs[0];

        switch (keyword) {
        case "todo":
            return new TodoTask(inputs[1]);
        case "deadline":
        case "event":
        case "doafter":
            return TimedTask.createTimedTask(inputs);
        default:
            throw new InvalidArgumentException();
        }
    }

    /**
     * Checks if a task's date & time corresponds with the specified date & time.
     *
     * @param  dateTimeOrString The specified date & time to be checked against.
     * @return                  True if the date & time match, false otherwise.
     */
    public boolean isAtDateTime(LocalDateTimeOrString dateTimeOrString) {
        return false;
    }

    /**
     * Checks if a task's description contains a specified search term.
     *
     * @param  searchTerm The search term to be checked against.
     * @return            True if the task description contains the search term, false otherwise.
     */
    public boolean descriptionContains(String searchTerm) {
        return description.contains(searchTerm);
    }

    /**
     * Marks the task as done.
     */
    public Task markAsDone() {
        isDone = true;
        return this;
    }

    private String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", symbol, getStatusIcon(), description);
    }
}
