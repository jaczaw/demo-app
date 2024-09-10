package pl.demo.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.demo.demoapp.model.Company;
import pl.demo.demoapp.model.dto.CompanyDataApi;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = """ 
            select new pl.demo.demoapp.model.dto.CompanyDataApi(
                   c.id,c.name, d.name, t.name, p.name, p.manager.nameManager, p.manager.contactInfo)
            from Company c, Department d, Team t, Project p
            where c.id=d.company.id and d.id=t.department.id and t.id=p.team.id
            """
        )
    List<CompanyDataApi> getCompanyDataQuery();

    @Query(value = """ 
            select new pl.demo.demoapp.model.dto.CompanyDataApi(
                   c.id,c.name, d.name, t.name, p.name, p.manager.nameManager, p.manager.contactInfo) 
            from Company c, Department d, Team t, Project p
            where c.id=d.company.id and d.id=t.department.id and t.id=p.team.id and c.id=:id
            """
    )
    List<CompanyDataApi> getCompanyByIdQuery(Long id);

    boolean existsByName(String name);

}