package algonquin.cst2335.lee00834;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.lee00834.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.lee00834.databinding.ReceiveMessageBinding;
import algonquin.cst2335.lee00834.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ArrayList<ChatMessage> chatMessages; //= new ArrayList<>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ChatRoom View Model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        chatMessages = chatModel.getChatMessages().getValue();
        if(chatMessages == null)
        {//initialize to the ViewModel arraylist:
            chatModel.getChatMessages().postValue( chatMessages = new ArrayList<>());
        }

        binding.recycleView.setAdapter( myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            // This function creates a ViewHolder object and represents a single row in the list
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //this inflates the row layout
                //int viewType is what layout to load
                if (viewType == 0) {                                                                //how big is parent?
                    SentMessageBinding rowBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    // this will initialize the row variables
                    return new MyRowHolder(rowBinding.getRoot());
                } else {
                    ReceiveMessageBinding rowBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(rowBinding.getRoot());
                }
            }

            // This initializes a ViewHolder to go at the row specified by the position parameter.
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage cm = chatMessages.get(position);

                // override the text in the rows:
                holder.messageText.setText(cm.getMessage()); // "Your actual message."
                holder.timeText.setText(cm.getTimeSent());
//                Timestamp ts = new Timestamp(System.currentTimeMillis());
//                holder.timeText.setText(ts.toString()); // "5:00 pm"
            }

            // the number of items:
            @Override
            public int getItemCount() {
                return chatMessages.size();
            }

            // This function just returns an int specifying how many items to draw.
            public int getItemViewType(int position) {
                boolean isSentButton = chatMessages.get(position).isSentButton();
                return isSentButton ? 0 : 1;
            }
        });
        // Sets LayoutManager
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // Send Button
        binding.sendButton.setOnClickListener(click -> {
            ChatMessage cm = new ChatMessage();
            String text = binding.textInput.getText().toString();
            // 4E: Monday 2E: Mon
            String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date());
            cm.setMessage(text);
            cm.setTimeSent(time);
            cm.setSentButton(true);
            chatMessages.add(cm);

            // notify the adapter:
//            myAdapter.notifyDataSetChanged(); //redraw the whole screen
            myAdapter.notifyItemInserted(chatMessages.size()-1); //tells the Adapter which row has to be redrawn
            //clear the previous text:
            binding.textInput.setText("");
        });

        // Receive Button
        binding.receiveButton.setOnClickListener(click -> {
            ChatMessage cm = new ChatMessage();
            String text = binding.textInput.getText().toString();
            // 4E: Monday 2E: Mon
            String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date());
            cm.setMessage(text);
            cm.setTimeSent(time);
            cm.setSentButton(false);
            chatMessages.add(cm);

            // notify the adapter:
//            myAdapter.notifyDataSetChanged(); //redraw the whole screen
            myAdapter.notifyItemInserted(chatMessages.size()-1); //tells the Adapter which row has to be redrawn
            //clear the previous text:
            binding.textInput.setText("");
        });
    }

    // this represents one row
    // Purpose: create this custom class to hold variables on one row.
    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        // just initialize the variables
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}