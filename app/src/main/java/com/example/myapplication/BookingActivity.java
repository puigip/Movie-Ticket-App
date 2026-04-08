package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private TextView tvBookingTitle;
    private EditText etSeats;
    private Button btnConfirm;
    private FirebaseFirestore db;
    private String movieId, movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = FirebaseFirestore.getInstance();
        movieId = getIntent().getStringExtra("MOVIE_ID");
        movieTitle = getIntent().getStringExtra("MOVIE_TITLE");

        tvBookingTitle = findViewById(R.id.tvBookingTitle);
        etSeats = findViewById(R.id.etSeats);
        btnConfirm = findViewById(R.id.btnConfirmBooking);

        tvBookingTitle.setText("Đặt vé: " + movieTitle);

        btnConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        String seatsStr = etSeats.getText().toString().trim();
        if (TextUtils.isEmpty(seatsStr)) {
            Toast.makeText(this, "Vui lòng nhập số ghế!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> seats = Arrays.asList(seatsStr.split(",\\s*"));
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setShowtimeId("MOCK_SHOWTIME_ID_" + movieId); // Trong thực tế sẽ chọn từ list showtimes
        ticket.setSeatNumbers(seats);
        ticket.setTotalAmount(seats.size() * 100000.0);
        ticket.setBookingDate(new Date());
        ticket.setStatus("BOOKED");

        db.collection("tickets")
                .add(ticket)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Đặt vé thành công!", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi đặt vé: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
