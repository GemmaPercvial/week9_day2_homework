package controllers;

import db.Seeds;

public class mainController {

    public static void main(String[] args) {
        Seeds.seedData();
        ManagersController managersController = new ManagersController();
        EmployeesController employeesController = new EmployeesController();

    }
}
