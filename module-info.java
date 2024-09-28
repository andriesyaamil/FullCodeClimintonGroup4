
module CLminton {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	requires java.desktop;

	opens main;
	exports main;
}