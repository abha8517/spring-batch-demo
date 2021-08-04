package org.learning.springbatchdemo.repository;

import org.learning.springbatchdemo.entity.MaleOscarWinners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaleOscarWinnersRepository extends JpaRepository<MaleOscarWinners, Long> {
}