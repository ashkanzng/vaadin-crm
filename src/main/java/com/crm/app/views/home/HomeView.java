package com.crm.app.views.home;

import com.crm.app.api.ApiClient;
import com.crm.app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private HorizontalLayout header;
    private SplitLayout mainLayout;
    private Button addColumnButton;
    private FormLayout form;


    public HomeView() {
        addClassName("home-view");
        header = new HorizontalLayout();
        header.getStyle().set("border-bottom", "1px solid #EEEEEE").set("padding-bottom","10px");
        header.setMargin(true);
        header.add(new H4("SQLite schema tables"));
        mainLayout = new SplitLayout();
        mainLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        mainLayout.setSplitterPosition(25);
        showTables();
        createTableForm();
        add(header,mainLayout);
    }

    private void showTables() {
        VerticalLayout listLayout = new VerticalLayout(new Label("Tables"));
        ListBox<String> listBox;
        listBox = new ListBox<>();
        listBox.setItems(ApiClient.getAllTables());
        listBox.setValue(ApiClient.getAllTables()[0]);
        listBox.addValueChangeListener(e -> System.out.println(e.getValue()));
        listLayout.add(listBox);
        mainLayout.addToPrimary(listLayout);
    }

    private void createTableForm() {
        VerticalLayout formLayout = new VerticalLayout(new Label("Create new table"));
        form = new FormLayout();
        TextField tableName = new TextField();
        TextField column = new TextField();

        tableName.setLabel("Table name");
        column.setPlaceholder("name");
        tableName.setPlaceholder("Table 1");
        addColumnButton = new Button("Add");
        addColumnButton.setMaxWidth("30px");
        addColumnButton.getStyle().set("margin-left","-5px");

        form.add(column,addColumnButton);
        form.setMaxWidth("410px");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("220px", 2));
        formLayout.add(tableName,form);
        mainLayout.addToSecondary(formLayout);
    }
}
