package io.github.dmatuteb.TravelAgency.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.dmatuteb.TravelAgency.entity.Employee;
import io.github.dmatuteb.TravelAgency.exception.EmployeeNotFoundException;
import io.github.dmatuteb.TravelAgency.repository.EmployeeRepository;
import io.github.dmatuteb.TravelAgency.resthateoas.EmployeeModelAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        List<EntityModel<Employee>> employees = this.employeeRepository.findAll()
                .stream()
                .map(this.employeeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> getEmployee(@PathVariable Long id) {
        return this.employeeRepository.findById(id).map(this.employeeModelAssembler::toModel)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee) {
        EntityModel<Employee> entityModel = this.employeeModelAssembler.toModel(this.employeeRepository.save(employee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editEmployee(@Valid @RequestBody Employee employee, @PathVariable Long id) {
        Employee updatedEmployee = this.employeeRepository.findById(id).map(mappedEmployee -> {
            mappedEmployee.setName(employee.getName());
            mappedEmployee.setLastname(employee.getLastname());
            mappedEmployee.setAddress(employee.getAddress());
            mappedEmployee.setPhoneNumber(employee.getPhoneNumber());
            mappedEmployee.setEmail(employee.getEmail());
            mappedEmployee.setAmusementPark(employee.getAmusementPark());
            return this.employeeRepository.save(mappedEmployee);
        }).orElseGet(() -> {
            employee.setId(id);
            return this.employeeRepository.save(employee);
        });
        EntityModel<Employee> entityModel = this.employeeModelAssembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        this.employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
