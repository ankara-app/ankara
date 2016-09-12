package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.Action;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.Topics;
import io.ankara.domain.*;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.main.view.cost.invoice.ItemsTable;
import io.ankara.ui.vaadin.main.view.cost.invoice.ItemsView;
import io.ankara.ui.vaadin.util.TextFieldUtils;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

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

@SpringComponent
public abstract class CostEditView extends VerticalLayout implements View {

    protected TextField code;

    protected ComboBox currency;

    protected ComboBox customer;

    protected TextField customerName;

    protected TextArea subject;

    @Inject
    protected ItemsView itemsView;

    @Inject
    protected ItemsTable itemsTable;

    protected TextField discountPercentage;

    protected TextArea notes;

    protected TextArea terms;

    protected Label amountSubtotal, amountDiscounted, amountDue;

    protected Map<Tax, Label> taxLabels = new HashMap<>();

    @Inject
    protected CompanyService companyService;

    @Inject
    protected CustomerService customerService;

    @Inject
    protected TaxService taxService;

    @Inject
    protected EventBus.UIEventBus eventBus;

    private BeanFieldGroup fieldGroup;

    private FormLayout summaryLayout, customerLayout;

    private Cost cost;

    /**
     * Edit cost instance with the company already set
     *
     * @param cost
     */
    public void editCost(Cost cost) {
        if (cost.getCompany() == null)
            throw new IllegalArgumentException("Cost must have company attibute already set");

        this.cost = cost;

        loadCustomerSelector();

        itemsView.setCost(cost);
        rebuildTaxLabels();

        fieldGroup = BeanFieldGroup.bindFieldsUnbuffered(cost, this);

        rebuildTaxSelector();
        calculateSummaries(null);
    }

    private void loadCustomerSelector() {
        customer.removeAllItems();
        customer.addItems(customerService.getCustomers(cost.getCompany()));
    }

    @EventBusListenerTopic(topic = Topics.TOPIC_COST_CALCULATE_SUMMARIES)
    @EventBusListenerMethod
    private void calculateSummaries(Integer rowIndex) {

        loadCost();

        amountSubtotal.setValue(createMoneyCaption(cost.getSubtotal().setScale(2, RoundingMode.HALF_DOWN), cost.getCurrency()));
        amountDiscounted.setValue(createMoneyCaption(cost.getDiscount().setScale(2, RoundingMode.HALF_DOWN), cost.getCurrency()));

        for (Tax tax : taxLabels.keySet()) {
            taxLabels.get(tax).setValue(createMoneyCaption(cost.calculateTax(tax).setScale(2, RoundingMode.HALF_DOWN), cost.getCurrency()));
        }

        amountDue.setValue(createMoneyCaption(cost.getAmountDue().setScale(2, RoundingMode.HALF_DOWN), cost.getCurrency()));
    }

    private String createMoneyCaption(BigDecimal money, String currency) {
        return money.toString() + " " + currency;
    }

