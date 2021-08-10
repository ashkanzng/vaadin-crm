package com.crm.app.views.schema;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.crm.app.views.MainLayout;

@PageTitle("Schema")
@Route(value = "schema", layout = MainLayout.class)
public class SchemaView extends Div {

    public SchemaView() {
        addClassName("schema-view");
        add(new Text("Schema 2"));
    }

}
