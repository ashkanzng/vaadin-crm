package com.crm.app.views.home;

import com.crm.app.api.ApiClient;
import com.crm.app.component.HeaderComponent;
import com.crm.app.component.LayoutComponent;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@CssImport(value = "./themes/vaadin-crm/views/my-text-field.css", themeFor = "vaadin-text-field")
@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private VerticalLayout columnLayout;
    private HorizontalLayout operationLayout;
    private TextField tableName;
    private TextField columnName;
    private Set<String> newTableColumns;
    private Grid<Map<String, String>> grid;
    private VerticalLayout dataForm;
    private ListBox<String> listBox;
    private String[] allTables;
    private HeaderComponent headerComponent;
    private LayoutComponent layoutComponent;

    public HomeView() {
        addClassName("home-view");
        initializing();
        createTableForm();
        showTables();
        add(headerComponent.getHeader(),layoutComponent.getMainLayout(),layoutComponent.getGridLayout());
    }

    private void initializing(){
        newTableColumns = new HashSet<>();
        allTables = ApiClient.getAllTables();
        headerComponent = new HeaderComponent();
        layoutComponent = new LayoutComponent();
        grid = new Grid<>();
        grid.addItemClickListener(e -> fillDataForm(e.getItem()));
        grid.setMaxHeight("350px");
        layoutComponent.getGridLayout().add(grid);
        dataForm = new VerticalLayout();
        layoutComponent.getSecondLayout().addToSecondary(dataForm);
        tableName = new TextField("Table name");
        columnName = new TextField("Column name");
    }

    public void showTables(){
        listBox = new ListBox<>();
        if (allTables.length > 0){
            listBox.setItems(allTables);
            listBox.setValue(allTables[0]);
            showTableColumnsAndGrid(allTables[0]);
        }
        listBox.addValueChangeListener(e->{
            if (e.getValue() == null) return;
            resetForm();
            showTableColumnsAndGrid(e.getValue());
        });
        layoutComponent.getListBoxLayout().add(listBox);
        layoutComponent.getMainLayout().addToPrimary(layoutComponent.getListBoxLayout());
    }

    private void createTableForm() {

        columnLayout = new VerticalLayout();
        operationLayout = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        columnLayout.setPadding(false);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.END);
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
        });
        Button cancel = new Button("Cancel", VaadinIcon.CLOSE_CIRCLE.create(),e -> {
            clearForm();
        });
        Button addColumnButton = new Button("", VaadinIcon.PLUS.create(),e -> {
            addColumn(columnName);
        });
        addColumnButton.setMaxWidth("120px");
        buttonLayout.add(tableName, columnName, addColumnButton);
        operationLayout.add(cancel,save);
        operationLayout.setVisible(false);
        layoutComponent.getFormLayout().add(buttonLayout,columnLayout,operationLayout);
        layoutComponent.getSecondLayout().addToPrimary(layoutComponent.getFormLayout());
    }

    private void addColumn(TextField columnName){
        if (tableName.getValue().isEmpty()) {
            //tableName.setErrorMessage("fill the name");
            //tableName.setInvalid(true);
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
        List<Map<String, String>> data = ApiClient.getTableData(tableName);
        if (data == null){
            data = List.of(Map.of("id",""));
        }
        String[] headers = ApiClient.getTableSchema(tableName);
        for (String column : headers) {
            grid.addColumn(myhash -> myhash.get(column)).setHeader(column).setSortable(true).setAutoWidth(true);
        }
        //grid.addColumn(myhash -> myhash.get("id")).setHeader("Id").setSortable(true);
        grid.setItems(data);
        createDataForm(headers);
    }

    private void createDataForm(String[] headers){
        dataForm.add(new Label("Insert/Update data"));
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        Button cancel = new Button("Cancel", VaadinIcon.CLOSE_CIRCLE.create(),e -> {
            clearForm();
        });
        Button save = new Button("Save", VaadinIcon.FILE_ADD.create(), e -> {
            clearForm();
        });
        for (String header : headers) {
            TextField field = new TextField(header);
            if (header.equals("id") || header.equals("created_at") || header.equals("update_at")){
                continue;
            }
            field.getElement().setProperty("name",header);
            dataForm.add(field);
        }
        HorizontalLayout operationLayout = new HorizontalLayout(save,cancel);
        dataForm.add(operationLayout);
    }

    private void fillDataForm(Map<String, String> row){
        dataForm.getChildren().forEach(c -> {
            if (c instanceof TextField && row.get(c.getElement().getProperty("name")) != null){
                TextField field = (TextField)c;
                field.setValue(row.get(c.getElement().getProperty("name")));
            }
        });
    }

    private void showTableColumnsAndGrid(String nameOfTable){
        tableName.setValue(nameOfTable);
        operationLayout.setVisible(true);
        for (String column : ApiClient.getTableSchema(nameOfTable)) {
            addColumn(new TextField("",column,""));
        }
        createTableGrid(nameOfTable);
    }

    private void clearForm(){
        resetForm();
        listBox.setValue(null);
    }

    private void resetForm(){
        operationLayout.setVisible(false);
        tableName.clear();
        columnName.clear();
        columnLayout.removeAll();
        newTableColumns.clear();
        grid.removeAllColumns();
        dataForm.removeAll();
    }
}
