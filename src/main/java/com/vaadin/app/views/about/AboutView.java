package com.vaadin.app.views.about;

import com.vaadin.app.api.ApiClient;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.app.views.MainLayout;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends Div {

    private Div div = new Div();

    public AboutView() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.getStyle().set("border", "1px solid #EEEEEE").set("padding","100px");
        layout.add(new H5("com-1"), new H5("com-2"), new H5("com-3"));
        layout.setWidth("100%");
        div.add(new Text("Text"));
        layout.add(div);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        add(layout);
    }

}