    @PostConstruct
    private void build() {
//        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(false, false, true, false));

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setMargin(true);

        formLayout.addComponent(createBasicsLayout());
        formLayout.addComponent(createSubjectForm());

        formLayout.addComponent(itemsView);
//        formLayout.setExpandRatio(itemsView, 1);

        summaryLayout = createSummaryLayout();
        formLayout.addComponent(summaryLayout);
        formLayout.setComponentAlignment(summaryLayout, Alignment.MIDDLE_RIGHT);

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
        save.addClickListener((Button.ClickListener) event -> {
            if (fieldGroup.isValid() && itemsTable.ensureItemsValidity()) {
                loadCost();
                doSave(cost);
            } else
                Notification.show("Enter required details correctly", "Items which are not required should be removed", Notification.Type.WARNING_MESSAGE);
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        footer.addComponents(save);

        return footer;
    }

    public void loadCost() {
        cost.setItems(itemsTable.getItems());
    }

    private FormLayout createSummaryLayout() {

        amountSubtotal = new Label();
        amountSubtotal.addStyleName("text-right");
        amountSubtotal.setCaption("Subtotal");

        amountDiscounted = new Label();
        amountDiscounted.addStyleName("text-right");
        amountDiscounted.setCaption("Discount");

        amountDue = new Label();
        amountDue.addStyleName("text-right");
        amountDue.setCaption("Amount Due");

        FormLayout summaryForm = new FormLayout(amountSubtotal, amountDiscounted, amountDue);
        summaryForm.setWidth("400px");
        summaryForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        return summaryForm;
    }

    private HorizontalLayout createBasicsLayout() {
        HorizontalLayout basicsLayout = new HorizontalLayout();
        basicsLayout.setSpacing(true);
        basicsLayout.setWidth("100%");
        basicsLayout.addComponent(createCostDetailsForm());
        basicsLayout.addComponent(createAssociatesForm());

        return basicsLayout;
    }

    private Collection<String> getCurrencies() {
        return Arrays.asList("TZS", "USD", "EUR");
    }

    protected Layout createSubjectForm() {
        subject = new TextArea("Subject");
        subject.setInputPrompt("Enter subject ...");
        subject.setRows(2);
        subject.setWidth("100%");
        subject.setNullRepresentation("");
        subject.setValidationVisible(false);

        VerticalLayout subjectLayout = new VerticalLayout(subject);
        subjectLayout.setWidth("100%");
        subjectLayout.setMargin(new MarginInfo(false, true, true, true));
        return subjectLayout;
    }

    protected FormLayout createAssociatesForm() {

        customer = new ComboBox();
        customer.setInputPrompt("Select customer ...");
        customer.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        customer.setWidth("100%");
        customer.setNullSelectionAllowed(false);
        customer.setValidationVisible(false);

        Button addCustomerButton = new Button();
        addCustomerButton.setDescription("Click to add new customer");
        addCustomerButton.setWidth("30px");
        addCustomerButton.setIcon(FontAwesome.PLUS_CIRCLE);
        addCustomerButton.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        addCustomerButton.addStyleName(AnkaraTheme.BUTTON_SMALL);
        addCustomerButton.addClickListener((Button.ClickListener) event -> {
            customerName.setVisible(true);
            customerName.focus();
        });

        customerName = new TextField();
        customerName.setInputPrompt("Enter new customer name ...");
        customerName.setNullRepresentation("");
        customerName.setVisible(false);

        //when user loses focus hide the field
        customerName.addBlurListener((FieldEvents.BlurListener) event -> customerName.setVisible(false));
        TextFieldUtils.handleEnter(customerName, (Action.Listener) (sender, target) -> {
            String name = customerName.getValue();
            createSelectCustomer(name);
            customerName.setVisible(false);
            customerName.setValue(null);
        });


        discountPercentage = new TextField("Discount");
        discountPercentage.setInputPrompt("Specify discount percentage (Optional) ...");
        discountPercentage.setWidth("100%");
        discountPercentage.setNullRepresentation("");
        discountPercentage.addValueChangeListener((Property.ValueChangeListener) event -> {
            amountDiscounted.setCaption("Discount (" + cost.getDiscountPercentage() + "%)");
            calculateSummaries(null);
        });

        HorizontalLayout customerSelectLayout = new HorizontalLayout(customer, addCustomerButton);
        customerSelectLayout.setCaption("Customer");
        customerSelectLayout.setExpandRatio(customer, 1);
        customerSelectLayout.setWidth("100%");
        customerLayout = new FormLayout(customerSelectLayout, customerName, discountPercentage);
        customerLayout.setWidth("100%");
        customerLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        return customerLayout;
    }

    private void createSelectCustomer(String name) {
        Customer selectedCustomer = customerService.getCustomer(name, cost.getCompany());
        if (selectedCustomer == null) {
            selectedCustomer = customerService.create(name, cost.getCompany());
        }
        loadCustomerSelector();
        customer.setValue(selectedCustomer);
    }


    private void rebuildTaxSelector() {

        for (int index = 3; index < customerLayout.getComponentCount(); index++) {
            customerLayout.removeComponent(customerLayout.getComponent(index));
        }

        Collection<Tax> companyTaxes = taxService.getTaxes(cost.getCompany());

        companyTaxes.forEach(tax -> {
            CheckBox taxSelector = new CheckBox(tax.toString());
            //,ark the selector if the cost has tax withinn already
            taxSelector.setValue(cost.getTaxes().contains(tax));
            taxSelector.addValueChangeListener((Property.ValueChangeListener) event -> {

                itemsTable.setIgnoreSummaryCalculateRequest(true);
                CheckBox source = (CheckBox) event.getProperty();
                if (source.getValue())
                    batchSelect(tax);
                else batchUnSelect(tax);

                calculateSummaries(null);
                itemsTable.setIgnoreSummaryCalculateRequest(false);
            });

            customerLayout.addComponent(taxSelector);
        });

    }

    private void batchUnSelect(Tax tax) {

        itemsTable.getItemsFieldGroup().values().forEach(itemBeanFieldGroup -> {
            Property taxProperty = itemBeanFieldGroup.getItemDataSource().getItemProperty("taxes");
            Collection<AppliedTax> value = (Collection) taxProperty.getValue();

            List<AppliedTax> appliedTaxes = value.stream().filter(appliedTax -> {
                return !appliedTax.getTax().equals(tax);
            }).collect(Collectors.toList());

            taxProperty.setValue(appliedTaxes);
        });
    }

    private void batchSelect(Tax tax) {

        itemsTable.getItemsFieldGroup().values().forEach(itemBeanFieldGroup -> {
            Property taxProperty = itemBeanFieldGroup.getItemDataSource().getItemProperty("taxes");
            Collection<AppliedTax> value = (Collection) taxProperty.getValue();

            if (value.stream().noneMatch(appliedTax -> appliedTax.getTax().equals(tax))) {
                List<AppliedTax> appliedTaxes = new LinkedList((Collection) taxProperty.getValue());
                appliedTaxes.add(new AppliedTax(tax));
                taxProperty.setValue(appliedTaxes);
            }

        });

    }

    private void rebuildTaxLabels() {
        loadCost();

        taxLabels.values().forEach(label -> summaryLayout.removeComponent(label));

        Collection<Tax> companyTaxes = taxService.getTaxes(cost.getCompany());

        companyTaxes.forEach(tax -> {
            Label taxLabel = new Label();
            taxLabel.addStyleName("text-right");
            taxLabel.setCaption(createTaxCaption(tax));

            taxLabels.put(tax, taxLabel);

            summaryLayout.addComponent(taxLabel, 2);
        });
    }

    public static String createTaxCaption(Tax tax) {
        return tax.getName() + " (" + tax.getPercentage() + "%)";
    }

    protected FormLayout createCostDetailsForm() {
        code = new TextField("Reference");
        code.setInputPrompt("Enter reference ...");
        code.setWidth("100%");
        code.setNullRepresentation("");

        currency = new ComboBox("Currency", getCurrencies());
        currency.setInputPrompt("Specify currency ...");
        currency.setWidth("100%");
        currency.setNullSelectionAllowed(false);
        currency.addValueChangeListener((Property.ValueChangeListener) event -> {
            calculateSummaries(null);
        });

        FormLayout basicsForm = new FormLayout(code, currency);
        basicsForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        basicsForm.setWidth("100%");
        return basicsForm;
    }

    protected HorizontalLayout createTermsForm() {
        notes = new TextArea("Notes");
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setInputPrompt("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");
        notes.setNullRepresentation("");

        terms = new TextArea("Terms");
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

    public TextField getCode() {
        return code;
    }

    public ComboBox getCurrency() {
        return currency;
    }

    public TextArea getSubject() {
        return subject;
    }

    public ComboBox getCustomer() {
        return customer;
    }

    public ItemsView getItemsView() {
        return itemsView;
    }

    public ItemsTable getItemsTable() {
        return itemsTable;
    }

    public TextField getDiscountPercentage() {
        return discountPercentage;
    }

    public TextArea getNotes() {
        return notes;
    }

    public TextArea getTerms() {
        return terms;
    }

    public Label getAmountDiscounted() {
        return amountDiscounted;
    }

    public Label getAmountDue() {
        return amountDue;
    }

    public BeanFieldGroup getFieldGroup() {
        return fieldGroup;
    }

    @PostConstruct
    protected void init() {
        eventBus.subscribe(this);
    }

    @PreDestroy
    protected void destroy() {
        eventBus.unsubscribe(this);
    }

    protected abstract void doSave(Cost cost);
}
