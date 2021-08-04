package org.learning.springbatchdemo.batch;

import org.learning.springbatchdemo.entity.MaleOscarWinners;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<MaleOscarWinners, MaleOscarWinners> {
    @Override
    public MaleOscarWinners process(MaleOscarWinners item) throws Exception {
        MaleOscarWinners maleOscarWinners = item;
        return maleOscarWinners;
    }
}
