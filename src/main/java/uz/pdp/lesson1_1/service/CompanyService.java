package uz.pdp.lesson1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1_1.entity.Address;
import uz.pdp.lesson1_1.entity.Company;
import uz.pdp.lesson1_1.payload.ApiResponse;
import uz.pdp.lesson1_1.payload.CompanyDto;
import uz.pdp.lesson1_1.repository.AddressRepository;
import uz.pdp.lesson1_1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    public List<Company> getCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get();
        }
        return null;
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists) {
            return new ApiResponse("Such named company exsist", false);
        }
        Company company = new Company();
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Company added", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (exists) {
            return new ApiResponse("Such named company exsist", false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("No company with this id found", false);
        }
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isPresent()) {
            company.setAddress(optionalAddress.get());
        } else {
            company.setAddress(null);
        }
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company o'chirildi", true);
        } catch (Exception exception) {
            return new ApiResponse("Something wrong", false);
        }
    }
}
