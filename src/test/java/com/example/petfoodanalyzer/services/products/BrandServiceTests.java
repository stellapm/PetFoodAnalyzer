package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.AddBrandDTO;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.viewModels.products.BrandOverviewViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.BrandViewModel;
import com.example.petfoodanalyzer.repositories.products.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {
    private BrandService testService;
    private Brand first;
    private Brand second;

    @Mock
    private BrandRepository mockRepository;

    @BeforeEach
    public void setup(){
        setupBrands();

        this.testService = new BrandService(mockRepository, new ModelMapper());
    }

    @Test
    public void testFindByName(){
        when(this.mockRepository.findByName("First"))
                .thenReturn(Optional.ofNullable(this.first));

        Brand result = this.testService.findByName("First");

        assertEquals(first, result);
        assertNotEquals(second, result);
    }

    @Test
    public void testFindByNameException(){
        when(this.mockRepository.findByName("First"))
                .thenReturn(Optional.empty());

        Throwable missingName = assertThrows(ObjectNotFoundException.class, () -> this.testService.findByName("First"));
        assertEquals("Object with name First and type Brand not found!", missingName.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testFindById(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(this.first));

        Brand result = this.testService.findById(1L);

        assertEquals(first, result);
        assertNotEquals(second, result);
    }

    @Test
    public void testFindByIdException(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable missingName = assertThrows(ObjectNotFoundException.class, () -> this.testService.findById(1L));
        assertEquals("Object with id 1 and type Brand not found!", missingName.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testAddBrand(){
        AddBrandDTO addBrandDTO = new AddBrandDTO()
                .setName("New")
                .setDescription("Example")
                .setPicUrl("URL");

        testService.addBrand(addBrandDTO);

        verify(mockRepository).save(any());
    }

    @Test
    void testGetAllBrandsNamesAsString(){
        when(this.mockRepository.findAllBrandNames())
                .thenReturn(List.of("First", "Second"));

        List<String> result = this.testService.getAllBrandsNamesAsString();

        assertEquals(2, result.size(), "Invalid result list size.");
        assertNotEquals(1, result.size());
        assertTrue(result.contains("First"));
        assertTrue(result.contains("Second"));
        assertFalse(result.contains("Invalid"));
    }

    @Test
    void testGetBrandInfoById(){
        BrandViewModel expected = new BrandViewModel()
                .setName("First")
                .setDescription("Example")
                .setPicUrl("FirstURL");

        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(first));

        BrandViewModel result = this.testService.getBrandInfoById(1L);

        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getPicUrl(), result.getPicUrl());
    }

    @Test
    void testFindFeaturedBrands(){
        when(this.mockRepository.findFeaturedBrands()).thenReturn(List.of(first, second));

        List<BrandOverviewViewModel> result = this.testService.findFeaturedBrands();

        assertEquals(2, result.size());
        assertEquals("FirstURL", result.get(0).getPicUrl());
        assertEquals("SecondURL", result.get(1).getPicUrl());
    }

    @Test
    public void testFindAllBrands(){
        when(this.mockRepository.findAll()).thenReturn(List.of(first, second));

        List<BrandOverviewViewModel> result = this.testService.getAllBrandsOverviewInfo();

        assertEquals(2, result.size());
        assertEquals("FirstURL", result.get(0).getPicUrl());
        assertEquals("SecondURL", result.get(1).getPicUrl());
    }

    private void setupBrands() {
        first = new Brand()
                .setName("First")
                .setDescription("Example")
                .setPicUrl("FirstURL")
                .setProducts(new HashSet<>());

        second = new Brand()
                .setName("Second")
                .setDescription("Example")
                .setPicUrl("SecondURL")
                .setProducts(new HashSet<>());
    }
}
