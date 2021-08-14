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

import java.util.List;

@CssImport(value = "./themes/vaadin-crm/views/text-field.css",themeFor = "vaadin-text-field")
@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private HorizontalLayout header;
    private SplitLayout mainLayout;
    private TextField tableName;
    private String newTable;
    private List<String> newTableColumns;


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
        listBox.addValueChangeListener(e -> tableName.setValue(e.getValue()));
        listLayout.add(listBox);
        mainLayout.addToPrimary(listLayout);
    }

    private void createTableForm() {
        VerticalLayout formLayout = new VerticalLayout(new Label("Create new table"));

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        tableName = new TextField();
        TextField columnName = new TextField();

        tableName.setLabel("Table name");
        columnName.setLabel("Column name");
        tableName.setRequired(true);
        tableName.setWidth("200px");
        tableName.addKeyDownListener(v -> {
            if (tableName.hasClassName("error")) tableName.removeClassName("error");
        });
        columnName.setWidth("160px");

        Button addColumnButton = new Button("Add column",VaadinIcon.PLUS.create());
        addColumnButton.setMaxWidth("130px");
        addColumnButton.addClickListener(e -> {
            if (tableName.getValue().isEmpty()){
                tableName.setClassName("error");
                return;
            }
            TextField c = new TextField();
            c.setValue(columnName.getValue());
            c.setMaxWidth("160px");
            formLayout.add(c);
        });
        buttonLayout.add(tableName,columnName,addColumnButton);
        formLayout.add(buttonLayout);
        mainLayout.addToSecondary(formLayout);
    }

}
