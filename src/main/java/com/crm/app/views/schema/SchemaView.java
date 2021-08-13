package com.crm.app.views.schema;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Schema")
@Route(value = "schema", layout = MainLayout.class)
public class SchemaView extends Div {

    private HorizontalLayout header;
    private HorizontalLayout body;
    private TextField tableName;
    private ListBox<String> listBox;
    private H4 title = new H4("SQLite schema tables");

    public SchemaView() {
        addClassName("schema-view");

        tableName = new TextField();
        tableName.setLabel("Table Name");

        header = new HorizontalLayout();
        header.getStyle().set("padding", "20px").set("border-bottom", "1px solid #EEEEEE");
        header.setMargin(true);
        header.setSpacing(true);
        header.add(title);


        body = new HorizontalLayout();
        body.getStyle().set("padding", "10px").set("border-bottom", "1px solid #EEEEEE");
        body.setMargin(true);
        body.setSpacing(true);
        body.setWidth("100%");

        showTables();
        createTableForm();
        add(header,body);

    }

    private void createTableForm() {
        H4 h4 = new H4("Create New Table");
        Button createButton = new Button("Add column");
//        createButton.addClickListener(e -> {
//        });
        FormLayout formLayout = new FormLayout();

        body.add(formLayout);
        body.setFlexGrow(2,formLayout);

    }


    private void showTables() {
        HorizontalLayout listLayout = new HorizontalLayout(new Label("Tables"));
        listBox = new ListBox<>();
        listBox.setItems(ApiClient.getAllTables());
        listBox.setValue(ApiClient.getAllTables()[0]);
        listBox.addValueChangeListener(e -> System.out.println(e.getValue()));
        listLayout.add(listBox);
        body.add(listLayout);
        body.setFlexGrow(1,listLayout);
    }
}
