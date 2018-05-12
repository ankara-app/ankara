package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.event.Action;
import com.vaadin.event.FieldEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.ankara.Topics;
import io.ankara.domain.AppliedTax;
import io.ankara.domain.Cost;
import io.ankara.domain.Customer;
import io.ankara.domain.Tax;
import io.ankara.service.CompanyService;
import io.ankara.service.CustomerService;
import io.ankara.service.TaxService;
import io.ankara.ui.vaadin.AnkaraTheme;
import io.ankara.ui.vaadin.util.TextFieldUtils;
import io.ankara.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.events.annotation.EventBusListenerTopic;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.math.BigDecimal;
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

public abstract class CostEditView<T extends Cost> extends VerticalLayout implements View {

    protected TextField code;

    protected ComboBox<String> currency;

    protected ComboBox<Customer> customer;

    protected TextField customerName;

    protected TextArea subject;

    private DateField issueDateField;

    @Inject
    protected ItemsView itemsView;

    @Inject
    protected ItemsTable itemsTable;

    protected TextField discountPercentageField;

    protected TextArea notes;

    protected TextArea terms;

    protected Label amountSubtotal, amountDiscounted, amountDue;

    protected Map<Tax, Label> taxLabels = new HashMap<>();
    protected Map<Tax, CheckBox> taxSelectors = new HashMap<>();

    @Inject
    protected CompanyService companyService;

    @Inject
    protected CustomerService customerService;

    @Inject
    protected TaxService taxService;

    @Inject
    protected EventBus.UIEventBus eventBus;

    private BeanValidationBinder<T> costBinder;

    private FormLayout summaryLayout, customerLayout;

    private Cost cost;

    public CostEditView(Class<T> type) {
        costBinder = new BeanValidationBinder<>(type);
    }

    /**
     * Edit cost instance with the company already set
     *
     * @param cost
     */
    public void editCost(T cost) {
        if (cost.getCompany() == null)
            throw new IllegalArgumentException("Cost must have company attibute already set");

        this.cost = cost;

        loadCustomerSelector();

        rebuildTaxLabels();


        rebuildTaxSelector();

        costBinder.setBean(cost);
        itemsView.setCost(cost);

        calculateSummaries(null);
    }

    private void loadCustomerSelector() {
        customer.setItems(customerService.getCustomers(cost.getCompany()));
    }

    @EventBusListenerTopic(topic = Topics.TOPIC_COST_CALCULATE_SUMMARIES)
    @EventBusListenerMethod
    private void calculateSummaries(Integer rowIndex) {

        loadCost();

        amountSubtotal.setValue(NumberUtils.formatMoney(cost.getSubtotal(), cost.getCurrency()));
        amountDiscounted.setValue(NumberUtils.formatMoney(cost.getDiscount(), cost.getCurrency()));
        amountDiscounted.setCaption("Discount (" + cost.getDiscountPercentage() + "%)");

        for (Tax tax : taxLabels.keySet()) {
            taxLabels.get(tax).setValue(NumberUtils.formatMoney(cost.calculateTax(tax), cost.getCurrency()));
        }

        //update tax selector on the view
        taxSelectors.forEach((tax, selector) -> {
            selector.setValue(cost.hasTax(tax));
        });

        amountDue.setValue(NumberUtils.formatMoney(cost.getAmountDue(), cost.getCurrency()));
    }

    @PostConstruct
    private void build() {
        setSizeFull();
        setSpacing(true);
        setMargin(false);

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
        form.setSizeFull();
        addComponent(form);
        setExpandRatio(form, 1);

        Component footer = createFooter();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_RIGHT);

