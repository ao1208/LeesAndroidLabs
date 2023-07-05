package algonquin.cst2335.lee00834;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity // A table called ChatMessage
public class ChatMessage {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="ID")
    public long id;
    @ColumnInfo(name="Message")
    protected String message;
    @ColumnInfo(name="Time")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    protected boolean isSentButton;

    ChatMessage(){} // for database query

    ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }

}
