package com.example.projectsync.ui.pmo;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsync.ApiService;
import com.example.projectsync.R;
import com.example.projectsync.model.PredictionRequest;
import com.example.projectsync.model.PredictionResponse;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerformancePredictionActivity extends AppCompatActivity {

    EditText etStudy, etFail, etAbs, etG1, etG2;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_prediction);

        etStudy = findViewById(R.id.et_studytime);
        etFail = findViewById(R.id.et_failures);
        etAbs = findViewById(R.id.et_absences);
        etG1 = findViewById(R.id.et_g1);
        etG2 = findViewById(R.id.et_g2);
        tvResult = findViewById(R.id.tv_result);

        findViewById(R.id.btn_predict).setOnClickListener(v -> predict());
    }

    private void predict() {

        if(etStudy.getText().toString().isEmpty() ||
                etFail.getText().toString().isEmpty() ||
                etAbs.getText().toString().isEmpty() ||
                etG1.getText().toString().isEmpty() ||
                etG2.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int study = Integer.parseInt(etStudy.getText().toString());
        int fail = Integer.parseInt(etFail.getText().toString());
        int abs = Integer.parseInt(etAbs.getText().toString());
        int g1 = Integer.parseInt(etG1.getText().toString());
        int g2 = Integer.parseInt(etG2.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://allan-nondiaphanous-inhomogeneously.ngrok-free.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        PredictionRequest request =
                new PredictionRequest(study, fail, abs, g1, g2);

        api.predict(request).enqueue(new Callback<PredictionResponse>() {

            @Override
            public void onResponse(Call<PredictionResponse> call,
                                   Response<PredictionResponse> response) {

                if(response.isSuccessful() && response.body() != null) {
                    tvResult.setText("Prediction: " +
                            response.body().result);
                } else {
                    tvResult.setText("Server Error");
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call,
                                  Throwable t) {
                tvResult.setText("Error: " + t.getMessage());
            }
        });
    }
}