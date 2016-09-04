import io.ankara.domain.Cost

import java.math.RoundingMode
import com.sun.org.apache.xml.internal.security.utils.Base64

//Cost cost;

//style(type: "text/css") {
//    include unescaped: 'templates/cost.css'
//}

link(rel: "stylesheet",
        href: "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css",
        integrity: "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u",
        crossorigin: "anonymous")

link(rel: "stylesheet",
        href: "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css",
        integrity: "sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp",
        crossorigin: "anonymous")

div(class: "printSection") {
    div(class: "container-fluid") {

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
                    div(class: "col-xs-2 caption", "From")
                    div(class: "col-xs-10 left-vertical-line") {
                        address {
                            strong(cost.company.name) br()
                            span(class: "pre", cost.company.address)
                        }
                        table(class: "table table-condensed") {

                            if (cost.company.phone) {
                                tr {
                                    td(width: "20%", class: "caption", "Phone")
                                    td {
                                        span(cost.company.phone)
                                    }
                                }
                            }

                            if (cost.company.fax) {

                                tr {
                                    td(width: "20%", class: "caption", " Fax")
                                    td {
                                        span(cost.company.fax)
                                    }
                                }
                            }

                            if (cost.company.email) {

                                tr {
                                    td(width: "20%", class: "caption", "Email")
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
                    div(class: "col-xs-4 caption", "Invoice ID")
                    div(class: "col-xs-8 left-vertical-line") {
                        strong(cost.code)
                    }
                }

                if (cost.purchaseOrder) {
                    div(class: "row") {
                        div(class: "col-xs-4 caption", "PO Number")
                        div(class: "col-xs-8 left-vertical-line") {
                            strong(cost.purchaseOrder)
                        }
                    }

                }

                if (cost.issueDate) {
                    div(class: "row top5") {
                        div(class: "col-xs-4 caption") {
                            span("Issue Date")
                        }
                        div(class: "col-xs-8 left-vertical-line") {
                            span(cost.issueDate)
                        }
                    }
                }

                if (cost.dueDate) {
                    div(class: "row") {

                        div(class: "col-xs-4 caption") {
                            span("Due Date")
                        }
                        div(class: "col-xs-8 left-vertical-line") {
                            span(cost.dueDate)
                        }
                    }

                }

                div(class: "row top5") {

                    div(class: "col-xs-4 caption") {
                        span("Subject")
                    }
                    div(class: "col-xs-8 left-vertical-line") {
                        span(class: "subject", cost.subject)
                    }
                }

            }
            div(class: "col-xs-6") {
                div(class: "row") {

                    div(class: "col-xs-2 caption") {
                        span("For")
                    }
                    div(class: "col-xs-10 left-vertical-line") {
                        address {
                            strong(cost.customer.name) br()
                            span(class: "pre", cost.customer.address)
                        }
                        table(class: "table table-condensed") {
                            if (cost.customer.phone) {
                                tr {
                                    td(width: "20%", class: "caption", "Phone")
                                    td {
                                        span(cost.customer.phone)
                                    }
                                }
                            }

                            if (cost.customer.fax) {

                                tr {
                                    td(width: "20%", class: "caption", " Fax")
                                    td {
                                        span(cost.customer.fax)
                                    }
                                }
                            }

                            if (cost.customer.email) {

                                tr {
                                    td(width: "20%", class: "caption", "Email")
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
                                td(class: "text-right") { span(item.quantity) }
                                td(class: "text-right") { span("$item.price $cost.currency") }
                                td(class: "text-right") { span("$item.amount $cost.currency") }
                            }
                        }
                    }
                }
            }
        }

        div(class: "row top10") {
            div(class: "col-xs-4 col-xs-offset-8 ") {
                table(class: "table table-condensed borderless") {
                    tr {
                        td(width: "50%", class: "caption text-right") {
                            span(" Subtotal")
                        }
                        td(class: "text-right") {
                            BigDecimal subtotal = cost.calculateSubtotal().setScale(2, RoundingMode.HALF_DOWN)
                            span("$subtotal $cost.currency")
                        }
                    }

                    if (cost.discountPercentage) {

                        tr {
                            td(width: "50%", class: "caption text-right") {
                                span("Discount (${cost.discountPercentage}%)")
                            }
                            td(class: "text-right") {
                                BigDecimal discount = cost.calculateDiscount().setScale(2, RoundingMode.HALF_DOWN)
                                span("$discount $cost.currency")
                            }
                        }

                    }

                    cost.taxes.each { tax ->
                        tr {
                            td(width: "50%", class: "caption text-right") {
                                span(tax.toString())
                            }
                            td(class: "text-right") {
                                BigDecimal taxAmount = cost.calculateTax(tax).setScale(2, RoundingMode.HALF_DOWN)
                                span("$taxAmount $cost.currency")
                            }
                        }
                    }


                    tr {
                        td(width: "50%", class: "text-right") {
                            strong("Amount Due")
                        }
                        td(class: "text-right") {
                            BigDecimal due = cost.calculateAmountDue().setScale(2, RoundingMode.HALF_DOWN)
                            strong("$due $cost.currency")
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
}