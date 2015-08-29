<%--
    Document   : index
    Created on : Oct 12, 2009, 11:44:46 AM
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Async JavaScript Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form>
            <input id="inputField" type="text" onkeyup="doUpdate()">
        </form>
        <div id="wisdom">
        </div>
        <script>
            wisdomTag = document.getElementById("wisdom");
            inputTag = document.getElementById("inputField");

            function doUpdate() {
                var req;
                if (window.XMLHttpRequest) {
                    req = new XMLHttpRequest();
                } else if (window.ActiveXObject) {
                    req = new ActiveXObject("Microsoft.XMLHTTP");
                } else {
                    alert("AJAX not supported");
                }

                req.onreadystatechange = function() {
                    if (req.readyState == 4 && req.status == 200) {
                        wisdomTag.innerHTML = req.responseText;
                    }
                }

                text = inputTag.value;
                req.open("GET", "update.jsp?text=" + text, true);
                req.send(null);
            }


        </script>
    </body>
</html>
