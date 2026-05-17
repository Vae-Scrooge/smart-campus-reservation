package com.smartcampus.controller;

import com.smartcampus.dto.ApiResponse;
import com.smartcampus.model.Resource;
import com.smartcampus.model.Reservation;
import com.smartcampus.repository.ReservationRepository;
import com.smartcampus.repository.ResourceRepository;
import com.smartcampus.service.ResourceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ResourceService resourceService;
    private final ReservationRepository reservationRepository;

    public AdminController(ResourceService resourceService,
                           ReservationRepository reservationRepository) {
        this.resourceService = resourceService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/resources")
    public ApiResponse<?> getAllResources() {
        return ApiResponse.success(resourceService.getAllResources());
    }

    @PostMapping("/resources")
    public ApiResponse<?> createResource(@RequestBody Resource resource) {
        return ApiResponse.success("新增成功", resourceService.createResource(resource));
    }

    @GetMapping("/reservations")
    public ApiResponse<?> getAllReservations() {
        return ApiResponse.success(reservationRepository.findAll());
    }
}
