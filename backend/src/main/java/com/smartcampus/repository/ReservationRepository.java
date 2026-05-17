package com.smartcampus.repository;

import com.smartcampus.model.Reservation;
import com.smartcampus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserOrderByCreatedAtDesc(User user);

    List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Reservation> findByResourceIdAndDate(Long resourceId, String date);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.resource.id = :resourceId " +
           "AND r.date = :date AND r.status NOT IN ('CANCELLED') " +
           "AND ((r.startTime <= :endTime AND r.endTime >= :startTime))")
    boolean existsConflict(
        @Param("resourceId") Long resourceId,
        @Param("date") String date,
        @Param("startTime") String startTime,
        @Param("endTime") String endTime
    );

    List<Reservation> findByStatus(String status);
}
