package com.jose.exercice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jose.exercice.entities.OutputBox;
import com.jose.exercice.repositories.OutputBoxRepository;

@Service
public class OutputBoxBasic implements OutputBoxService {

	private final OutputBoxRepository outputBoxRepository;
	
	@Autowired
	public OutputBoxBasic(final OutputBoxRepository outputBoxRepository) {
		this.outputBoxRepository = outputBoxRepository;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void saveOutPutBox(final OutputBox outputBox) {
		outputBoxRepository.save(outputBox);		
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public String concatenateAllOutputBoxex() {
		final StringBuffer buffer = new StringBuffer();
		outputBoxRepository.findAll().forEach(box ->{
			buffer.append(box.getOutput());
		});
		
		return buffer.toString();
	}

}
