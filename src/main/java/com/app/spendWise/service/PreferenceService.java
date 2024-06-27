package com.app.spendWise.service;

import com.app.spendWise.entity.Preferences;
import com.app.spendWise.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceService {
    @Autowired
    private PreferenceRepository preferenceRepository;

    public void savePreferences(Preferences preferences) {
        preferenceRepository.save(preferences);
    }

    public Preferences getPreferences(String userId) {
        return preferenceRepository.findById(userId).orElse(null);
    }

    public void deletePreferences(String userId) {
        preferenceRepository.deleteById(userId);
    }

    public void updatePreferences(Preferences preferences) {
        preferenceRepository.save(preferences);
    }
}
