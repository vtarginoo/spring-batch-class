package br.com.fiap.handsonspringbatch;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class PersonProcessor implements ItemProcessor<Person,Person> {


    @Override
    public Person process(Person item) throws Exception {
        item.setCreatedDateTime(LocalDateTime.now());

        return item;
    }
}
