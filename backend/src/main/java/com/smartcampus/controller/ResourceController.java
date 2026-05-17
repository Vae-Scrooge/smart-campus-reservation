package com.smartcampus.controller;

import com.smartcampus.dto.ApiResponse;
import com.smartcampus.model.Resource;
import com.smartcampus.service.ResourceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ApiResponse<?> getAllResources() {
        return ApiResponse.success(resourceService.getAllResources());
    }

    @GetMapping("/type/{type}")
    public ApiResponse<?> getResourcesByType(@PathVariable String type) {
        return ApiResponse.success(resourceService.getResourcesByType(type));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getResource(@PathVariable Long id) {
        return ApiResponse.success(resourceService.getResourceById(id));
    }
}
