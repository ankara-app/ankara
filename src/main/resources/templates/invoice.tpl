package templates

import com.sun.org.apache.xml.internal.security.utils.Base64
import io.ankara.ui.vaadin.util.NumberUtils

import java.math.RoundingMode

//Cost cost;

//style(type: "text/css") {
//    include unescaped: 'templates/cost.css'
//}
//
//script(src: "https://printjs-4de6.kxcdn.com/print.min.js", type: "text/javacript"){}
//link(rel: "stylesheet", "https://printjs-4de6.kxcdn.com/print.min.css")

// On the printing UI we use bootstrap theme which comes with the necessary styles
if (!printing) {
    link(rel: "stylesheet",
            href: "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css",
            integrity: "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u",
            crossorigin: "anonymous")

    link(rel: "stylesheet",
            href: "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css",
            integrity: "sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp",
            crossorigin: "anonymous")
}

div(id: "printSection", class: "container-fluid") {

    div(class: "row top10") {
        div(class: "col-xs-6") {

            if (cost.company.picture) {
                def photoEncoded = Base64.encode(cost.company.picture);
                img(class: "logo  img-rounded",
                        src: "data:image/png;base64,$photoEncoded") {
                }
            }

        }
        div(class: "col-xs-6") {
            div(class: "row") {
                div(class: "col-xs-2") {
                    span(class: "caption", "From")
                }

                div(class: "col-xs-10 left-vertical-line") {
                    address {
                        strong(cost.company.name) br()
                        span(class: "pre", cost.company.address)
                    }
                    table(class: "table table-condensed") {

                        if (cost.company.phone) {
                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Phone")
                                }
                                td {
                                    span(cost.company.phone)
                                }
                            }
                        }

                        if (cost.company.fax) {

                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Fax")
                                }
                                td {
                                    span(cost.company.fax)
                                }
                            }
                        }

                        if (cost.company.email) {

                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Email")
                                }
                                td {
                                    span(cost.company.email)
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    div(class: "row top10") {
        div(class: "col-xs-6") {
            div(class: "row") {
                div(class: "col-xs-4") {
                    span(class: "caption", "Invoice ID")
                }
                div(class: "col-xs-8 left-vertical-line") {
                    strong(cost.code)
                }
            }

            if (cost.purchaseOrder) {
                div(class: "row") {
                    div(class: "col-xs-4") {
                        span(class: "caption", "PO Number")
                    }
                    div(class: "col-xs-8 left-vertical-line") {
                        strong(cost.purchaseOrder)
                    }
                }

            }

            if (cost.issueDate) {
                div(class: "row top5") {
                    div(class: "col-xs-4") {
                        span(class: "caption", "Issue Date")
                    }
                    div(class: "col-xs-8 left-vertical-line") {
                        span(cost.issueDate)
                    }
                }
            }

            if (cost.dueDate) {
                div(class: "row") {

                    div(class: "col-xs-4") {
                        span(class: "caption", "Due Date")
                    }
                    div(class: "col-xs-8 left-vertical-line") {
                        span(cost.dueDate)
                    }
                }

            }

            div(class: "row top5") {

                div(class: "col-xs-4") {
                    span(class: "caption", "Subject")
                }
                div(class: "col-xs-8 left-vertical-line") {
                    span(class: "subject", cost.subject)
                }
            }

        }
        div(class: "col-xs-6") {
            div(class: "row") {

                div(class: "col-xs-2") {
                    span(class: "caption", "For")
                }
                div(class: "col-xs-10 left-vertical-line") {
                    address {
                        strong(cost.customer.name) br()
                        span(class: "pre", cost.customer.address)
                    }
                    table(class: "table table-condensed") {
                        if (cost.customer.phone) {
                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Phone")
                                }
                                td {
                                    span(cost.customer.phone)
                                }
                            }
                        }

                        if (cost.customer.fax) {

                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Fax")
                                }
                                td {
                                    span(cost.customer.fax)
                                }
                            }
                        }

                        if (cost.customer.email) {

                            tr {
                                td(width: "20%") {
                                    span(class: "caption", "Email")
                                }
                                td {
                                    span(cost.customer.email)
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    div(class: "row top15") {
        div(class: "col-xs-12") {
            table(class: "table") {
                thead {
                    tr {
                        th(class: "text-right", "Index")
                        th("Type")
                        th("Description")
                        th(class: "text-right", " Quantity")
                        th(width: "16.5%", class: "text-right", "Unit Price")
                        th(width: "16.5%", class: "text-right", "Amount")
                    }
                }
                tbody {
                    int index = 0;
                    cost.items.each { item ->
                        tr {
                            th(scope: "row", class: "text-right", ++index)
                            td() { span(item.type.name) }
                            td() { span(item.description) }

                            def quantity = item.quantity.setScale(2, RoundingMode.HALF_DOWN)
                            td(class: "text-right") { span(quantity) }


                            td(class: "text-right") { span(formatMoney(item.price, cost.currency)) }

                            td(class: "text-right") { span(formatMoney(item.amount, cost.currency)) }
                        }
                    }
                }
            }
        }
    }

    div(class: "row top10") {
        div(class: "col-xs-6 col-xs-offset-6 ") {
            table(class: "table table-condensed borderless") {
                tr {
                    td(width: "50%", class: "text-right") {
                        span(class: "caption", "Subtotal")
                    }
                    td(class: "text-right") {
                        span(formatMoney(cost.subtotal, cost.currency))
                    }
                }

                if (cost.discountPercentage) {

                    tr {
                        td(width: "50%", class: "text-right") {
                            span(class: "caption", "Discount (${cost.discountPercentage}%)")
                        }
                        td(class: "text-right") {
                            span(formatMoney(cost.discount, cost.currency))
                        }
                    }

                }

                cost.taxes.each { tax ->
                    tr {
                        td(width: "50%", class: "text-right") {
                            span(class: "caption", tax.toString())
                        }
                        td(class: "text-right") {
                            BigDecimal taxAmount = cost.calculateTax(tax).setScale(2, RoundingMode.HALF_DOWN)
                            span(formatMoney(taxAmount, cost.currency))
                        }
                    }
                }


                tr {
                    td(width: "50%", class: "text-right") {
                        strong("Amount Due")
                    }
                    td(class: "text-right") {
                        strong(formatMoney(cost.amountDue, cost.currency))
                    }
                }
            }
        }
    }

    if (cost.notes) {
        div(class: "row top10") {
            hr()
            div(class: "col-lg-8") {
                strong("Notes") br()
                span(class: "pre", cost.notes)
            }
        }
    }

    if (cost.terms) {
        div(class: "row top10") {
            div(class: "col-lg-8") {
                strong("Terms") br()
                span(class: "pre", cost.terms)
            }
        }
    }

}

def formatMoney(BigDecimal amount, String currency) {
    return NumberUtils.formatMoney(amount,currency)
}