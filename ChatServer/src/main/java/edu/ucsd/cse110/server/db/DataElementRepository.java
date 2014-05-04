package edu.ucsd.cse110.server.db;

import org.springframework.data.repository.CrudRepository;


public interface DataElementRepository extends CrudRepository<DataElement, Integer> {
	public Iterable<DataElement> findByName(String name);
}
