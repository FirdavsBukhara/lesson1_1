package uz.pdp.lesson1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1_1.entity.Company;
import uz.pdp.lesson1_1.entity.Department;
import uz.pdp.lesson1_1.payload.ApiResponse;
import uz.pdp.lesson1_1.payload.DepartmentDto;
import uz.pdp.lesson1_1.repository.CompanyRepository;
import uz.pdp.lesson1_1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    public Department getDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent())
            return optionalDepartment.get();
        return new Department();
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByName(departmentDto.getName());
        if (exists)
            return new ApiResponse("Such named department already exsist",false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()){
            return new ApiResponse("Company with such id not found",false);
        }
        Department department=new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department added",true);
    }
    public ApiResponse editDepartment(Integer id,DepartmentDto departmentDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found",false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()){
            return new ApiResponse("Company id not found",false);
        }

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department edited",true);
    }

    public ApiResponse deleteDepartment(Integer id){
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted",true);
        }catch (Exception exception){
            return new ApiResponse("EXCEPTION",false);
        }
    }
}
