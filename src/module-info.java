module calouseIF_project {
	opens views;
	opens controllers;
	opens models;
	opens database;
	opens main;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
}