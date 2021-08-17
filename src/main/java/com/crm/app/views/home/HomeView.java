package com.crm.app.views.home;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
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
import java.util.Map;
import java.util.Set;


@CssImport(value = "./themes/vaadin-crm/views/my-text-field.css", themeFor = "vaadin-text-field")
@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private HorizontalLayout header;
    private SplitLayout mainLayout;
    private VerticalLayout formLayout;
    private VerticalLayout columnLayout;
    private HorizontalLayout operationLayout;
    private VerticalLayout gridLayout;
    private VerticalLayout listBoxLayout;
    private TextField tableName;
    private TextField columnName;
    private ListBox<String> listBox;
    private Set<String> newTableColumns = new HashSet<>();
    private Grid<Map<String, String>> grid = new Grid<>();
    private String[] allTables;

    public HomeView() {
        addClassName("home-view");

        header = new HorizontalLayout();
        header.getStyle().set("border-bottom", "1px solid #EEEEEE").set("padding-bottom", "10px");
        header.setMargin(true);
        header.add(new H4("SQLite schema tables"));

        mainLayout = new SplitLayout();
        mainLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        mainLayout.setSplitterPosition(25);

        gridLayout = new VerticalLayout(new Label("Grid table"));
        gridLayout.setSpacing(true);
        grid.setMaxHeight("350px");
        gridLayout.add(grid);

        listBoxLayout = new VerticalLayout(new Label("Tables"));
        formLayout = new VerticalLayout(new Label("Create/Update table"));

        allTables = ApiClient.getAllTables();
        showTables();
        createTableForm();

        add(header, mainLayout,gridLayout);
    }

    private void showTables() {
        listBox = new ListBox<>();
        listBox.setItems(allTables);
        listBox.addValueChangeListener(e -> {
            clearForm();
            if (e.getValue() == null){
                return;
            }
            tableName.setValue(e.getValue());
            operationLayout.setVisible(true);
            for (String column : ApiClient.getTableSchema(e.getValue())) {
                addColumn(new TextField("",column,""));
            }
            createTableGrid(e.getValue());
        });
        listBoxLayout.add(listBox);
        mainLayout.addToPrimary(listBoxLayout);
    }

    private void createTableForm() {

        columnLayout = new VerticalLayout();
        operationLayout = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        columnLayout.setPadding(false);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        tableName = new TextField("Table name");
        columnName = new TextField("Column name");

        tableName.setRequired(true);
        tableName.setWidth("200px");
        tableName.addKeyDownListener(v -> {
            if (tableName.hasClassName("error")) tableName.removeClassName("error");
        });
        columnName.setWidth("160px");
        columnName.setRequired(true);

        Button save = new Button("Save", VaadinIcon.FILE_ADD.create(), e -> {
            ApiClient.createTable(tableName.getValue(), newTableColumns);
            clearForm();
            allTables = ApiClient.getAllTables();
            listBox.setItems(allTables);
            listBox.setValue(null);
        });
        Button cancel = new Button("Cancel", VaadinIcon.CLOSE_CIRCLE.create(),e -> {
            clearForm();
            listBox.setValue(null);
        });
        Button addColumnButton = new Button("", VaadinIcon.PLUS.create());
        addColumnButton.setMaxWidth("120px");
        addColumnButton.addClickListener(e -> {
            addColumn(columnName);
        });
        buttonLayout.add(tableName, columnName, addColumnButton);
        operationLayout.add(cancel,save);
        operationLayout.setVisible(false);
        formLayout.add(buttonLayout,columnLayout,operationLayout);
        mainLayout.addToSecondary(formLayout);
    }

    private void addColumn(TextField columnName){
        if (tableName.getValue().isEmpty()) {
            tableName.setClassName("error");
            return;
        }
        if (columnName.getValue().isEmpty()){
            columnName.setClassName("error");
            return;
        }
        if (!newTableColumns.contains(columnName.getValue())){
            TextField newColumn = new TextField();
            newColumn.setValue(columnName.getValue());
            newColumn.setReadOnly(true);
            newColumn.setMaxWidth("200px");
            newTableColumns.add(columnName.getValue());
            columnLayout.add(newColumn);
        }
        if (!operationLayout.isVisible()) operationLayout.setVisible(true);
    }

    private void createTableGrid(String tableName){
        //grid.removeAllColumns();
        for (String column : ApiClient.getTableSchema(tableName)) {
            grid.addColumn(myhash -> myhash.get(column)).setHeader(column).setSortable(true);
        }
//        grid.addColumn(myhash -> myhash.get("id")).setHeader("Id").setSortable(true);
        grid.addSelectionListener(e->{
            e.getFirstSelectedItem().ifPresent(rowData -> System.out.println(rowData));
        });
        grid.setItems(ApiClient.getTableData(tableName));
    }

    private void clearForm(){
        operationLayout.setVisible(false);
        tableName.clear();
        columnName.clear();
        columnLayout.removeAll();
        newTableColumns.clear();
        grid.removeAllColumns();
    }
}
