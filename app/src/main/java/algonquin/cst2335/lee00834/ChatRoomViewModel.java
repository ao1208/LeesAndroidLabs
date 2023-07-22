package algonquin.cst2335.lee00834;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ChatMessage>> chatMessages;
    private final MutableLiveData<ChatMessage> selectedMessage;
    private final MutableLiveData<Integer> selectedRow;

    public ChatRoomViewModel() {
        chatMessages = new MutableLiveData<>();
        selectedMessage = new MutableLiveData<>();
        selectedRow = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<ChatMessage>> getChatMessages() {
        return chatMessages;
    }

    public MutableLiveData<ChatMessage> getSelectedMessage() {return selectedMessage;}

    public MutableLiveData<Integer> getSelectedRow() {return selectedRow;}


}
