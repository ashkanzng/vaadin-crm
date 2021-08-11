package com.crm.app.views.schema;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Schema")
@Route(value = "schema", layout = MainLayout.class)
public class SchemaView extends Div {

    private HorizontalLayout layout;
    private Div container = new Div();
    private H3 title = new H3("Tables");

    public SchemaView() {
        addClassName("schema-view");

        container.add(title);
        container.setWidth("100%");
        container.getStyle().set("margin","20px");
        add(container);

        layout = new HorizontalLayout();
        layout.getStyle().set("padding","20px").set("border","1px solid #EEEEEE");
        layout.setMargin(true);
        layout.setSpacing(true);

        showTables();
        add(layout);
    }

    void showTables(){
        ListBox<String> listBox = new ListBox<>();
        listBox.setItems(ApiClient.getAllTables());
        for (String table : ApiClient.getAllTables()) {
            listBox.setValue(table);
        }
        listBox.addValueChangeListener(e -> System.out.println(e.getValue()));
        layout.add(listBox);
    }
}
