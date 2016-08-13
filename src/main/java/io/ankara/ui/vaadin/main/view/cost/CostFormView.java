package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Customer;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import io.ankara.ui.vaadin.main.view.cost.invoice.ItemGrid;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/16 8:28 PM
 */
public abstract class CostFormView extends VerticalLayout implements View {
    private TextField code;

    private ComboBox currency;

    private ComboBox company;

    private ComboBox customer;

    private TextArea subject;

    @Inject
    private ItemGrid items;

    private TextField tax;

    private TextField discount;

    private TextArea notes;

    private TextArea terms;

    private Label amountDiscounted, amountDue;

    @Inject
    private CompanyService companyService;

    @Inject
    private CustomerService customerService;

    @PostConstruct
    private void build() {
        setSizeFull();

        addComponent(createBasicsLayout());
        addComponent(createSubjectForm());

        addComponent(items);
        setExpandRatio(items,1);

        Component summary = createSummaryLayout();
        addComponent(summary);
        setComponentAlignment(summary, Alignment.MIDDLE_RIGHT);

        addComponent(createTermsForm());
    }


    private Component createSummaryLayout() {
        tax = new TextField();
        tax.setInputPrompt("Specify tax (Optional) ...");
        tax.setWidth("200px");

        discount = new TextField();
        discount.setInputPrompt("Specify discount (Optional) ...");
        discount.setWidth("200px");

        FormLayout manipulationForm = new FormLayout(tax, discount);
        manipulationForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        amountDiscounted = new Label();
        amountDiscounted.setCaption("Discount");

        amountDue = new Label();
        amountDue.setCaption("Amount Due");

        FormLayout summaryForm = new FormLayout(amountDiscounted, amountDue);
        summaryForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        HorizontalLayout summaryLay = new HorizontalLayout(manipulationForm, summaryForm);
        return summaryLay;
    }

    private Component createBasicsLayout() {
        HorizontalLayout basicsLayout = new HorizontalLayout();
        basicsLayout.setSpacing(true);
        basicsLayout.setWidth("100%");
        basicsLayout.addComponent(createCostDetailsForm());
        basicsLayout.addComponent(createAssociatesForm());

        return basicsLayout;
    }

    protected Container getCustomerContainer() {
        return new BeanItemContainer<Customer>(Customer.class, customerService.getCurrentUserCustomers());
    }

    private Container getCompanyContainer() {
        return new BeanItemContainer<Company>(Company.class, companyService.getCurrentUserCompanies());
    }

    private Collection<String> getCurrencies() {
        return Collections.emptyList();
    }

    protected Component createSubjectForm() {
        subject = new TextArea();
        subject.setInputPrompt("Enter subject ...");
        subject.setRows(3);
        subject.setWidth("100%");

        FormLayout subjectLayout = new FormLayout(subject);
        subjectLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subjectLayout.setWidth("100%");
        return subjectLayout;
    }

    protected Component createAssociatesForm() {
        company = new ComboBox(null, getCompanyContainer());
        company.setInputPrompt("Select company ...");
        company.setWidth("100%");
        company.setNullSelectionAllowed(false);

        customer = new ComboBox(null, getCustomerContainer());
        customer.setInputPrompt("Select customer ...");
        customer.setWidth("100%");
        customer.setNullSelectionAllowed(false);

        FormLayout associateForm = new FormLayout(company, customer);
        associateForm.setWidth("100%");
        associateForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        return associateForm;
    }

    protected Component createCostDetailsForm() {
        code = new TextField();
        code.setInputPrompt("Enter reference ...");
        code.setWidth("100%");

        currency = new ComboBox(null, getCurrencies());
        currency.setInputPrompt("Specify currency ...");
        currency.setWidth("100%");
        currency.setNullSelectionAllowed(false);

        FormLayout basicsForm = new FormLayout(code, currency);
        basicsForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        basicsForm.setWidth("100%");
        return basicsForm;
    }

    protected Component createTermsForm() {
        notes = new TextArea();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setInputPrompt("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");

        terms = new TextArea();
        terms.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        terms.setInputPrompt("Specify terms ...");
        terms.setRows(6);
        terms.setWidth("100%");

        HorizontalLayout termsLayout = new HorizontalLayout(notes,terms);
        termsLayout.setWidth("100%");
        termsLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        return termsLayout;
    }

}
