package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.LocationDto;
import com.miromax.cinema.dtos.LocationPostDto;
import com.miromax.cinema.services.HallService;
import com.miromax.cinema.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/locations")
public class LocationController {
    private final LocationService locationService;
    private final HallService hallService;

    public LocationController(LocationService locationService, HallService hallService) {
        this.locationService = locationService;
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findLocationById(id));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<LocationDto>> getAllLocationsFromCity(@RequestParam Long id) {
        return ResponseEntity.ok(locationService.findLocationsByCityId(id));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<LocationDto>> getLocationsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(locationService.findLocationsSortedBy(sortBy, sortDirection));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LocationDto>> searchLocations(@RequestParam String value) {
        return ResponseEntity.ok(locationService.searchLocations(value));
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationPostDto locationPostDto) {
        LocationDto createdLocation = locationService.createLocation(locationPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLocation.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable Long id,
            @RequestBody LocationPostDto locationPostDto) {
        LocationDto locationDto = locationService.updateLocation(id, locationPostDto);
        return ResponseEntity.ok(locationDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocationDto> patchLocation(
            @PathVariable Long id,
            @RequestBody LocationPostDto locationPostDto) {
        LocationDto locationDto = locationService.patchLocation(id, locationPostDto);
        return ResponseEntity.ok(locationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/halls")
    public ResponseEntity<List<HallDto>> getAllHallsFromLocation(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.findHallsByLocationId(id));
    }
}
