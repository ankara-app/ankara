package io.ankara.ui.vaadin.main.view.cost.estimate;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.UserService;
import io.ankara.ui.vaadin.main.view.cost.CostsProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/12/17 1:10 PM
 */
@SpringComponent
@PrototypeScope
public class EstimatesProvider extends CostsProvider<Estimate>{

    private EstimateService estimateService;
    private UserService userService;

    public EstimatesProvider(EstimateService estimateService, UserService userService) {
        this.estimateService = estimateService;
        this.userService = userService;
    }

    @Override
    protected Page<Estimate> getCosts(Query<Estimate, Void> query, String codeFilter, String customerNameFilter, String subjectFilter, Pageable pageable) {
        return estimateService.getEstimates(userService.getCurrentUser(),codeFilter,customerNameFilter,subjectFilter,pageable);
    }

    @Override
    protected int countCosts(Query<Estimate, Void> query, String codeFilter, String customerNameFilter, String subjectFilter) {
        Long size = estimateService.countEstimates(userService.getCurrentUser(),codeFilter,customerNameFilter,subjectFilter);
        return size.intValue();
    }
}
