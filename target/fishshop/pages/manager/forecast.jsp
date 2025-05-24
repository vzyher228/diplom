<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>
<fmt:message key="manager.forecast.title" var="title"/>

<html>
<head>
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<jsp:include page="manager_header.jsp"/>

<div class="container mt-4 text-center">
    <form class="row justify-content-center align-items-end g-2">
        <div class="col-auto">
            <label for="fromDate" class="form-label">Использовать данные с:</label>
            <input type="date" id="fromDate" class="form-control form-control-sm" value="2022-06-01">
        </div>
        <div class="col-auto">
            <label for="cruise" class="form-label">Товар:</label>
            <select id="cruise" class="form-select form-select-sm">
                <option>Все товары</option>
                <option>Катушки</option>
                <option>Удочки</option>
                <option>Лески</option>
                <option>Спининги</option>
                <option>Аксессуары</option>
            </select>
        </div>
        <div class="col-auto">
            <label for="toDate" class="form-label">Прогнозровать до:</label>
            <input type="date" id="toDate" class="form-control form-control-sm" value="2025-06-02">
        </div>
        <div class="col-12 mt-3">
            <button type="button" class="btn btn-primary btn-sm mx-1" onclick="showChart()">График</button>
            <button type="button" class="btn btn-primary btn-sm mx-1" onclick="showTable()">Таблица</button>
            <button type="button" class="btn btn-primary btn-sm mx-1" onclick="downloadCSV()">Скачать в CSV</button>
        </div>
    </form>
</div>

<!-- Chart Section -->

<div class="container mt-5" id="chartContainer">
    <div class="row">
        <div class="col-md-6">
            <h5 class="mt-5">Данные за 36 месяцев</h5>
            <canvas id="forecastChart"></canvas>
        </div>
        <div class="col-md-6">
            <h5 class="mt-5">Прогноз на 36 месяцев</h5>
            <canvas id="forecastChart2"></canvas>
        </div>
    </div>
</div>

<!-- Table Section -->
<div class="container mt-5" id="tableContainer" style="display: none;">
    <div class="row">
        <div class="col-md-6">
            <h5 class="mt-5">Данные за 36 месяцев</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>Месяц</th>
                    <th>Продажи</th>
                </tr>
                </thead>
                <tbody id="forecastTableBody">
                <!-- JS заполняет эту таблицу -->
                </tbody>
            </table>
        </div>
        <div class="col-md-6">
            <h5 class="mt-5">Прогноз на 36 месяцев</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>Месяц</th>
                    <th>Продажи</th>
                </tr>
                </thead>
                <tbody id="forecastTableBody2">
                <!-- JS заполняет эту таблицу -->
                </tbody>
            </table>
        </div>
    </div>

</div>

<script>
    const labels = Array.from({length: 36}, (_, i) => i + 1);
    const actualData = [
        851, 787, 963, 1428, 1610, 1768, 1869, 1715, 1445, 1242, 1093, 1003,
        857, 792, 968, 1437, 1620, 1777, 1880, 1726, 1452, 1250, 1100, 1009,
        862, 797, 974, 1446, 1630, 1788, 1892, 1736, 1461, 1257, 1107, 1015
    ];
    const trendData = [
        1306, 1309, 1310, 1310, 1311, 1312, 1312, 1313, 1314, 1314, 1315, 1315,
        1316, 1317, 1317, 1318, 1319, 1319, 1320, 1321, 1321, 1322, 1323, 1323,
        1324, 1325, 1325, 1326, 1327, 1327, 1328, 1329, 1329, 1330, 1331, 1331
    ];
    const actualData2 = [
        866, 800, 979, 1452, 1637, 1796, 1900, 1744, 1468, 1263, 1111, 1019,
        872, 805, 985, 1461, 1646, 1807, 1912, 1753, 1476, 1271, 1117, 1025,
        877, 809, 991, 1470, 1656, 1818, 1923, 1764, 1485, 1278, 1124, 1031
    ];
    const trendData2 = [
        1330, 1331, 1331, 1332, 1333, 1333, 1334, 1335, 1335, 1336, 1336, 1337,
        1338, 1338, 1339, 1340, 1340, 1341, 1342, 1342, 1343, 1344, 1344, 1345,
        1346, 1346, 1347, 1348, 1348, 1349, 1350, 1350, 1351, 1352, 1352, 1353
    ];

    const ctx2 = document.getElementById("forecastChart2").getContext("2d");
    const chart2 = new Chart(ctx2, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Прогноз',
                    data: actualData2,
                    borderColor: 'green',
                    backgroundColor: 'rgba(0, 128, 0, 0.2)',
                    tension: 0.3,
                    pointRadius: 2
                },
                {
                    label: 'Тренд',
                    data: trendData2,
                    borderColor: 'red',
                    borderDash: [6, 4],
                    fill: false,
                    tension: 0.3,
                    pointRadius: 0
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Forecast for 36 months' }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });



    const ctx = document.getElementById("forecastChart").getContext("2d");
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Продажи',
                    data: actualData,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    tension: 0.3,
                    pointRadius: 2
                },
                {
                    label: 'Тренд',
                    data: trendData,
                    borderColor: 'orange',
                    borderDash: [6, 4],
                    fill: false,
                    tension: 0.3,
                    pointRadius: 0
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Прогноз продаж по месяцам' }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    function showChart() {
        document.getElementById("chartContainer").style.display = "block";
        document.getElementById("tableContainer").style.display = "none";
    }

    function showTable() {
        const tableBody = document.getElementById("forecastTableBody");
        const tableBody2 = document.getElementById("forecastTableBody2");

        // Очистка
        tableBody.innerHTML = "";
        tableBody2.innerHTML = "";

        // Добавление строк
        for (let i = 0; i < labels.length; i++) {
            // Первая таблица
            const row = document.createElement("tr");
            const cellMonth = document.createElement("td");
            cellMonth.textContent = labels[i];
            const cellActual = document.createElement("td");
            cellActual.textContent = actualData[i];

            row.appendChild(cellMonth);
            row.appendChild(cellActual);

            tableBody.appendChild(row);

            // Вторая таблица
            const row2 = document.createElement("tr");
            const cellMonth2 = document.createElement("td");
            cellMonth2.textContent = labels[i];
            const cellActual2 = document.createElement("td");
            cellActual2.textContent = actualData2[i];
            row2.appendChild(cellMonth2);
            row2.appendChild(cellActual2);
            tableBody2.appendChild(row2);
        }

        // Переключение отображения
        document.getElementById("chartContainer").style.display = "none";
        document.getElementById("tableContainer").style.display = "block";
    }

    function downloadCSV() {
        // Заголовки CSV
        let csvContent = "data:text/csv;charset=utf-8,Month,Sales,Forecast\n";

        // Добавляем данные построчно с помощью обычного цикла for
        for (let i = 0; i < labels.length; i++) {
            csvContent += labels[i] + "," + actualData[i] + "," + actualData2[i] + "\n";
        }

        // Создаем и запускаем загрузку
        const encodedUri = encodeURI(csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "sales_forecast.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
</script>

<jsp:include page="../footer.jsp"/>
</body>
</html>
