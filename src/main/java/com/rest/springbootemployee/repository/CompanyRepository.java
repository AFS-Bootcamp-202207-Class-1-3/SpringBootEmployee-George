package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchCompanyException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    private static final List<Employee> OOCL_EMPLOYEES = new ArrayList<>();
    private static final List<Employee> IQAX_EMPLOYEES = new ArrayList<>();

    public CompanyRepository() {
        OOCL_EMPLOYEES.add(new Employee(1,"George1",18,"male",180));
        OOCL_EMPLOYEES.add(new Employee(2,"George2",18,"male",180));
        OOCL_EMPLOYEES.add(new Employee(3,"George3",18,"male",180));
        IQAX_EMPLOYEES.add(new Employee(4,"George4",18,"female",180));
        IQAX_EMPLOYEES.add(new Employee(5,"George5",18,"female",180));
        companies.add(new Company(1,"OOCL",OOCL_EMPLOYEES));
        companies.add(new Company(2,"IQAX",IQAX_EMPLOYEES));
    }

    public List<Company> findAllCompanies() {
        return companies;
    }

    public Company findCompanyById(Integer id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchCompanyException::new);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findCompanyByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Company> addCompany(Company company) {
        companies.add(new Company(generateCompanyId(),
                company.getCompanyName(),company.getEmployees()));
        return companies;
    }

    private Integer generateCompanyId() {
        return companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
    }

    public Company updateCompanyById(Integer id) {
        Company updateCompany = findCompanyById(id);
        updateCompany.updateCompanyName();
        return updateCompany;
    }
}
