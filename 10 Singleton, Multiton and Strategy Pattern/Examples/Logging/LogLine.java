public class LogLine {

    private final String timestamp;
    private String logString;

    public LogLine(String logString, String timestamp) {
        this.logString = logString;
        this.timestamp = timestamp;
    }

    public String getLogString() {
        return logString;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return  "["+timestamp+"]" + " " + logString;
    }
}
