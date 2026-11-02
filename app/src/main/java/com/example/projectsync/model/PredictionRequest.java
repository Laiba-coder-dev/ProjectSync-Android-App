package com.example.projectsync.model;

public class PredictionRequest {

    int studytime;
    int failures;
    int absences;
    int G1;
    int G2;

    public PredictionRequest(int studytime, int failures,
                             int absences, int G1, int G2) {

        this.studytime = studytime;
        this.failures = failures;
        this.absences = absences;
        this.G1 = G1;
        this.G2 = G2;
    }
}