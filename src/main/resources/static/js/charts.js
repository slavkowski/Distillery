let gLimited;
let g;
let temps;

let level1;
let level2;
let level3;
let level4;

let level1alarm = 100;
let level2alarm = 100;
let level3alarm = 100;
let level4alarm = 100;


let ts;

window.onload = function () {
    window.document.getElementById("level1threshold").innerText = level1alarm + ' °C';
    window.document.getElementById("level2threshold").innerText = level2alarm + ' °C';
    window.document.getElementById("level3threshold").innerText = level3alarm + ' °C';
    window.document.getElementById("level4threshold").innerText = level4alarm + ' °C';
    getTemps();
    getLimited()
    getAll()
};

function saveThresholds() {
    let level1alarmRead = document.getElementById("threshold1").value;
    if(level1alarmRead){
        level1alarm = level1alarmRead;
        window.document.getElementById("level1threshold").innerText = level1alarmRead + ' °C';
    }
    let level2alarmRead = document.getElementById("threshold2").value;
    if(level2alarmRead){
        level2alarm = level2alarmRead;
        window.document.getElementById("level2threshold").innerText = level2alarm + ' °C';
    }
    let level3alarmRead = document.getElementById("threshold3").value;
    if(level3alarmRead){
        level3alarm = level3alarmRead;
        window.document.getElementById("level3threshold").innerText = level3alarm + ' °C';
    }
    let level4alarmRead = document.getElementById("threshold4").value;
    if(level4alarmRead){
        level4alarm = level4alarmRead;
        window.document.getElementById("level4threshold").innerText = level4alarm + ' °C';
    }

}

function getTemps() {
    let xmlhttp = new XMLHttpRequest();
    let url = "http://" + window.location.hostname + ":8080/api/get/temps";
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            temps = JSON.parse(this.responseText);
            level1 = temps.level1.toFixed(2)
            level2 = temps.level2.toFixed(2)
            level3 = temps.level3.toFixed(2)
            level4 = temps.level4.toFixed(2)
            ts = temps.ts
            window.document.getElementById("t1").innerText = 'Poziom 1: ' + level1 + ' °C';
            window.document.getElementById("t2").innerText = 'Poziom 2: ' + level2 + ' °C';
            window.document.getElementById("t3").innerText = 'Poziom 3: ' + level3 + ' °C';
            window.document.getElementById("t4").innerText = 'Poziom 4: ' + level4 + ' °C';
            window.document.getElementById("ts").innerText = 'Ostatni pomiar: ' + ts;
            checkThresholdLevels();
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send()
}

function checkThresholdLevels() {
    let showAlarm = false;
    let alertText = ''
    if (level1 >= level1alarm) {
        alertText = alertText + 'Poziom 1. Jest: ' + level1 + ' Alarm: ' + level1alarm + "<br>";
        showAlarm = true;
    }
    if (level2 >= level2alarm) {
        alertText = alertText + 'Poziom 2. Jest: ' + level2 + ' Alarm: ' + level2alarm + "<br>";
        showAlarm = true;
    }
    if (level3 >= level3alarm) {
        alertText = alertText + 'Poziom 3. Jest: ' + level3 + ' Alarm: ' + level3alarm + "<br>";
        showAlarm = true;
    }
    if (level4 >= level4alarm) {
        alertText = alertText + 'Poziom 4. Jest: ' + level4 + ' Alarm: ' + level4alarm + "<br>";
        showAlarm = true;
    }
    if(showAlarm){
        window.document.getElementById("alert_message").innerHTML = alertText;
        showAlert();
    }
}

let limited_chart;

function getLimited() {
    var xmlhttp = new XMLHttpRequest();
    var url = "http://" + window.location.hostname + ":8080/api/get/limited";
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            limited_chart = this.responseText;
            gLimited.updateOptions({
                'file': limited_chart
            }, false);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send()
}

let chart;

function getAll() {
    var xmlhttp = new XMLHttpRequest();
    var url = "http://" + window.location.hostname + ":8080/api/get/all";
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            chart = this.responseText;
            g.updateOptions({
                'file': chart
            }, false);
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
            getAll();
            getTemps()
            seconds = 15;
        }
        textNode.data = seconds;
    };
}()), 1000);

