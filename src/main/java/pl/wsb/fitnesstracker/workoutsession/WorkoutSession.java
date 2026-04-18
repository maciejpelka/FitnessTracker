package pl.wsb.fitnesstracker.workoutsession;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.training.api.Training;

import java.time.LocalDateTime;

@Entity
@Table(name = "workout_session")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;
    private Double altitude;

    public WorkoutSession(
            final Training training,
            final LocalDateTime timestamp,
            final Double startLatitude,
            final Double startLongitude,
            final Double endLatitude,
            final Double endLongitude,
            final Double altitude) {
        this.training = training;
        this.timestamp = timestamp;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.altitude = altitude;
    }
}