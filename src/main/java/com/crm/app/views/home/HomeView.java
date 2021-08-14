package com.crm.app.views.home;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import java.util.HashSet;
import java.util.Set;

@CssImport(value = "./themes/vaadin-crm/views/my-text-field.css", themeFor = "vaadin-text-field")
@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private HorizontalLayout header;
    private SplitLayout mainLayout;
    private VerticalLayout formLayout;
    private HorizontalLayout operationLayout;
    private TextField tableName;
    private String newTableName;
    private Set<String> newTableColumns = new HashSet<>();

    public HomeView() {
        addClassName("home-view");
        header = new HorizontalLayout();
        header.getStyle().set("border-bottom", "1px solid #EEEEEE").set("padding-bottom", "10px");
        header.setMargin(true);
        header.add(new H4("SQLite schema tables"));
        mainLayout = new SplitLayout();
        mainLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        mainLayout.setSplitterPosition(25);
        showTables();
        createTableForm();
        add(header, mainLayout);
    }

    private void showTables() {
        VerticalLayout listLayout = new VerticalLayout(new Label("Tables"));
        ListBox<String> listBox;
        listBox = new ListBox<>();
        listBox.setItems(ApiClient.getAllTables());
        listBox.setValue(ApiClient.getAllTables()[0]);
        listBox.addValueChangeListener(e -> {
            tableName.setValue(e.getValue());
            operationLayout.setVisible(true);
        });
        listLayout.add(listBox);
        mainLayout.addToPrimary(listLayout);
    }

    private void createTableForm() {
        formLayout = new VerticalLayout(new Label("Create new table"));
        operationLayout = new HorizontalLayout();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        tableName = new TextField();
        TextField columnName = new TextField();

        tableName.setLabel("Table name");
        tableName.setRequired(true);
        tableName.setWidth("200px");
        tableName.addKeyDownListener(v -> {
            if (tableName.hasClassName("error")) tableName.removeClassName("error");
        });
        columnName.setWidth("160px");
        columnName.setLabel("Column name");
        columnName.setRequired(true);
        Button save = new Button("Save", VaadinIcon.FILE_ADD.create());
        Button cancel = new Button("Cancel", VaadinIcon.CLOSE_CIRCLE.create());
        cancel.addClickListener(e -> {
            operationLayout.setVisible(false);
            tableName.clear();
            columnName.clear();
        });
        Button addColumnButton = new Button("", VaadinIcon.PLUS.create());
        addColumnButton.setMaxWidth("120px");
        addColumnButton.addClickListener(e -> {
            addColumn(columnName);
        });
        buttonLayout.add(tableName, columnName, addColumnButton);
        operationLayout.add(cancel,save);
        operationLayout.setVisible(false);
        formLayout.add(buttonLayout,operationLayout);
        mainLayout.addToSecondary(formLayout);
    }

    private void addColumn(TextField columnName){
        if (tableName.getValue().isEmpty()) {
            tableName.setClassName("error");
            return;
        }
        if (columnName.getValue().isEmpty()){
            return;
        }
        if (!newTableColumns.contains(columnName.getValue())){
            TextField newColumn = new TextField();
            newColumn.setValue(columnName.getValue());
            newColumn.setMaxWidth("160px");
            newTableColumns.add(columnName.getValue());
        }
        if (!operationLayout.isVisible()) operationLayout.setVisible(true);
    }
}
