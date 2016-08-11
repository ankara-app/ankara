package io.ankara.domain;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/11/16 10:46 AM
 */
@Entity
public class Invoice extends Cost {

    private String purchaseOrder;

    private Date dueDate;
}
