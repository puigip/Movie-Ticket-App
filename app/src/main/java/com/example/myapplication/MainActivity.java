package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView rvMovies;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is logged in
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        rvMovies = findViewById(R.id.rvMovies);
        progressBar = findViewById(R.id.progressBar);

        movieList = new ArrayList<>();
        adapter = new MovieAdapter(movieList, this);

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setAdapter(adapter);

        fetchMovies();
    }

    private void fetchMovies() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movie.setId(document.getId());
                            movieList.add(movie);
                        }
                        adapter.notifyDataSetChanged();
                        
                        if (movieList.isEmpty()) {
                            Toast.makeText(this, "Chưa có phim nào. Hãy thêm dữ liệu trên Firestore Console!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Lỗi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("MOVIE_ID", movie.getId());
        intent.putExtra("MOVIE_TITLE", movie.getTitle());
        startActivity(intent);
    }
}
