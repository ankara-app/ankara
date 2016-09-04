/**
 * Created by bonifacechacha on 9/4/16.
 */
document.getElementById("btnPrint").onclick = function () {
    printElement(document.getElementById("printThis"));
    window.print();
}

function printElement(elem) {
    var domClone = elem.cloneNode(true);

    var $printSection = document.getElementById("printSection");

    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }

    $printSection.innerHTML = "";

    $printSection.appendChild(domClone);
}