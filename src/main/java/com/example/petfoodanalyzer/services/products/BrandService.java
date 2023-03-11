package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.AddBrandDTO;
import com.example.petfoodanalyzer.models.viewModels.products.BrandViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.BrandOverviewViewModel;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.repositories.products.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.petfoodanalyzer.constants.Exceptions.ID_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.BRAND;



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
        return this.brandRepository.findByName(name).orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, name, BRAND));
    }

    public Brand findById(Long id){
        return this.brandRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ID_IDENTIFIER, String.valueOf(id), BRAND));
    }

    public void addBrand(AddBrandDTO addBrandDTO) {
        Brand brand = this.modelMapper.map(addBrandDTO, Brand.class);
        this.brandRepository.save(brand);
    }

    public List<String> getAllBrandsNamesAsString() {
        return this.brandRepository.findAllBrandNames();
    }

    public BrandViewModel getBrandInfoById(Long id) {
        return this.modelMapper.map(findById(id), BrandViewModel.class);
    }

    public List<BrandOverviewViewModel> findFeaturedBrands() {
        return this.brandRepository.findFeaturedBrands()
                .stream()
                .limit(4)
                .map(b -> this.modelMapper.map(b, BrandOverviewViewModel.class))
                .toList();
    }
}
