package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.AddBrandDTO;
import com.example.petfoodanalyzer.models.dtos.products.BrandInfoDTO;
import com.example.petfoodanalyzer.models.dtos.products.BrandOverviewDTO;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.repositories.products.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    public Brand findByName(String name) {
        return this.brandRepository.findByName(name);
    }

    public Brand findById(Long id){
        return this.brandRepository.findById(id).get();
    }

    public void addBrand(AddBrandDTO addBrandDTO) {
        Brand brand = this.modelMapper.map(addBrandDTO, Brand.class);
        this.brandRepository.save(brand);
    }

    public List<String> getAllBrandsNamesAsString() {
        return this.brandRepository.findAllBrandNames();
    }

    public BrandInfoDTO getBrandInfoById(Long id) {
        return this.modelMapper.map(findById(id), BrandInfoDTO.class);
    }

    public List<BrandOverviewDTO> findFeaturedBrands() {
        return this.brandRepository.findFeaturedBrands()
                .stream()
                .limit(4)
                .map(b -> this.modelMapper.map(b, BrandOverviewDTO.class))
                .toList();
    }
}
