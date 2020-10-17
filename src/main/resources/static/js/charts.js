$(document).ready(function() {
    new Dygraph(
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
window.setInterval((function(){
    var seconds = 60;
    var textNode = document.createTextNode('60');
    document.getElementById('seconds-counter').appendChild(textNode);
    return function() {
        seconds -= 1
        if(seconds===0){
            window.location.reload();
        }
        textNode.data = seconds;
    };
}()), 1000);

