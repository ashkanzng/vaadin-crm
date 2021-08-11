package com.crm.app.views.schema;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Schema")
@Route(value = "schema", layout = MainLayout.class)
public class SchemaView extends Div {

    private int c = 0;

    private HorizontalLayout header;
    private HorizontalLayout layout;
    private Div formContainer;
    private Button createButton;
    private TextField input;
    private ListBox<String> listBox;
    private HorizontalLayout container;

    private H3 title = new H3("SQLite Tables");

    public SchemaView() {
        addClassName("schema-view");
        listBox = new ListBox<>();
        formContainer = new Div();
        createButton = new Button("Create new table");
        input = new TextField();
        input.setLabel("Name");


        header = new HorizontalLayout();
        header.getStyle().set("padding", "20px").set("border-bottom", "1px solid #EEEEEE");
        header.setMargin(true);
        header.setSpacing(true);
        header.add(title);


        layout = new HorizontalLayout();
        layout.getStyle().set("padding", "20px").set("border-bottom", "1px solid #EEEEEE");
        layout.setMargin(true);
        layout.setSpacing(true);

        container = new HorizontalLayout();
        container.getStyle().set("padding", "20px").set("border-bottom", "1px solid #EEEEEE");
        container.setMargin(true);
        container.setSpacing(true);


        layout.add(formContainer,listBox);
        layout.setFlexGrow(1,listBox);
        layout.setFlexGrow(0.2,formContainer);
        add(header,layout,container);

        createTable();
        showTables();
    }

    private void createTable() {

        List<String> tablesName = new ArrayList<>();
        createButton.addClickListener(e -> {
            c++;
            tablesName.add("some"+c);
            System.out.println(input.getValue());
            tablesName.forEach(n -> container.add(new Paragraph(n)));
        });

        input.getStyle().set("margin-right","10px");
        input.setMaxWidth("250px");
        input.setWidth("200px");
        formContainer.add(input,createButton);
    }


    private void showTables() {
        listBox.setItems(ApiClient.getAllTables());
        listBox.setValue(ApiClient.getAllTables()[0]);
        listBox.addValueChangeListener(e -> System.out.println(e.getValue()));
    }
}
