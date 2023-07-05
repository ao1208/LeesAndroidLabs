package algonquin.cst2335.lee00834;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    // Which DAO do we use for this database:
    public abstract ChatMessageDAO cmDAO();//only 1 function for how to interact
}
