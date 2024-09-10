package pl.demo.demoapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDataApi {

    private Long id;
    private String companyName;
    private String departmentName;
    private String teamName;
    private String projectName;
    private String managerName;
    private String managerContact;
}
