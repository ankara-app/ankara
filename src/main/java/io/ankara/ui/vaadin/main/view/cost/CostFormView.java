package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.domain.Company;
import io.ankara.domain.Cost;
import io.ankara.domain.Customer;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import io.ankara.ui.vaadin.main.view.cost.invoice.ItemsView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/16 8:28 PM
 */
//TODO CUSTOMER AND COMPANY DETAILS AT THE TIME OF CREATION SHOULD BE SAVE ON THE COST
//TODO SO THAT IF THE CUSTOMER DETAILS OR COMPANY DETAILS CHANGES THE INVOICE REMAINS AS IT WAS CREATED
//TODO DURING CREATION THE FORM SHOULD ALLOW USER TO SELECT THE CUSTOMER AND COMPANY WHICH WILL INTURN FILL THEIR INFORMATION ON
//TODO THE COST. BUT AFTER A CERTAIN STATE SAY SUBMITTED/SENT TO CUSTOMER STATE OF COST/INVOICE THE FORM SHOULD NO LONGER ALLOW EDITING BY SELCTING CUSTOMER OR COMPANY
//TODO INSTEAD IT SHOULD ONLY ALLOW EDITING THE ACTUAL DETAILS OF THE COST AS THEY WERE OBTAINED FROM THE CUSTOMER AND COMPANY
public abstract class CostFormView extends VerticalLayout implements View {

    protected TextField code;

    protected ComboBox currency;

    protected ComboBox company;

    protected ComboBox customer;

    protected TextArea subject;

    @Inject
    protected ItemsView items;

    protected TextField tax;

    protected TextField discount;

    protected TextArea notes;

    protected TextArea terms;

    protected Label amountDiscounted, amountDue;

    @Inject
    protected CompanyService companyService;

    @Inject
    protected CustomerService customerService;

    private BeanFieldGroup fieldGroup;

    public void editCost(Cost cost) {
        fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(cost, this);
        items.setCost(cost);
    }

    @PostConstruct
    private void build() {
//        setSizeFull();
        setSpacing(true);
        setMargin(true);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setMargin(true);

        formLayout.addComponent(createBasicsLayout());
        formLayout.addComponent(createSubjectForm());

        formLayout.addComponent(items);
//        formLayout.setExpandRatio(items, 1);

        Component summary = createSummaryLayout();
        formLayout.addComponent(summary);
        formLayout.setComponentAlignment(summary, Alignment.MIDDLE_RIGHT);

        formLayout.addComponent(createTermsForm());

        Panel form = new Panel(formLayout);
//        form.setSizeFull();
        addComponent(form);
        setExpandRatio(form, 1);

        Component footer = createFooter();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_RIGHT);
    }

    private HorizontalLayout createFooter() {
        Button save = new Button("Save", FontAwesome.FLOPPY_O);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setWidth("200px");
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

            }
        });

        Button cancel = new Button("Cancel", FontAwesome.STOP_CIRCLE_O);
        cancel.setWidth("200px");

        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        footer.addComponents(save, cancel);

        return footer;
    }

    private HorizontalLayout createSummaryLayout() {
        tax = new TextField();
        tax.setInputPrompt("Specify tax (Optional) ...");
        tax.setWidth("200px");
        tax.setNullRepresentation("");

        discount = new TextField();
        discount.setInputPrompt("Specify discount (Optional) ...");
        discount.setWidth("200px");
        discount.setNullRepresentation("");

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

    private HorizontalLayout createBasicsLayout() {
        HorizontalLayout basicsLayout = new HorizontalLayout();
        basicsLayout.setSpacing(true);
        basicsLayout.setWidth("100%");
        basicsLayout.addComponent(createCostDetailsForm());
        basicsLayout.addComponent(createAssociatesForm());

        return basicsLayout;
    }

    protected Container getCustomerContainer(Company selectedCompany) {
        return new BeanItemContainer<Customer>(Customer.class, customerService.getCustomers(selectedCompany));
    }

    private Container getCompanyContainer() {
        return new BeanItemContainer<Company>(Company.class, companyService.getCurrentUserCompanies());
    }

    private Collection<String> getCurrencies() {
        return Arrays.asList("TZS", "USD", "EUR");
    }

    protected FormLayout createSubjectForm() {
        subject = new TextArea();
        subject.setInputPrompt("Enter subject ...");
        subject.setRows(3);
        subject.setWidth("100%");
        subject.setNullRepresentation("");

        FormLayout subjectLayout = new FormLayout(subject);
        subjectLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        subjectLayout.setWidth("100%");
        return subjectLayout;
    }

    protected FormLayout createAssociatesForm() {
        company = new ComboBox(null, getCompanyContainer());
        company.setInputPrompt("Select company ...");
        company.setWidth("100%");
        company.setNullSelectionAllowed(false);

        company.addValueChangeListener(event -> {
            Company selectedCompany = (Company) event.getProperty().getValue();
            if(selectedCompany == null) return;

            customer.setContainerDataSource(getCustomerContainer(selectedCompany));
            terms.setValue(selectedCompany.getTerms());
            notes.setValue(selectedCompany.getNotes());
            items.getItemsTable().reset();
        });

        customer = new ComboBox();
        customer.setInputPrompt("Select customer ...");
        customer.setWidth("100%");
        customer.setNullSelectionAllowed(false);

        FormLayout associateForm = new FormLayout(company, customer);
        associateForm.setWidth("100%");
        associateForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        return associateForm;
    }

    protected FormLayout createCostDetailsForm() {
        code = new TextField();
        code.setInputPrompt("Enter reference ...");
        code.setWidth("100%");
        code.setNullRepresentation("");

        currency = new ComboBox(null, getCurrencies());
        currency.setInputPrompt("Specify currency ...");
        currency.setWidth("100%");
        currency.setNullSelectionAllowed(false);

        FormLayout basicsForm = new FormLayout(code, currency);
        basicsForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        basicsForm.setWidth("100%");
        return basicsForm;
    }

    protected HorizontalLayout createTermsForm() {
        notes = new TextArea();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setInputPrompt("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");
        notes.setNullRepresentation("");

        terms = new TextArea();
        terms.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        terms.setInputPrompt("Specify terms ...");
        terms.setRows(6);
        terms.setWidth("100%");
        terms.setNullRepresentation("");

        HorizontalLayout termsLayout = new HorizontalLayout(notes, terms);
        termsLayout.setWidth("100%");
        termsLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        return termsLayout;
    }

}
