package ru.spbau.shavkunov.services;

import org.springframework.data.repository.CrudRepository;
import ru.spbau.shavkunov.primitives.Statistics;

/**
 * Class allows to save statistics to internal database.
 */
public interface DataRepository extends CrudRepository<Statistics, String> {
}