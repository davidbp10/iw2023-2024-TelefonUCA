package es.uca.iw.telefonuca.views.welcome;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.telefonuca.views.MainLayout;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.UI;


@PageTitle("Bienvenida")
@Route(value = "welcome", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class WelcomeView extends HorizontalLayout {

    public WelcomeView() {
    }
}
