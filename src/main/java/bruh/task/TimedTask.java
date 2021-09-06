package bruh.task;

import bruh.exception.BruhException;
import bruh.exception.InvalidArgumentException;
import bruh.parser.Parser;

/**
 * Represents a task with an associated date and time.
 */
public abstract class TimedTask extends Task {
    private static final String DEADLINE_MISSING_ARG_ERROR_MSG =
            "Please specify the date & time of your deadline.";
    private static final String EVENT_MISSING_ARG_ERROR_MSG =
            "Please specify the date & time of your event.";

    private final LocalDateTimeOrString dateTimeOrString;

    /**
     * Constructor for a task with an associated date and time.
     *
     * @param description The description of the task.
     * @param symbol The symbol representing the task type.
     * @param dateTimeInput The date and time associated with the task.
     */
    public TimedTask(String description, char symbol, String dateTimeInput) {
        super(description, symbol);
        this.dateTimeOrString = new LocalDateTimeOrString(dateTimeInput);
    }

    /**
     * Returns the appropriate timed task, as specified by the keyword in the input.
     *
     * @param inputs An array of the string input, divided into the first word and the rest of the
     *        input.
     * @return The timed task as specified by the input.
     * @throws BruhException If an error occurs in creating the task.
     */
    public static TimedTask createTimedTask(String[] inputs) throws BruhException {
        String keyword = inputs[0];

        switch (keyword) {
        case "deadline":
            inputs = inputs[1].split(" /by ", 2);
            Parser.checkMissingArguments(inputs, DEADLINE_MISSING_ARG_ERROR_MSG);
            return new Deadline(inputs[0], inputs[1]);
        case "event":
            inputs = inputs[1].split(" /at ", 2);
            Parser.checkMissingArguments(inputs, EVENT_MISSING_ARG_ERROR_MSG);
            return new Event(inputs[0], inputs[1]);
        default:
            throw new InvalidArgumentException();
        }
    }

    @Override
    public boolean isAtDateTime(LocalDateTimeOrString dateTimeOrString) {
        return this.dateTimeOrString.equals(dateTimeOrString);
    }

    /**
     * Returns a description of the task's date & time.
     *
     * @return A description of the task's date & time.
     */
    protected String getDateTimeDesc() {
        return dateTimeOrString.toString();
    }
}
