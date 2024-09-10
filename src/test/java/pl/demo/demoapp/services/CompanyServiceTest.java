package pl.demo.demoapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.demo.demoapp.exceptions.ResourceAlreadyExistsException;
import pl.demo.demoapp.model.Company;
import pl.demo.demoapp.repository.CompanyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompanies() {
        // Given
        Company company1 = new Company();
        company1.setId(1L);
        company1.setName("Company A");

        Company company2 = new Company();
        company2.setId(2L);
        company2.setName("Company B");

        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.findAll()).thenReturn(companies);

        // When
        List<Company> result = companyService.getAllCompanies();

        // Then
        assertEquals(2, result.size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testGetCompanyById() {
        // Given
        Company company = new Company();
        company.setId(1L);
        company.setName("Company A");

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        // When
        Company result = companyService.getCompanyById(1L);

        // Then
        assertEquals("Company A", result.getName());
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCompanyById_NotFound() {
        // Given
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> companyService.getCompanyById(1L));
    }

    @Test
    void testCreateCompany() {
        // Given
        Company company = new Company();
        company.setName("Company A");

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // When
        Company result = companyService.createCompany(company);

        // Then
        assertEquals("Company A", result.getName());
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdateCompany() {
        // Given
        Company existingCompany = new Company();
        existingCompany.setId(1L);
        existingCompany.setName("Old Name");

        Company updatedCompany = new Company();
        updatedCompany.setName("New Name");

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        // When
        Company result = companyService.updateCompany(1L, updatedCompany);

        // Then
        assertEquals("New Name", result.getName());
        verify(companyRepository, times(1)).findById(1L);
        verify(companyRepository, times(1)).save(existingCompany);
    }

    @Test
    void testDeleteCompany() {
        // Given
        doNothing().when(companyRepository).deleteById(anyLong());

        // When
        companyService.deleteCompany(1L);

        // Then
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCreatingCompanyWithExistingName() {
        // given
        Company existingCompany = new Company();
        existingCompany.setId(1L);
        existingCompany.setName("Existing Company");
        when(companyRepository.existsByName(existingCompany.getName())).thenReturn(true);

        // when
        assertThrows(ResourceAlreadyExistsException.class, () -> companyService.createCompany(existingCompany));

        // then
        verify(companyRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCompanyWithExistingName() {
        // given
        Company existingCompany = new Company();
        existingCompany.setId(1L);
        existingCompany.setName("Existing Company");
        Company updatedCompany = new Company();
        updatedCompany.setId(1L);
        updatedCompany.setName("Existing Company");
        when(companyRepository.findById(existingCompany.getId())).thenReturn(Optional.of(existingCompany));
        when(companyRepository.existsByName(updatedCompany.getName())).thenReturn(true);

        // when
        Throwable exception = assertThrows(ResourceAlreadyExistsException.class, () ->
            companyService.updateCompany(existingCompany.getId(), updatedCompany));

        // then
        assertEquals("Company name:Existing Company already exist",exception.getMessage());
    }
}