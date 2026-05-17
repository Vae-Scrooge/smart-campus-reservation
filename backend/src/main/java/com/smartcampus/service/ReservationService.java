package com.smartcampus.service;

import com.smartcampus.dto.ReservationRequest;
import com.smartcampus.model.Resource;
import com.smartcampus.model.Reservation;
import com.smartcampus.model.User;
import com.smartcampus.repository.ReservationRepository;
import com.smartcampus.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository,
                              ResourceRepository resourceRepository,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
        this.userService = userService;
    }

    @Transactional
    public Reservation createReservation(String username, ReservationRequest req) {
        User user = userService.getUserByUsername(username);
        Resource resource = resourceRepository.findById(req.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("资源不存在"));

        if (!"AVAILABLE".equals(resource.getStatus())) {
            throw new IllegalArgumentException("该资源当前不可用");
        }

        if (req.getStartTime().isAfter(req.getEndTime()) || req.getStartTime().equals(req.getEndTime())) {
            throw new IllegalArgumentException("预约时间不合法");
        }

        boolean conflict = reservationRepository.existsConflict(
                req.getResourceId(), req.getDate(),
                req.getStartTime(), req.getEndTime());
        if (conflict) {
            throw new IllegalArgumentException("该时间段已被预约");
        }

        if (user.getCreditScore() < 60) {
            throw new IllegalArgumentException("信用分过低（低于60），暂时无法预约");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setDate(req.getDate());
        reservation.setStartTime(req.getStartTime());
        reservation.setEndTime(req.getEndTime());
        reservation.setStatus("PENDING");

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(String username) {
        User user = userService.getUserByUsername(username);
        return reservationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Reservation cancelReservation(String username, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("预约记录不存在"));

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("只能取消自己的预约");
        }

        if (!"PENDING".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("当前状态不允许取消");
        }

        reservation.setStatus("CANCELLED");
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation checkIn(String username, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("预约记录不存在"));

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("只能签到自己的预约");
        }

        if (!"PENDING".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("当前状态不允许签到");
        }

        reservation.setStatus("CHECKED_IN");
        reservation.setCheckinTime(LocalDateTime.now());
        Reservation saved = reservationRepository.save(reservation);

        userService.updateCreditScore(reservation.getUser().getId(), 2);
        return saved;
    }

    @Transactional
    public void processNoShows() {
        List<Reservation> pending = reservationRepository.findByStatus("PENDING");
        LocalDateTime now = LocalDateTime.now();

        for (Reservation r : pending) {
            LocalDateTime deadline = LocalDateTime.of(r.getDate(), r.getStartTime());
            if (now.isAfter(deadline.plusMinutes(30))) {
                r.setStatus("NO_SHOW");
                reservationRepository.save(r);
                userService.updateCreditScore(r.getUser().getId(), -10);
            }
        }
    }
}
