import java.io.Serializable;

public abstract class Task implements Serializable {
    private String description;
    private char symbol;
    private boolean isDone;

    public Task(String description, char symbol) {
        this.description = description;
        this.symbol = symbol;
        this.isDone = false;
    }

    public static Task createTask(String[] inputs) throws DukeException {
        String keyword = inputs[0];

        switch (keyword) {
        case "todo":
            return new Todo(inputs[1]);
        case "deadline":
        case "event":
            return TimedTask.createTimedTask(inputs);
        default:
            throw new InvalidArgumentException();
        }
    }

    public boolean isAtTime(LocalDateTimeOrString dateTimeOrString) {
        return false;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", symbol, getStatusIcon(), description);
    }
}
