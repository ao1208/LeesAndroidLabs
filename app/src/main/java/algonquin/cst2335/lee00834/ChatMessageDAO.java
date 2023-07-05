package algonquin.cst2335.lee00834;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Insert // after inserting, the database returns id as int. The return type could be void.
    long insertMessage(ChatMessage cm);

    @Query("Select * from ChatMessage")
    List<ChatMessage> getAllMessages();

    @Update
    int updateMessage(ChatMessage cm);

    @Delete // number of rows deleted, should be 1 if id matches. The return type could be void.
    int deleteMessage(ChatMessage cm);

}
