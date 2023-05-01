package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoegame.MainActivity;
import com.example.tictactoegame.databinding.ActivityGamePlayBinding;

public class GamePlayActivity extends AppCompatActivity {

    ActivityGamePlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamePlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setButtonListeners();
        String[] playerNames = getNames();

        if(playerNames != null){
            binding.textUserTurn.setText(playerNames[0] + "'s turn");
        }

        binding.buttonPlayAgain.setVisibility(View.GONE);
        binding.buttonHome.setVisibility(View.GONE);
        binding.gameboard.setUpGame(binding.buttonPlayAgain, binding.buttonHome, binding.textUserTurn, playerNames);
    }

    private String[] getNames() {
        return getIntent().getStringArrayExtra("PLAYER_NAMES");
    }

    private void setButtonListeners() {
        binding.buttonHome.setOnClickListener(view -> {
            startActivity(new Intent(GamePlayActivity.this, MainActivity.class));
        });

        binding.buttonPlayAgain.setOnClickListener(view -> {
            binding.gameboard.resetGame();
            binding.gameboard.invalidate();

        });
    }
}