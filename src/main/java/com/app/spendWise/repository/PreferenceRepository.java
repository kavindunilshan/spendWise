package com.app.spendWise.repository;

import com.app.spendWise.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preferences, String> {
}
