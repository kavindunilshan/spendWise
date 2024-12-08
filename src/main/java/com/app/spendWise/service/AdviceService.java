package com.app.spendWise.service;

import com.app.spendWise.entity.Advice;
import com.app.spendWise.repository.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Advice> adviceOptional = adviceRepository.findById(id);
        if (adviceOptional.isEmpty()) {
            return null;
        }
        return adviceRepository.save(advice);
    }

    public Advice getAdviceById(Integer id) {
        return adviceRepository.findById(id).orElse(null);
    }

    public List<Advice> getAllAdvices() {
        return adviceRepository.findAll();
    }

    public List<Advice> getUnAnsweredAdvices() {
        return adviceRepository.findByAdviceIsNull();
    }
}
