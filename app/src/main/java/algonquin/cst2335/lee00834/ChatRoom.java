package algonquin.cst2335.lee00834;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.lee00834.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.lee00834.databinding.ReceiveMessageBinding;
import algonquin.cst2335.lee00834.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private static ChatRoomViewModel chatModel;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    private ArrayList<ChatMessage> chatMessages; //= new ArrayList<>()

    MessageDatabase myDB ;
    ChatMessageDAO myDAO;
    private FragmentManager fMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // adds your toolbar
        setSupportActionBar(binding.toolbar);

        // Access the database:
        myDB = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MyChatMessageDB").build();
        myDAO = myDB.cmDAO(); //the only function in MessageDatabase;

        //ChatRoom View Model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        chatMessages = chatModel.getChatMessages().getValue();
        if(chatMessages == null)
        {//initialize to the ViewModel arraylist:
            chatModel.getChatMessages().postValue( chatMessages = new ArrayList<>());
            // get all messages: run the query in a separate thread
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                List<ChatMessage> fromDatabase = myDAO.getAllMessages();
                //Once you get the data from database
                chatMessages.addAll(fromDatabase);
                //You can then load the RecyclerView (must be done on the main UI thread)
                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter ));
            });
        }

        //register as a listener to the MutableLiveData object
        chatModel.getSelectedMessage().observe( this, selectedChatMessage -> {

            if(selectedChatMessage != null) {
                //This is a Singleton object
                fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                //What to show:
                MessageDetailsFragment chatFragment = new MessageDetailsFragment(selectedChatMessage);
                fMgr.popBackStack();
                //Where to load:
                // This line actually loads the fragment into the specified FrameLayout
                tx.replace(R.id.fragmentLocation, chatFragment);
                // Back to previous step
                tx.addToBackStack("anything here");
                tx.commit();
                // Another written codes using Builder Pattern:
                // getSupportFragmentManager().beginTransaction().add(R.id.fragmentLocation, chatFragment).commit();
            }
           });

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
            // ChatMessage cm = new ChatMessage(text,time,true);
            cm.setMessage(text);
            cm.setTimeSent(time);
            cm.setSentButton(true);
            // insert into ArrayList
            chatMessages.add(cm);
            // insert into database
            Executor thread = Executors.newSingleThreadExecutor();
            // new Runnable() has only 1 run() function, so could use Lambda ->
            thread.execute( () -> {
                // run on a second processor:
                cm.id = myDAO.insertMessage(cm); //returns the id
            });

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
            // insert into ArrayList
            chatMessages.add(cm);
            // insert into database
            Executor thread = Executors.newSingleThreadExecutor();
            // new Runnable() has only 1 run() function, so could use Lambda ->
            thread.execute( () -> {
                // run on a second processor:
                cm.id = myDAO.insertMessage(cm); //returns the id
            });

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

            // Click anywhere on the area of the ConstraintLayout area
            itemView.setOnClickListener( click ->{
                int position = getAbsoluteAdapterPosition();

                ChatMessage selected = chatMessages.get(position);
//                chatModel.selectedMessage.postValue(selected);
                chatModel.getSelectedMessage().postValue(selected);
                chatModel.getSelectedRow().postValue(position);

//                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
//                builder.setTitle( "Question:" )
//                        .setMessage( "Do you want to delete the message: " + messageText.getText() )
//                        .setPositiveButton( "No" , (dialog, cl) -> {})
//                        .setNegativeButton( "Yes" , (dialog, cl) -> {
//                            ChatMessage removeMessage = chatMessages.get(position);
//                            // Deletes the chatMessage in the Database and runs in another thread
//                            Executors.newSingleThreadExecutor().execute(() -> {
//                                myDAO.deleteMessage(removeMessage);
//                            });
//                            chatMessages.remove(position);
//                            myAdapter.notifyItemRemoved(position);
//                                                                                    //position starts from 0
//                            Snackbar.make( messageText, "You deleted message #" + (position + 1), Snackbar.LENGTH_LONG)
//                                    .setAction("Undo", clk -> {
//                                        // Re-inserts the chatMessage into the Database
//                                        Executors.newSingleThreadExecutor().execute(() -> {
//                                            myDAO.insertMessage(removeMessage);
//                                        });
//                                        chatMessages.add(position, removeMessage);
//                                        myAdapter.notifyItemInserted(position);
//                                    })
//                                    .show();
//                        })
//                        .create().show(); //actually make the window appear
            });
        }
    }

    // This function is to load a Menu layout file under the /res/menu folder.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate( R.menu.my_menu, menu );

        return true;
    }
    // When user clicks on a menuitem:
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        ChatMessage theMessage = chatModel.getSelectedMessage().getValue();

        if (itemId == R.id.item_delete && theMessage != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle( "Question:" )
                        .setMessage( "Do you want to delete the chat message?" )
                        .setPositiveButton( "No" , (dialog, cl) -> {})
                        .setNegativeButton( "Yes" , (dialog, cl) -> {
                            int selectedRow = chatModel.getSelectedRow().getValue();
                            // Deletes the chatMessage in the Database and runs in another thread
                            Executors.newSingleThreadExecutor().execute(() -> {
                                myDAO.deleteMessage(theMessage);
                            });
                            chatMessages.remove(selectedRow);
                            myAdapter.notifyItemRemoved(selectedRow);
                            fMgr.popBackStack();
                            chatModel.getSelectedMessage().postValue(null);
                        })
                        .create().show(); //actually make the window appear
        }
        else if (itemId == R.id.item_about){
            Toast.makeText( ChatRoom.this ,"Version 1.0, created by Wan-Hsuan",Toast.LENGTH_LONG).show();
        }

        return true;
    }

}