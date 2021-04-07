let gLimited;
let g;

function getLimited() {
    var xmlhttp = new XMLHttpRequest();
    var url = "http://localhost:8080/api/get/limited";
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            limited_chart = this.responseText;
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send()
}
function getLAll() {
    var xmlhttp = new XMLHttpRequest();
    var url = "http://localhost:8080/api/get/all";
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            chart = this.responseText;
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send()
}

$(document).ready(function () {
    g = new Dygraph(
        document.getElementById("temp_chart"),
        chart,
        {
            legend: 'never',
            title: 'Temperatury',
            showRoller: false,
            rollPeriod: 1,
            ylabel: 'T(C°)'
        }
    );
})
$(document).ready(function () {
    gLimited = new Dygraph(
        document.getElementById("temp_limited_chart"),
        limited_chart,
        {
            legend: 'never',
            title: 'Temperatury',
            showRoller: false,
            rollPeriod: 1,
            ylabel: 'T(C°)'
        }
    );
})
window.setInterval((function () {
    let seconds = 15;
    let textNode = document.createTextNode('15');
    document.getElementById('seconds-counter').appendChild(textNode);
    return function () {
        seconds -= 1
        if (seconds === 0) {
            // window.location.reload();
            getLimited();
            getLAll();
            seconds = 15;
            gLimited.updateOptions({
                'file': limited_chart
            }, false);
            g.updateOptions({
                'file': chart
            }, false);
        }
        textNode.data = seconds;
    };
}()), 1000);

