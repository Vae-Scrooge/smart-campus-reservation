package com.smartcampus.service;

import com.smartcampus.model.Resource;
import com.smartcampus.repository.ResourceRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY = "resources:all";

    public ResourceService(ResourceRepository resourceRepository,
                           RedisTemplate<String, Object> redisTemplate) {
        this.resourceRepository = resourceRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Resource> getAllResources() {
        List<Resource> cached = (List<Resource>) redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return cached;
        }

        List<Resource> resources = resourceRepository.findAll();
        redisTemplate.opsForValue().set(CACHE_KEY, resources, 5, TimeUnit.MINUTES);
        return resources;
    }

    public List<Resource> getResourcesByType(String type) {
        return resourceRepository.findByType(type);
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("资源不存在"));
    }

    public Resource createResource(Resource resource) {
        Resource saved = resourceRepository.save(resource);
        redisTemplate.delete(CACHE_KEY);
        return saved;
    }

    public Resource updateResource(Long id, Resource updated) {
        Resource resource = getResourceById(id);
        resource.setName(updated.getName());
        resource.setType(updated.getType());
        resource.setLocation(updated.getLocation());
        resource.setCapacity(updated.getCapacity());
        resource.setDescription(updated.getDescription());
        resource.setStatus(updated.getStatus());
        resource.setOpenTime(updated.getOpenTime());
        resource.setCloseTime(updated.getCloseTime());
        Resource saved = resourceRepository.save(resource);
        redisTemplate.delete(CACHE_KEY);
        return saved;
    }
}
