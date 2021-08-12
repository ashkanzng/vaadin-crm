package com.crm.app.views.schema;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        body.getStyle().set("padding", "20px").set("border-bottom", "1px solid #EEEEEE");
        body.setMargin(true);
        body.setSpacing(true);
        body.setVerticalComponentAlignment(FlexComponent.Alignment.END);

        add(header,body);
        showTables();
        createTableForm();
    }

    private void createTableForm() {
        Button createButton = new Button("Add column");
        TextField column = new TextField();
        TextField column1 = new TextField();
        HorizontalLayout row1 = new HorizontalLayout();
        row1.add(tableName);

        VerticalLayout row2 = new VerticalLayout();
        row2.setWidth("50%");
        row2.setSpacing(true);

//        createButton.addClickListener(e -> {
//            TextField column = new TextField();
//            formContainer.add(container,column);
//        });
//        input.getStyle().set("margin-right","10px");
        row2.add(column,column1,createButton);
        body.add(row1,row2);
        body.setFlexGrow(0.5,row1);
        body.setFlexGrow(0.5,row2);
    }


    private void showTables() {
        listBox = new ListBox<>();
        listBox.setItems(ApiClient.getAllTables());
        listBox.setValue(ApiClient.getAllTables()[0]);
        listBox.addValueChangeListener(e -> System.out.println(e.getValue()));
        body.add(listBox);
        body.setFlexGrow(0.5,listBox);
    }
}
