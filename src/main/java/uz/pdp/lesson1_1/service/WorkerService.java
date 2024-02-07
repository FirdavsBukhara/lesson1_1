package uz.pdp.lesson1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1_1.entity.Address;
import uz.pdp.lesson1_1.entity.Department;
import uz.pdp.lesson1_1.entity.Worker;
import uz.pdp.lesson1_1.payload.ApiResponse;
import uz.pdp.lesson1_1.payload.WorkerDto;
import uz.pdp.lesson1_1.repository.AddressRepository;
import uz.pdp.lesson1_1.repository.DepartmentRepository;
import uz.pdp.lesson1_1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getWorkersList() {
        List<Worker> workerList = workerRepository.findAll();
        return workerList;
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent())
            return optionalWorker.get();
        return null;
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists) {
            return new ApiResponse("This phoneNumber exsists", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (optionalAddress.isEmpty()) {
            return new ApiResponse("Address not found", false);
        }
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker successfully added", true);
    }
    public ApiResponse editWorker(Integer id,WorkerDto workerDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department does not exsist",false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            Worker worker = optionalWorker.get();
            worker.setName(workerDto.getName());
            worker.setPhoneNumber(workerDto.getPhoneNumber());
            if (optionalAddress.isPresent())
                worker.setAddress(optionalAddress.get());
            else worker.setAddress(null);
            workerRepository.save(worker);
            return new ApiResponse("Worker added",true);
        }else {
            return new ApiResponse("Worker not found",false);
        }
    }
    public ApiResponse deleteWorker(Integer id){
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker deleted",true);
        }catch (Exception exception){
            return new ApiResponse("Exception",false);
        }
    }
}
