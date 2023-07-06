package algonquin.cst2335.lee00834;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.lee00834.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage cm){
        selected = cm;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.Message.setText(selected.message);
        binding.Time.setText(selected.timeSent);
        binding.isSentMessage.setText("isSentMessage = " + String.valueOf(selected.isSentButton()));
        binding.DBID.setText("ID = " + selected.id);

        return binding.getRoot();
    }
}
