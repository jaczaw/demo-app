package pl.demo.demoapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.demo.demoapp.exceptions.ResourceAlreadyExistsException;
import pl.demo.demoapp.exceptions.ResourceNotFoundException;
import pl.demo.demoapp.model.Company;
import pl.demo.demoapp.model.dto.CompanyDataApi;
import pl.demo.demoapp.repository.CompanyRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<CompanyDataApi> getAllCompaniesData() {
        return companyRepository.getCompanyDataQuery();
    }

    public List<CompanyDataApi> getCompaniesByIdData(Long id) {
        return companyRepository.getCompanyByIdQuery(id);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company id:%s not found".formatted(id)));
    }

    public Company createCompany(Company company) {
        if(companyRepository.existsByName(company.getName())){
            throw new ResourceAlreadyExistsException("Company name:%s already exist".formatted(company.getName()));
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = getCompanyById(id);
        if(companyRepository.existsByName(updatedCompany.getName())){
            throw new ResourceAlreadyExistsException("Company name:%s already exist".formatted(updatedCompany.getName()));
        }
        existingCompany.setName(updatedCompany.getName());
        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
