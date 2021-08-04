package org.learning.springbatchdemo.batch;

import lombok.extern.slf4j.Slf4j;
import org.learning.springbatchdemo.entity.MaleOscarWinners;
import org.learning.springbatchdemo.repository.MaleOscarWinnersRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DBWriter implements ItemWriter<MaleOscarWinners> {

    @Autowired
    private MaleOscarWinnersRepository oscarWinnersRepository;

    @Override
    public void write(List<? extends MaleOscarWinners> items) throws Exception {
        log.info("ActorsInfo :: " + items);
        oscarWinnersRepository.saveAll(items);
    }
}
