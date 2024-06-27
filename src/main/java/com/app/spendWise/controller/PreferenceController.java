package com.app.spendWise.controller;

import com.app.spendWise.entity.Preferences;
import com.app.spendWise.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/preferences")
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;

    @PostMapping("")
    public ResponseEntity<Preferences> savePreferences(@RequestBody Preferences preferences) {
        preferenceService.savePreferences(preferences);
        return new ResponseEntity<>(preferences, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Preferences> getPreferences(@PathVariable String userId) {
        Preferences preferences = preferenceService.getPreferences(userId);
        return new ResponseEntity<>(preferences, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePreferences(@PathVariable String userId) {
        preferenceService.deletePreferences(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<Preferences> updatePreferences(@RequestBody Preferences preferences) {
        preferenceService.updatePreferences(preferences);
        return new ResponseEntity<>(preferences, HttpStatus.OK);
    }
}
