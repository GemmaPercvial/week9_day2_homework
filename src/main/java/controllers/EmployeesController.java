package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DBHelper;
import models.Department;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

public class EmployeesController {

    ArrayList<Employee> employees = new ArrayList<>();

    public EmployeesController() {
    }

    public void setupEndPoints(Object employee, Department department){
        get("/employees",(req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Employee> employees = DBHelper.getAll(Employee.class);
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/employees", (req, res) -> {
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int departmentID = Integer.parseInt(req.queryParams("department"));

            Department departments = DBHelper.find(departmentID, Department.class);
            Employee employees = new Employee(firstName, lastName, salary, department);
            DBHelper.save(employee);
            res.redirect("/managers");
            return null;
        });

        get("/employees/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/employees/:id/edit", (req,res) -> {
            Employee employees = DBHelper.find(Integer.parseInt(req.params("id")), Employee.class);
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/employees/edit.vtl");
            model.put("employee", employee);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/employees/:id", (req,res) -> {
            Employee employees = DBHelper.find(Integer.parseInt(req.params("id")), Employee.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int departmentID = Integer.parseInt(req.queryParams("department"));
                Employee updatedEmployee = DBHelper.find(departmentID, Department.class);
            updatedEmployee.setFirstName(firstName);
            updatedEmployee.setLastName(lastName);
            updatedEmployee.setSalary(salary);
            updatedEmployee.setDepartment(department);
                DBHelper.save(employee);
            res.redirect("/managers/"+req.params("id"));
            return null;
        });

        post("/employee/:id/delete", (req,res) -> {
            Employee employees = DBHelper.find(Integer.parseInt(req.params("id")), Employee.class);
            DBHelper.delete(employee);
            res.redirect("/employee");
            return null;
        });
    }
}
