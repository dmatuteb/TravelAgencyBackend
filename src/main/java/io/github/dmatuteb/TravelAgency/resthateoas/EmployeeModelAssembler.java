package io.github.dmatuteb.TravelAgency.resthateoas;

import io.github.dmatuteb.TravelAgency.controller.EmployeeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import io.github.dmatuteb.TravelAgency.controller.AmusementParkController;
import io.github.dmatuteb.TravelAgency.entity.Employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(
                employee,
                WebMvcLinkBuilder.linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"),
                linkTo(methodOn(AmusementParkController.class).getAmusementPark(employee.getId())).withRel("amusementPark")
        );
    }

}
