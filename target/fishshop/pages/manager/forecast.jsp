<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-4">
    <h3>Initial Data (${dataDates.size()} months)</h3>
    <div id="dataChart" style="height: 300px;"></div>

    <h3 class="mt-5">Forecast (${monthsCount} months)</h3>
    <div id="forecastChart" style="height: 300px;"></div>
</div>

<!-- Подключение Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const dataChartData = ${dataChart};
    const forecastChartData = ${forecastChart};

    const ctx1 = document.getElementById('dataChart').getContext('2d');
    new Chart(ctx1, {
        type: 'line',
        data: {
            labels: Object.keys(dataChartData),
            datasets: [{
                label: 'Initial Data',
                data: Object.values(dataChartData),
                borderColor: 'blue',
                fill: false
            }]
        }
    });

    const ctx2 = document.getElementById('forecastChart').getContext('2d');
    new Chart(ctx2, {
        type: 'line',
        data: {
            labels: Object.keys(forecastChartData),
            datasets: [{
                label: 'Forecast',
                data: Object.values(forecastChartData),
                borderColor: 'green',
                fill: false
            }]
        }
    });
</script>