        costBinder.bindInstanceFields(this);
        costBinder.setRequiredConfigurator(null);
    }

    private HorizontalLayout createFooter() {
        Button save = new Button("Save", VaadinIcons.DISC);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setWidth("80%");
        save.addClickListener((Button.ClickListener) event -> {

            if (costBinder.isValid()) {
                loadCost();
                doSave(cost);
            } else Notification.show("Enter required details correctly", Notification.Type.WARNING_MESSAGE);
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        footer.addComponents(save);

        return footer;
    }

    public void loadCost() {
        cost.setItems(itemsTable.getValidItems());
    }

    private FormLayout createSummaryLayout() {

        amountSubtotal = new Label();
        amountSubtotal.addStyleName(AnkaraTheme.TEXT_RIGHT);
        amountSubtotal.setCaption("Subtotal");
        amountSubtotal.setWidth("100%");

        amountDiscounted = new Label();
        amountDiscounted.addStyleName(AnkaraTheme.TEXT_RIGHT);
        amountDiscounted.setCaption("Discount");
        amountDiscounted.setWidth("100%");

        amountDue = new Label();
        amountDue.addStyleName(AnkaraTheme.TEXT_RIGHT);
        amountDue.setCaption("Amount Due");
        amountDue.setWidth("100%");

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
        return Arrays.asList("TZS", "KES", "UGX", "USD", "EUR");
    }

    protected Layout createSubjectForm() {
        subject = new TextArea("Subject");
        subject.setPlaceholder("Enter subject ...");
        subject.setRows(2);
        subject.setWidth("100%");

        VerticalLayout subjectLayout = new VerticalLayout(subject);
        subjectLayout.setWidth("100%");
        subjectLayout.setMargin(new MarginInfo(false, true, true, true));
        return subjectLayout;
    }

    protected FormLayout createAssociatesForm() {


        customerName = new TextField();
        customerName.setPlaceholder("Enter new customer name ...");
        customerName.setWidth("100%");

        Button saveCustomerButton = new Button("Save");
        saveCustomerButton.setDescription("Click to save new customer or press enter");
        saveCustomerButton.setWidth("70px");
        saveCustomerButton.setIcon(FontAwesome.SAVE);
        saveCustomerButton.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        saveCustomerButton.addStyleName(AnkaraTheme.BUTTON_SMALL);
        saveCustomerButton.addClickListener((Button.ClickListener) event -> {
            saveNewCustomer();
        });

        HorizontalLayout customerCreateLayout = new HorizontalLayout(customerName, saveCustomerButton);
        customerCreateLayout.setVisible(false);
        customerCreateLayout.setCaption("Customer");
        customerCreateLayout.setExpandRatio(customerName, 1);
        customerCreateLayout.setWidth("100%");

        //when user loses focus hide the field
        customerName.addBlurListener((FieldEvents.BlurListener) event -> {
            String name = customerName.getValue();
            if (!StringUtils.isEmpty(name))
                saveNewCustomer();

            customerCreateLayout.setVisible(false);
        });
        TextFieldUtils.handleEnter(customerName, (Action.Listener) (sender, target) -> {
            saveNewCustomer();
        });

        customer = new ComboBox<>();
        customer.setPlaceholder("Select customer ...");
        customer.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        customer.setWidth("100%");
        customer.setEmptySelectionAllowed(true);

        Button addCustomerButton = new Button("Add");
        addCustomerButton.setDescription("Click to add new customer");
        addCustomerButton.setWidth("70px");
        addCustomerButton.setIcon(FontAwesome.PLUS_CIRCLE);
        addCustomerButton.addStyleName(AnkaraTheme.BUTTON_BORDERLESS_COLORED);
        addCustomerButton.addStyleName(AnkaraTheme.BUTTON_SMALL);
        addCustomerButton.addClickListener((Button.ClickListener) event -> {
            customerCreateLayout.setVisible(true);
            customerName.focus();
        });

        HorizontalLayout customerSelectLayout = new HorizontalLayout(customer, addCustomerButton);
        customerSelectLayout.setCaption("Customer");
        customerSelectLayout.setExpandRatio(customer, 1);
        customerSelectLayout.setWidth("100%");

        discountPercentageField = new TextField("Discount");
        discountPercentageField.setPlaceholder("Specify discount percentage (Optional) ...");
        discountPercentageField.setWidth("100%");
        costBinder.forField(discountPercentageField).withConverter(new StringToBigDecimalConverter("Discount percentage should be a number")).bind("discountPercentage");
        discountPercentageField.addValueChangeListener(event -> {
            if (event.isUserOriginated())
                calculateSummaries(null);
        });

        customerLayout = new FormLayout(customerSelectLayout, customerCreateLayout, discountPercentageField);
        customerLayout.setWidth("100%");
        customerLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        return customerLayout;
    }

    private void saveNewCustomer() {
        String name = customerName.getValue();
        if (StringUtils.isEmpty(name)) return;

        Customer selectedCustomer = customerService.getCustomer(name, cost.getCompany());
        if (selectedCustomer == null) {
            selectedCustomer = customerService.create(name, cost.getCompany());
        }
        loadCustomerSelector();
        customer.setValue(selectedCustomer);

        customer.focus();
        customerName.setValue(null);
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
            taxSelector.addValueChangeListener(event -> {
                //if event is not originating from user then it may be from the entries tax selector there for ignore
                if (!event.isUserOriginated()) return;

                itemsTable.setIgnoreSummaryCalculateRequest(true);
                CheckBox source = (CheckBox) event.getComponent();
                if (source.getValue())
                    batchSelect(tax);
                else batchUnSelect(tax);

                if (event.isUserOriginated())
                    calculateSummaries(null);
                itemsTable.setIgnoreSummaryCalculateRequest(false);

            });

            customerLayout.addComponent(taxSelector);
            taxSelectors.put(tax,taxSelector);
        });

    }

    private void batchUnSelect(Tax tax) {

        itemsTable.getItemBinders().values().forEach(itemBeanFieldGroup -> {
            Collection<AppliedTax> appliedTaxes = itemBeanFieldGroup.getBean().getTaxes();

            List<AppliedTax> filteredTaxes = appliedTaxes.stream()
                    .filter(appliedTax -> !appliedTax.getTax().equals(tax))
                    .collect(Collectors.toList());

            itemBeanFieldGroup.getBean().setTaxes(filteredTaxes);
        });

        itemsTable.getDataProvider().refreshAll();
    }

    private void batchSelect(Tax tax) {

        itemsTable.getItemBinders().values().forEach(itemBeanFieldGroup -> {
            Collection<AppliedTax> appliedTaxes = itemBeanFieldGroup.getBean().getTaxes();

            if (appliedTaxes.stream().noneMatch(appliedTax -> appliedTax.getTax().equals(tax))) {
                List<AppliedTax> toApplyTaxes = new LinkedList(appliedTaxes);
                toApplyTaxes.add(new AppliedTax(tax));
                itemBeanFieldGroup.getBean().setTaxes(toApplyTaxes);
            }

        });

        itemsTable.getDataProvider().refreshAll();

    }

    private void rebuildTaxLabels() {

        Collection<Tax> companyTaxes = taxService.getTaxes(cost.getCompany());

        companyTaxes.forEach(tax -> {
            Label taxLabel = new Label();
            taxLabel.addStyleName(AnkaraTheme.TEXT_RIGHT);
            taxLabel.setCaption(createTaxCaption(tax));
            taxLabel.setWidth("100%");

            taxLabels.put(tax, taxLabel);

            summaryLayout.addComponent(taxLabel, 2);
        });
    }

    public static String createTaxCaption(Tax tax) {
        return tax.getName() + " (" + tax.getPercentage() + "%)";
    }

    protected FormLayout createCostDetailsForm() {
        code = new TextField("Reference");
        code.setPlaceholder("Enter reference ...");
        code.setWidth("100%");

        currency = new ComboBox<String>("Currency", getCurrencies());
        currency.setPlaceholder("Specify currency ...");
        currency.setWidth("100%");
        currency.setEmptySelectionAllowed(false);
        currency.addValueChangeListener(event -> {
            if (event.isUserOriginated())
                calculateSummaries(null);
        });

        issueDateField = new DateField("Issue Date");
        issueDateField.setDescription("Specify invoice issue date");
        issueDateField.setWidth("100%");
        costBinder.forField(issueDateField).withConverter(new LocalDateToDateConverter()).bind("issueDate");

        FormLayout basicsForm = new FormLayout(code, currency, issueDateField);
        basicsForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        basicsForm.setWidth("100%");
        return basicsForm;
    }

    protected HorizontalLayout createTermsForm() {
        notes = new TextArea("Notes");
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        notes.setPlaceholder("Specify other notes ...");
        notes.setRows(6);
        notes.setWidth("100%");

        terms = new TextArea("Terms");
        terms.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        terms.setPlaceholder("Specify terms ...");
        terms.setRows(6);
        terms.setWidth("100%");

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

    public TextField getCustomerName() {
        return customerName;
    }

    public DateField getIssueDateField() {
        return issueDateField;
    }

    public TextField getDiscountPercentageField() {
        return discountPercentageField;
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

    public Binder<T> getCostBinder() {
        return costBinder;
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
