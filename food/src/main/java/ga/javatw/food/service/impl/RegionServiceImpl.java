package ga.javatw.food.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.food.model.Region;
import ga.javatw.food.repository.RegionRepository;
import ga.javatw.food.service.RegionService;

@Service
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

	private RegionRepository regionRepository;

	@Autowired
	public RegionServiceImpl(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	}

	@Override
	public List<Region> findAll() {
		return this.regionRepository.findAll();
	}

}
