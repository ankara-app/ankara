package io.ankara.ui.vaadin.main.view.cost;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import io.ankara.domain.Cost;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/17 12:34 PM
 */

public abstract  class CostsProvider<T extends Cost> extends PageableDataProvider<T,Void> {
    private String codeFilter = "";
    private String customerNameFilter = "";
    private String subjectFilter = "";

    public String getCodeFilter() {
        return codeFilter;
    }

    public String getCustomerNameFilter() {
        return customerNameFilter;
    }

    public String getSubjectFilter() {
        return subjectFilter;
    }

    public void setCodeFilter(String codeFilter) {
        this.codeFilter = codeFilter;
        refreshAll();
    }

    public void setCustomerNameFilter(String customerNameFilter) {
        this.customerNameFilter = customerNameFilter;
        refreshAll();
    }

    public void setSubjectFilter(String subjectFilter) {
        this.subjectFilter = subjectFilter;
        refreshAll();
    }

    @Override
    protected Page<T> fetchFromBackEnd(Query<T, Void> query, Pageable pageable) {
        return getCosts(query,codeFilter,customerNameFilter,subjectFilter,pageable);
    }

    protected abstract Page<T> getCosts(Query<T, Void> query, String codeFilter, String customerNameFilter, String subjectFilter, Pageable pageable);

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        List<QuerySortOrder> sortOrders = new ArrayList<>();
        sortOrders.add(new QuerySortOrder("timeCreated", SortDirection.DESCENDING));
        sortOrders.add(new QuerySortOrder("id", SortDirection.DESCENDING));
        return sortOrders;
    }

    @Override
    protected int sizeInBackEnd(Query<T,Void> query) {
        return countCosts(query,codeFilter,customerNameFilter,subjectFilter);
    }

    protected abstract int countCosts(Query<T, Void> query, String codeFilter, String customerNameFilter, String subjectFilter);
}
