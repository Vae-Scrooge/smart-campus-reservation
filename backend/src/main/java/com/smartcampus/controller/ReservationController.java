package com.smartcampus.controller;

import com.smartcampus.dto.ApiResponse;
import com.smartcampus.dto.ReservationRequest;
import com.smartcampus.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ApiResponse<?> createReservation(Principal principal,
                                            @Valid @RequestBody ReservationRequest req) {
        return ApiResponse.success("预约成功",
                reservationService.createReservation(principal.getName(), req));
    }

    @GetMapping
    public ApiResponse<?> getMyReservations(Principal principal) {
        return ApiResponse.success(
                reservationService.getUserReservations(principal.getName()));
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<?> cancelReservation(Principal principal,
                                            @PathVariable Long id) {
        return ApiResponse.success("取消成功",
                reservationService.cancelReservation(principal.getName(), id));
    }

    @PostMapping("/{id}/checkin")
    public ApiResponse<?> checkIn(Principal principal,
                                  @PathVariable Long id) {
        return ApiResponse.success("签到成功",
                reservationService.checkIn(principal.getName(), id));
    }
}
