package com.app.spendWise.repository;

import com.app.spendWise.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preferences, String> {
}
