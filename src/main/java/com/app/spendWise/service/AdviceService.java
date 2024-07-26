package com.app.spendWise.service;

import com.app.spendWise.entity.Advice;
import com.app.spendWise.repository.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdviceService {
    @Autowired
    private AdviceRepository adviceRepository;

    public List<Advice> getAdvicesByUserId(String userId) {
        return adviceRepository.findByUserId(userId);
    }

    public Advice createAdvice(Advice advice) {
        return adviceRepository.save(advice);
    }

    public void deleteAdvice(Integer id) {
        adviceRepository.deleteById(id);
    }

    public Advice updateAdvice(Integer id, Advice advice) {
        advice.setId(id);
        return adviceRepository.save(advice);
    }

    public Advice getAdviceById(Integer id) {
        return adviceRepository.findById(id).orElse(null);
    }

    public List<Advice> getAdvice() {
        return adviceRepository.findAll();
    }
}
