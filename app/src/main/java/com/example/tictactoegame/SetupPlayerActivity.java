package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tictactoegame.databinding.ActivitySetupPlayerBinding;

public class SetupPlayerActivity extends AppCompatActivity {

    ActivitySetupPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setupListeners();
    }

    private void setupListeners() {

        binding.buttonStart.setOnClickListener(view -> {
            String player1 = binding.edittextPlayer1.getText().toString();
            String player2 = binding.edittextPlayer2.getText().toString();
            if(player1.equals("") || player2.equals("")){
                Toast.makeText(SetupPlayerActivity.this, "ENTER THE PLAYER NAMES FIRST",
                        Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(SetupPlayerActivity.this, GamePlayActivity.class);
                intent.putExtra("PLAYER_NAMES", new String[] {player1, player2});
                startActivity(intent);
            }
        });





    }
}