module com.artistcorner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens com.artistcorner to javafx.fxml;
    exports com.artistcorner;

    opens com.artistcorner.controller.guicontroller.login.summarypanel to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login.summarypanel;

    opens com.artistcorner.controller.guicontroller.login to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.login;

    opens com.artistcorner.controller.applicationcontroller to javafx.fxml;
    exports com.artistcorner.controller.applicationcontroller;

    opens com.artistcorner.controller.guicontroller.getreccomandation to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.getreccomandation;

    opens com.artistcorner.controller.guicontroller.viewartgalleryproposals to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewartgalleryproposals;

    opens com.artistcorner.controller.guicontroller.viewprofile to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewprofile;

    opens com.artistcorner.controller.guicontroller.viewsaleshistory to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.viewsaleshistory;

    opens com.artistcorner.controller.guicontroller.uploadartwork to javafx.fxml;
    exports com.artistcorner.controller.guicontroller.uploadartwork;

}