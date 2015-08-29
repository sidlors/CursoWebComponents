<%-- 
    Document   : CustomerView
    Created on : Oct 14, 2009, 5:00:23 PM
    Author     : Simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Customer Information</h1>
        <p>Customer name: ${customer.name}</p>
        <p>Customer Business Address:<br/>
            ${customer.officeAddress.address1}<br/>
            ${customer.officeAddress.address2}<br/>
            ${customer.officeAddress.city}<br/>
            ${customer.officeAddress.region}<br/>
            ${customer.officeAddress.postcode}<br/>
        </p>
        <p>Customer Billing Address:<br/>
            ${customer.billingAddress.address1}<br/>
            ${customer.billingAddress.address2}<br/>
            ${customer.billingAddress.city}<br/>
            ${customer.billingAddress.region}<br/>
            ${customer.billingAddress.postcode}<br/>
        </p>
        <p>Customer Business Address:<br/>
            ${customer.deliveryAddress.address1}<br/>
            ${customer.deliveryAddress.address2}<br/>
            ${customer.deliveryAddress.city}<br/>
            ${customer.deliveryAddress.region}<br/>
            ${customer.deliveryAddress.postcode}<br/>
        </p>
        <p>Selecting Customer Business Address by <em>index:</em><br/>
            ${customer.addresses[2].address1}<br/>
            ${customer.addresses[2].address2}<br/>
            ${customer.addresses[2].city}<br/>
            ${customer.addresses[2].region}<br/>
            ${customer.addresses[2].postcode}<br/>
        </p>
    </body>
</html>
